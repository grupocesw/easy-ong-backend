package br.com.grupocesw.easyong.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.grupocesw.easyong.enumerations.Gender;
import br.com.grupocesw.easyong.utilities.UserUtility;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="Name required")
	@Size(min = 3, max = 100, message = "Name must contain between 3 and 100 characters")
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@NotNull(message="Birthday required")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "birthday", nullable = false)
	private LocalDate birthday;
	
	@NotNull(message="Gender required")
	@Column(name = "gender", nullable = false)
	private Gender gender;
	
	@Pattern(regexp = "[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
	        + "[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
	        + "(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9]"
	        + "(?:[A-Za-z0-9-]*[A-Za-z0-9])?",
	        message = "The email format is incorrect")
	@NotEmpty(message="Login required")
	@Size(min = 3, max = 100, message = "Login must contain between 3 and 100 characters")
	@Column(name = "login", nullable = false, length = 100, unique = true)
	private String login;
	
	@NotEmpty(message="Password required")
	@Size(min = 3, max = 100, message = "Password must contain between 3 and 100 characters")
	private String password;

	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "create_at", columnDefinition = "TIMESTAMP")
	private LocalDateTime createAt;
	
	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "update_at", columnDefinition = "TIMESTAMP")
	private LocalDateTime updateAt;
	
	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "checked_at", columnDefinition = "TIMESTAMP")
	private LocalDateTime checkedAt;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Picture picture;
	
	@ManyToMany
	@JoinTable(name = "user_social_cause", joinColumns= @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "social_cause_id"))
	private Set<SocialCause> causes;
	
	@ManyToMany
	@JoinTable(name = "user_favorite_ngo", joinColumns= @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "ngo_id"))
	private Set<Ngo> favoriteNgos;
	
	@ManyToMany
	@JoinTable(name = "user_notification", joinColumns= @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "notification_id"))
	private Set<Notification> notifications;
	
	@OneToMany(targetEntity=NgoSuggestion.class, mappedBy="user",cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<NgoSuggestion> NgoSuggestions;

	public User() {}

	public User(Long id, String name, LocalDate birthday, Gender gender, String login, String password) {
		super();
		this.id = id;
		this.name = name;
		this.birthday = birthday;
		this.gender = gender;
		this.login = login;
		this.setPassword(password);
		this.causes = new HashSet<>();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		
		this.password = UserUtility.encryptPassword(password);
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

	public LocalDateTime getCheckedAt() {
		return checkedAt;
	}

	public void setCheckedAt(LocalDateTime checkedAt) {
		this.checkedAt = checkedAt;
	}
	
	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	public Set<SocialCause> getCauses() {
		return causes;
	}
	
	public Set<Ngo> getFavoriteNgos() {
		return favoriteNgos;
	}
	
	public Set<Notification> getNotifications() {
		return notifications;
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
