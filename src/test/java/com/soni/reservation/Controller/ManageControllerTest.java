//package com.soni.reservation.Controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.soni.reservation.domain.Manager;
//import com.soni.reservation.dto.ManagerDto;
//import com.soni.reservation.security.JwtAuthenticationFilter;
//import com.soni.reservation.security.TokenProvider;
//import com.soni.reservation.service.ManageService;
//import com.soni.reservation.service.MemberService;
//import com.soni.reservation.service.StoreService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static com.soni.reservation.type.Authority.ROLE_MANAGER;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(ManageController.class)
//class ManageControllerTest {
//    @MockBean
//    private ManageService manageService;
//
//    @MockBean
//    private StoreService storeService;
//
//    @MockBean
//    private TokenProvider tokenProvider;
//
//    @MockBean
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @MockBean
//    private MemberService memberService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    void registerTest() throws Exception {
//        //given
//        given(manageService.register(ManagerDto.RegisterRequest
//                .builder()
//                .name("소은")
//                .mail("soeun@naver.com")
//                .password("soni")
//                .role(String.valueOf(ROLE_MANAGER))
//                .build()))
//                .willReturn(
//                        Manager.builder()
//                                .name("소은")
//                                .password("soni")
//                                .mail("soeun@naver.com")
//                                .role(String.valueOf(ROLE_MANAGER))
//                                .build()
//                );
//        //when
//        //then
//        mockMvc.perform(post("/manager/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(
//                                objectMapper.writeValueAsString(
//                                        new ManagerDto.RegisterRequest(
//                                                "소은", "soeun@naver.com", "soni","ROLE_MANAGER"
//                                        )
//                                )
//                        ))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("소은"))
//                .andDo(print());
//    }
//}