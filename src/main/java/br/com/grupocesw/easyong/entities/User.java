package br.com.grupocesw.easyong.entities;

import java.io.Serializable;
import java.time.LocalDate;
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
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.grupocesw.easyong.enumerations.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	public User(User user) {
		this.id = user.id;
		this.username = user.username;
		this.password = user.password;
		this.birthday = user.birthday;
		this.gender = user.gender;
		this.causes = user.causes;
		this.roles = user.roles;
		this.createdAt = user.getCreatedAt();
		this.updatedAt = user.getUpdatedAt();
		this.checkedAt = user.getCheckedAt();
	}

	public User(String name, String username, String password) {
		this.name = name;
		this.username = username;
		this.password = password;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Name required")
	@Size(min = 3, max = 100, message = "Name must contain between 3 and 100 characters")
	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "birthday", nullable = true)
	private LocalDate birthday;

	@Column(name = "gender", nullable = true)
	private Gender gender;

	@Email
	@NotEmpty(message = "Username required")
	@Size(min = 3, max = 100, message = "Username must contain between 3 and 100 characters")
	@Column(name = "username", nullable = false, length = 100, unique = true)
	private String username;

	@NotEmpty(message = "Password required")
	@Size(min = 3, max = 100, message = "Password must contain between 3 and 100 characters")
	private String password;

	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "created_at", columnDefinition = "TIMESTAMP")
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "updated_at", columnDefinition = "TIMESTAMP")
	private LocalDateTime updatedAt;

	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "checked_at", columnDefinition = "TIMESTAMP", nullable = true)
	private LocalDateTime checkedAt;

	@OneToOne(optional = true)
	private Picture picture;

	@ManyToMany
	@JoinTable(name = "user_social_causes", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "social_cause_id"))
	private Set<SocialCause> causes;

	@ManyToMany
	@JoinTable(name = "user_favorite_ngos", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "ngo_id"))
	private Set<Ngo> favoriteNgos;

	@ManyToMany
	@JoinTable(name = "user_notifications", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "notification_id"))
	private Set<Notification> notifications;

	@OneToMany(targetEntity = NgoSuggestion.class, mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<NgoSuggestion> NgoSuggestions;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
}
