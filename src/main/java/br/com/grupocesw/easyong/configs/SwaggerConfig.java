
package br.com.grupocesw.easyong.configs;

import br.com.grupocesw.easyong.utils.MavenPomPropertyUtil;
import org.apache.maven.model.Model;
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

	private static final Model pomModel = MavenPomPropertyUtil.getPom();

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
			.useDefaultResponseMessages(false)
			.select()
			.apis(RequestHandlerSelectors.basePackage("br.com.grupocesw.easyong"))
			.paths(PathSelectors.any())
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

