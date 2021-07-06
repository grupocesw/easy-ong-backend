package br.com.grupocesw.easyong.request.dtos;

import br.com.grupocesw.easyong.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Email(message = "Invalid email format")
	@NotEmpty(message = "Username required")
	@Size(min = 3, max = 100, message = "Username must contain between 3 and 100 characters")
	private String username;

	@NotEmpty(message = "Password required")
	@Size(min = 8, max = 100, message = "Password must contain between 6 and 100 characters")
	private String password;
	
	@NotEmpty(message = "Name required")
	@Size(min = 3, max = 100, message = "Name must contain between 3 and 100 characters")
    private String name;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;
	
    private Gender gender;

	private Set<Long> causeIds;

}