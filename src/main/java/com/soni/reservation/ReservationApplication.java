package com.soni.reservation;

import com.soni.reservation.domain.Manager;
import com.soni.reservation.domain.Store;
import com.soni.reservation.repository.ManagerRepository;
import com.soni.reservation.repository.StoreRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.websocket.Session;
import java.time.LocalDateTime;

@SpringBootApplication
public class ReservationApplication {

	public static void main(String[] args) {

		SpringApplication.run(ReservationApplication.class, args);

	}
}
