package br.com.grupocesw.easyong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching(proxyTargetClass = true)
public class EasyOngApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyOngApplication.class, args);
	}

}
