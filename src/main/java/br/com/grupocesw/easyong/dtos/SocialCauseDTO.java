package br.com.grupocesw.easyong.dtos;

import java.io.Serializable;

import br.com.grupocesw.easyong.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SocialCauseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;

	public SocialCauseDTO(User user) {
		id = user.getId();
		name = user.getName();
	}
}
