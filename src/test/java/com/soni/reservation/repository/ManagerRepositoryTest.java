package com.soni.reservation.repository;

import com.soni.reservation.domain.Manager;
import com.soni.reservation.type.Authority;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Transactional
class ManagerRepositoryTest {
    @Mock
    private ManagerRepository managerRepository;

    @Test
    void insertTest() {
        //given
        Manager manager = new Manager();
        manager.setName("소니");
        manager.setMail("soensuckuN@Naver.com");
        manager.setPassword("123455");
        manager.setRole(String.valueOf(Authority.ROLE_MANAGER));
        //when
        ArgumentCaptor<Manager> captor = ArgumentCaptor.forClass(Manager.class);
        managerRepository.save(manager);
        //then
        verify(managerRepository, times(1)).save(captor.capture());
     }
}