package br.com.grupocesw.easyong.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.enumerations.Gender;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private LocalDate birthday;
	private Gender gender;
	private String username;
	private Set<SocialCause> causes = new HashSet<>();
	private Set<Long> favoriteNgoIds = new HashSet<>();
	private PictureDTO picture;

	public UserDTO() {}

	public UserDTO(User user) {
		id = user.getId();
		name = user.getName();
		birthday = user.getBirthday();
		gender = user.getGender();
		username = user.getUsername();
		causes = user.getCauses();
		picture = user.getPicture() != null ? new PictureDTO(user.getPicture()) : null;
		
		for (Ngo ngo: user.getFavoriteNgos()) {
			if (ngo.getActivated()) {
				favoriteNgoIds.add(ngo.getId());
			}			
	    }
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<Long> getFavoriteNgoIds() {
		return favoriteNgoIds;
	}

	public void setFavoriteNgoIds(Set<Long> favoriteNgoIds) {
		this.favoriteNgoIds = favoriteNgoIds;
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
