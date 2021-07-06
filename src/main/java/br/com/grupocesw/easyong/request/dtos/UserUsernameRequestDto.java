package br.com.grupocesw.easyong.request.dtos;

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
public class UserUsernameRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Email(message = "Invalid email format")
	@NotEmpty(message = "Username required")
	@Size(min = 3, max = 100, message = "Username must contain between 3 and 100 characters")
	private String username;

}