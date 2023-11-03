package com.soni.reservation.service;

import com.soni.reservation.domain.Member;
import com.soni.reservation.domain.Reserve;
import com.soni.reservation.domain.Review;
import com.soni.reservation.dto.LoginDto;
import com.soni.reservation.dto.MemberDto;
import com.soni.reservation.dto.ReserveDto;
import com.soni.reservation.dto.ReviewDto;
import com.soni.reservation.exception.UserException;
import com.soni.reservation.repository.MemberRepository;
import com.soni.reservation.repository.ReserveRepository;
import com.soni.reservation.repository.ReviewRepository;
import com.soni.reservation.repository.StoreRepository;
import com.soni.reservation.security.TokenProvider;
import com.soni.reservation.type.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.soni.reservation.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final ReserveRepository reserveRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;


    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        return this.memberRepository.findByMail(mail)
                .orElseThrow(RuntimeException::new);
    }

    /**
     * 이용자 회원가입
     */
    public Member register(MemberDto.RegisterRequest member) {
        validate(member);

        member.setRole(String.valueOf(Authority.ROLE_MEMBER));
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member.toEntity());
    }

    /**
     * 회원가입 유효한지 확인
     */
    private void validate(MemberDto.RegisterRequest member) {
        boolean exists = memberRepository.existsByMail(member.getMail());
        if (exists) {
            throw new UserException(USER_DUPLICATED);
        }
    }

    /**
     * 로그인 유효한지 확인
     */
    public String authenticate(LoginDto member) {
        var user = memberRepository.findByMail(member.getMail())
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        if (!this.passwordEncoder.matches(member.getPassword(), user.getPassword())) {
            throw new UserException(PASSWORD_UNMATCHED);
        }
        return this.tokenProvider.generateToken(member.getMail(), user.getRole());
    }

    /**
     * 매장 예약
     */
    public ReserveDto addReserve(String token, ReserveDto reserve) {
        //회원 존재하는지 확인
        Member member = this.getMemberEntity(token);

        //매장 존재하는지 확인
        var store = storeRepository.findByStoreName(reserve.getStoreName())
                .orElseThrow(() -> new UserException(STORE_NOT_FOUND));

        //예약이 가능한지 확인 (동시간에 예약이 5건이면 안 됨)
        if (reserveRepository.countByReservedAt(reserve.getReservedAt()) == 5) {
            throw new UserException(RESERVE_IS_FULL);
        }

        //해당 회원 예약 중에 같은 매장에 예약한 게 있고 방문이 되지 않았으면 안 됨
        Optional<List<Reserve>> reserveStore = reserveRepository.findByStore(store);
        if (reserveStore.isPresent() && reserveStore.get().size() > 0) {
            List<Reserve> reserveList = reserveStore.get();
            reserveList.stream().forEach(item -> {
                if (Objects.equals(item.getStore().getId(), store.getId()) &&  !item.isVisited()) {
                    throw new UserException(RESERVE_DUPLICATED);
                }
            });
        }

        String reserveNum = this.getReserveNum(member.getId(), reserve.getReservedAt(), store.getId());

        reserveRepository.save(
                Reserve.builder()
                        .reserveNum(reserveNum)
                        .reservedAt(reserve.getReservedAt())
                        .member(member)
                        .store(store)
                        .build()
        );
        return ReserveDto.builder()
                .storeName(reserve.getStoreName())
                .reservedAt(reserve.getReservedAt())
                .reserveNum(reserveNum)
                .build();
    }

    /**
     * email로 해당 회원 Entity를 찾는 메소드
     */
    private Member getMemberEntity(String token) {
        String mail = getMailFromToken(token);

        Optional<Member> optionalMember = memberRepository.findByMail(mail);
        if (optionalMember.isEmpty()) {
            throw new UserException(MEMBER_NOT_FOUND);
        }

        return optionalMember.get();
    }

    /**
     * 토큰에서 email 꺼내오는 메소드
     */

    private String getMailFromToken(String token) {
        if (!ObjectUtils.isEmpty(token) && token.startsWith("Bearer")) {
            token =  token.substring("Bearer".length());
        }
        return tokenProvider.getMail(token);
    }

    /**
     * 예약번호 발급
     */
    private String getReserveNum(Long memberId, LocalDateTime reservedAt, Long storeId) {
        return memberId +
                Integer.toString(reservedAt.getYear()) +
                reservedAt.getMonth() +
                reservedAt.getDayOfMonth() +
                storeId;
    }

    /**
     * 리뷰 작성
     */
    public ReviewDto.Response addReview(ReviewDto.Request review, String token) {
        //token으로 회원확인
        Member member = this.getMemberEntity(token);

        //존재하는 예약인지 확인
        Reserve reserve = reserveRepository.findByReserveNum(review.getReserveNum())
                .orElseThrow(() -> new UserException(RESERVE_NOT_FOUND));
        //해당 예약건에 예약한 사람이 맞는지 확인
        if (!Objects.equals(member.getId(), reserve.getMember().getId())) {
            throw new UserException(UNMATCHED_MEMBER_RESERVE);
        }
        //방문 여부가 true인지 확인
        validateVisited(reserve);

        Review savedReview = reviewRepository.save(
                Review.builder()
                        .store(reserve.getStore())
                        .member(reserve.getMember())
                        .text(review.getText())
                        .build()
        );
        return ReviewDto.Response.builder()
                .storeName(savedReview.getStore().getStoreName())
                .memberName(savedReview.getMember().getName())
                .text(savedReview.getText())
                .build();
    }

    /**
     * 리뷰 작성하기 위해 방문했는지 확인
     */
    private void validateVisited(Reserve reserve) {
        if (!reserve.isVisited()) {
            throw new UserException(REVIEW_NOT_ALLOWED);
        }
    }
}
