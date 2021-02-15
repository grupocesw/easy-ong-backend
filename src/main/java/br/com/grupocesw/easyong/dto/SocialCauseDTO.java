package br.com.grupocesw.easyong.dto;

import java.io.Serializable;

import br.com.grupocesw.easyong.entities.User;

public class SocialCauseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;

	public SocialCauseDTO() {}

	public SocialCauseDTO(User user) {
		id = user.getId();
		name = user.getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
