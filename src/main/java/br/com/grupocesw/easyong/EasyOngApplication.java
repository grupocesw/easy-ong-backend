package br.com.grupocesw.easyong;

import br.com.grupocesw.easyong.configs.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(
	exclude = { RedisRepositoriesAutoConfiguration.class }
)
@EnableCaching(proxyTargetClass = true)
@EnableConfigurationProperties(AppProperties.class)
public class EasyOngApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyOngApplication.class, args);
	}

}
