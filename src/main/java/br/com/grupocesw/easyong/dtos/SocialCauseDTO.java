package br.com.grupocesw.easyong.dtos;

import java.io.Serializable;

import br.com.grupocesw.easyong.entities.SocialCause;
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

	public SocialCauseDTO(SocialCause cause) {
		id = cause.getId();
		name = cause.getName();
	}
}
