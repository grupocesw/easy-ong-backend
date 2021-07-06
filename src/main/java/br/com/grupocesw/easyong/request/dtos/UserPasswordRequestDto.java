package br.com.grupocesw.easyong.request.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
    
	@NotEmpty(message = "Password required")
	@Size(min = 8, max = 100, message = "Password must contain between 6 and 100 characters")
	private String password;

	@NotEmpty(message = "Password required")
	@Size(min = 8, max = 100, message = "Password must contain between 6 and 100 characters")
	private String passwordConfirmation;

}