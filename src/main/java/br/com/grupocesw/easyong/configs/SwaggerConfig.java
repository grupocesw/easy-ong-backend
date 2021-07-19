
package br.com.grupocesw.easyong.configs;

import br.com.grupocesw.easyong.utils.MavenPomPropertyUtil;
import org.apache.maven.model.Model;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
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

	private static final Model pomModel = MavenPomPropertyUtil.getPom();

	@Bean
	@Primary
	public Docket docketV1() {
		return new Docket(DocumentationType.SWAGGER_2)
			.groupName(pomModel.getName() + " API Version 1")
			.useDefaultResponseMessages(false)
			.select()
			.apis(RequestHandlerSelectors.basePackage("br.com.grupocesw.easyong.controllers"))
			.paths(PathSelectors.ant("/api/v1/**"))
			.build()
			.apiInfo(apiInfo());
	}

	@Bean
	public Docket docketV2() {
		return new Docket(DocumentationType.SWAGGER_2)
			.groupName(pomModel.getName() + " API Version 2")
			.useDefaultResponseMessages(false)
			.select()
			.apis(RequestHandlerSelectors.basePackage("br.com.grupocesw.easyong.controllers"))
			.paths(PathSelectors.ant("/api/v2/**"))
			.build()
			.apiInfo(apiInfo());
	}

	private Contact contact() {
		return new Contact(
			pomModel.getName(),
			pomModel.getUrl(),
			"grupocesw@gmail.com"
		);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title(pomModel.getName())
			.description(pomModel.getDescription())
			.version(pomModel.getVersion())
			.contact(contact())
			.build();
	}
}

