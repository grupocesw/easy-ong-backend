package br.com.grupocesw.easyong.payloads;

import java.time.LocalDate;
import java.util.Set;

import br.com.grupocesw.easyong.dtos.PictureDTO;
import br.com.grupocesw.easyong.entities.Role;
import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.enums.GenderEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    
	private Long id;
	private String name;
	private LocalDate birthday;
	private GenderEnum gender;
	private String username;
	private Set<SocialCause> causes;
	private Set<Role> roles;
	private PictureDTO picture;
	
	
}
