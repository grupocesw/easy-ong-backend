package br.com.grupocesw.easyong.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.enumerations.Gender;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private LocalDate birthday;
	private Gender gender;
	private String login;
	private Set<SocialCause> causes;
	private PictureDTO picture;

	public UserDTO() {}

	public UserDTO(User user) {
		id = user.getId();
		name = user.getName();
		birthday = user.getBirthday();
		gender = user.getGender();
		login = user.getLogin();
		causes = user.getCauses();
		picture = new PictureDTO(user.getPicture());
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

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Set<SocialCause> getCauses() {
		return causes;
	}

	public void setCauses(Set<SocialCause> causes) {
		this.causes = causes;
	}

	public PictureDTO getPicture() {
		return picture;
	}

	public void setPictures(PictureDTO picture) {
		this.picture = picture;
	}

}
