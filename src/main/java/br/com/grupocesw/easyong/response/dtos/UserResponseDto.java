package br.com.grupocesw.easyong.response.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import br.com.grupocesw.easyong.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto implements Serializable {
    
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private LocalDate birthday;
	private Gender gender;
	private String username;
	private PictureResponseDto picture;
	private Set<SocialCauseResponseDto> causes;
	private Set<RoleResponseDto> roles;

}
