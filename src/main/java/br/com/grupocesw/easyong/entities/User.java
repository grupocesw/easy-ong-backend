package br.com.grupocesw.easyong.entities;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@PreAuthorize("hasRole('ADMIN')")
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "username"})
@Builder
@ToString
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

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

	@ManyToMany(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinTable(name = "user_favorite_ngos", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "ngo_id"))
	private Set<Ngo> favoriteNgos;

	@ManyToMany(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinTable(name = "user_notifications", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "notification_id"))
	private Set<Notification> notifications;

	@OneToMany(targetEntity = NgoSuggestion.class, mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<NgoSuggestion> NgoSuggestions;

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toSet());
	}
}
