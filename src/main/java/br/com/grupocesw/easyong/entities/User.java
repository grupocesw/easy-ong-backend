package br.com.grupocesw.easyong.entities;

import br.com.grupocesw.easyong.enums.AuthProvider;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "username"})
@Builder
@ToString(of = { "id", "username" })
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username", nullable = false, length = 100, unique = true)
	private String username;

	@Column(name = "password", nullable = false, length = 100)
	private String password;

	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime updatedAt;

	@Builder.Default
	@Column(name = "locked")
	private Boolean locked = false;

	@Builder.Default
	@Column(name = "enabled")
	private Boolean enabled = false;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Picture picture;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Person person;

	@Builder.Default
	@NotNull
	@Enumerated(EnumType.STRING)
	private AuthProvider provider = AuthProvider.local;

	private String providerId;

	@ManyToMany(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@OneToMany(targetEntity = ConfirmationToken.class, mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<ConfirmationToken> tokens;

	@ManyToMany(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinTable(name = "user_social_causes", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "social_cause_id"))
	private Set<SocialCause> causes;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinTable(name = "user_favorite_ngos", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "ngo_id"))
	private Set<Ngo> favoriteNgos;

	@JsonIgnore
	@ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
	private Set<Notification> notifications;

	@OneToMany(targetEntity = AppContact.class, mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<AppContact> appContacts;

	public User(User user) {
		this.id = user.id;
		this.username = user.username;
		this.password = user.password;
		this.setPerson(user.person);
		this.causes = user.causes;
		this.roles = user.roles;
		this.createdAt = user.getCreatedAt();
		this.updatedAt = user.getUpdatedAt();
	}

	public User(String username, String password, Person person) {
		this.username = username;
		this.password = password;
		this.person = person;
	}

	public Picture getPicture() {
		if (picture == null)
			return Picture.builder()
					.url(Picture.noImage)
					.build();

		return picture;
	}

	public String getUsername() {
		if (username != null && !username.isEmpty())
			return username.trim().toLowerCase();

		return username;
	}

	public String getPassword() {
		if (username != null && !password.isEmpty())
			return password.trim();

		return password;
	}

}
