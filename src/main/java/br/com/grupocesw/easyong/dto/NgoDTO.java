package br.com.grupocesw.easyong.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import br.com.grupocesw.easyong.entities.Address;
import br.com.grupocesw.easyong.entities.Contact;
import br.com.grupocesw.easyong.entities.MoreInformationNgo;
import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.entities.Picture;
import br.com.grupocesw.easyong.entities.SocialCause;

public class NgoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String description;
	private Address address;
	private Set<SocialCause> causes;
	private Set<Contact> contacts;
	private Set<MoreInformationNgo> moreInformations;
	private Set<PictureDTO> pictures = new HashSet<>();

	public NgoDTO() {}

	public NgoDTO(Ngo ngo) {
		id = ngo.getId();
		name = ngo.getName();
		description = ngo.getDescription();
		address = ngo.getAddress();
		causes = ngo.getCauses();
		contacts = ngo.getContacts();
		moreInformations = ngo.getMoreInformations();

		for (Picture p: ngo.getPictures()) {
			pictures.add(new PictureDTO(p));
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<SocialCause> getCauses() {
		return causes;
	}

	public void setCauses(Set<SocialCause> causes) {
		this.causes = causes;
	}

	public Set<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}

	public Set<MoreInformationNgo> getMoreInformations() {
		return moreInformations;
	}

	public void setMoreInformations(Set<MoreInformationNgo> moreInformations) {
		this.moreInformations = moreInformations;
	}

	public Set<PictureDTO> getPictures() {
		return pictures;
	}

	public void setPictures(Set<PictureDTO> pictures) {
		this.pictures = pictures;
	}

}
