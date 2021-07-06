
package br.com.grupocesw.easyong.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.select()
				.apis(
						RequestHandlerSelectors.basePackage("br.com.grupocesw.easyong")
				)
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private Contact contact() {
		return new Contact(
				"Grupo C Software Engineer",
				"https://github.com/grupocesw/easy-ong-backend",
				"grupocesw@gmail.com"
		);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("API EASY ONG")
				.description("API to Project Bootcamp Impacta Software Engineer 2021")
				.version("1.0.0")
				.contact(contact())
				.build();
	}
}

