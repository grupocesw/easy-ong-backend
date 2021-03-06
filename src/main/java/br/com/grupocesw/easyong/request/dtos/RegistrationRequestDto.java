package br.com.grupocesw.easyong.request.dtos;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.grupocesw.easyong.enums.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDto {

	@NotEmpty(message = "Name required")
	@Size(min = 3, max = 100, message = "Name must contain between 3 and 100 characters")
	@Column(name = "name", nullable = false, length = 100)
    private String name;

	@ApiModelProperty(notes="Username is email", name="Username", required=true, value="user@easyong.com.br")
	@Email(message = "The email format is incorrect")
	@NotEmpty(message = "Username required")
	@Size(min = 3, max = 100, message = "Username must contain between 3 and 100 characters")
	@Column(name = "username", nullable = false, length = 100, unique = true)
    private String username;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;
    
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "birthday")
	private LocalDate birthday;

	@Column(name = "gender")
	private Gender gender = Gender.UNINFORMED;
}
