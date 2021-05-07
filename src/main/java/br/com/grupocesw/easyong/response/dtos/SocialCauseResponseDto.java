package br.com.grupocesw.easyong.response.dtos;

import java.io.Serializable;

import br.com.grupocesw.easyong.entities.SocialCause;
import lombok.Data;

@Data
public class SocialCauseResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;

	public SocialCauseResponseDto(SocialCause cause) {
		id = cause.getId();
		name = cause.getName();
	}
}
