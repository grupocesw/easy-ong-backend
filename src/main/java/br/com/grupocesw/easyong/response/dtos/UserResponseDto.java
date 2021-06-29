package br.com.grupocesw.easyong.response.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import br.com.grupocesw.easyong.entities.Role;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.enums.Gender;
import br.com.grupocesw.easyong.mappers.PictureMapper;
import br.com.grupocesw.easyong.mappers.RoleMapper;
import br.com.grupocesw.easyong.mappers.SocialCauseMapper;
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

	public UserResponseDto(User user) {
		id = user.getId();
		name = user.getPerson().getName();
		birthday = user.getPerson().getBirthday();
		gender = user.getPerson().getGender();
		username = user.getUsername();
		picture = PictureMapper.INSTANCE.entityToResponseDto(user.getPicture());
		causes = SocialCauseMapper.INSTANCE.listToResponseDtoSet(user.getCauses());
		roles = RoleMapper.INSTANCE.listToResponseDtoSet(user.getRoles());
	}
	
}
