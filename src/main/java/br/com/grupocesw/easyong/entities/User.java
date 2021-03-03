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
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.grupocesw.easyong.enumerations.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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
	@NotEmpty(message="Username required")
	@Size(min = 3, max = 100, message = "Username must contain between 3 and 100 characters")
	@Column(name = "username", nullable = false, length = 100, unique = true)
	private String username;
	
	@NotEmpty(message="Password required")
	@Size(min = 3, max = 100, message = "Password must contain between 3 and 100 characters")
	private String password;

	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "created_at", columnDefinition = "TIMESTAMP")
	private LocalDateTime createAt;
	
	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "updated_at", columnDefinition = "TIMESTAMP")
	private LocalDateTime updateAt;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "checked_at", columnDefinition = "TIMESTAMP", nullable = true)
	private LocalDateTime checkedAt;
	
	@OneToOne(optional = true)
	private Picture picture;
	
	@ManyToMany
	@JoinTable(name = "user_social_causes", joinColumns= @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "social_cause_id"))
	private Set<SocialCause> causes = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name = "user_favorite_ngos", joinColumns= @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "ngo_id"))
	private Set<Ngo> favoriteNgos = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name = "user_notifications", joinColumns= @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "notification_id"))
	private Set<Notification> notifications = new HashSet<>();
	
	@OneToMany(targetEntity=NgoSuggestion.class, mappedBy="user",cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<NgoSuggestion> NgoSuggestions = new HashSet<>();
}
