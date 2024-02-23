package org.cperez.springcloud.msvc.materias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcMateriasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcMateriasApplication.class, args);
	}

}
