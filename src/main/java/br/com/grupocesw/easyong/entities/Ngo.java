package br.com.grupocesw.easyong.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Ngo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="Name required")
	@Size(min = 3, max = 100, message = "Name must contain between 3 and 100 characters")
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@NotEmpty(message="CNPJ required")
	@Size(min = 14, max = 14, message = "CNPJ must contain 14 digits")
	@Column(name = "cnpj", nullable = false, length = 14)
	private Integer cnpj;
	
	@NotEmpty(message="Name required")
	@Column(name = "description", nullable = false, columnDefinition="TEXT")
	private String description;
	
	@Column(name = "activated", nullable = false, columnDefinition="boolean default false")
	private Boolean activated;

	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "create_at", columnDefinition = "TIMESTAMP")
	private LocalDateTime createAt;
	
	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "update_at", columnDefinition = "TIMESTAMP")
	private LocalDateTime updateAt;
	
	@OneToOne(mappedBy = "ngo", cascade = CascadeType.ALL)
	private Address address;
	
	@OneToMany(targetEntity=Contact.class, mappedBy="ngo",cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Contact> contacts;
	
	@OneToMany(targetEntity=MoreInformationNog.class, mappedBy="ngo",cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<MoreInformationNog> moreInformations;
	
	@ManyToMany
	@JoinTable(name = "ngo_social_cause", joinColumns= @JoinColumn(name = "ngo_id"), inverseJoinColumns = @JoinColumn(name = "social_cause_id"))
	private Set<SocialCause> causes;
	
	@ManyToMany
	@JoinTable(name = "ngo_picture", joinColumns= @JoinColumn(name = "ngo_id"), inverseJoinColumns = @JoinColumn(name = "picture_id"))
	private Set<Picture> pictures;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "favoriteNgos")
	private Set<User> users;

	public Ngo() {}
	
	public Ngo(Long id, String name, Integer cnpj, String description) {
		super();
		this.id = id;
		this.name = name;
		this.cnpj = cnpj;
		this.description = description;
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
	
	public Integer getCnpj() {
		return cnpj;
	}

	public void setCnpj(Integer cnpj) {
		this.cnpj = cnpj;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
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
	
	public Set<User> getUsers() {
		return users;
	}
	
	public Set<Contact> getContacts() {
		return contacts;
	}
	
	public Set<Picture> getPictures() {
		return pictures;
	}
	
	public Set<MoreInformationNog> getMoreInformations() {
		return moreInformations;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ngo other = (Ngo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
