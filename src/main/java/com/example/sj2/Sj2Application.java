package com.example.sj2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // // 엔티티 클래스의 생성일자와 수정일자를 편리하게 관리 가능
@SpringBootApplication
public class Sj2Application {

	public static void main(String[] args) {
		SpringApplication.run(Sj2Application.class, args);
	}

}
