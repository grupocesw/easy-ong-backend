package br.com.grupocesw.easyong.request.dtos;

import br.com.grupocesw.easyong.enums.AppReasonContact;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppContactRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Size(min = 3, max = 100, message = "Name must contain between 3 and 100 characters")
	private String name;

	@Email(message = "Invalid email format")
	@Size(min = 3, max = 100, message = "Email must contain between 3 and 100 characters")
	private String email;

	private AppReasonContact reason;

	@NotEmpty(message = " message")
	private String message;

}