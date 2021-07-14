package br.com.grupocesw.easyong.entities;

import br.com.grupocesw.easyong.security.UserPrincipal;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "ngos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Proxy(lazy = false)
@EqualsAndHashCode(of = {"id", "cnpj"})
@ToString(of = { "id", "cnpj", "name", "activated" })
public class Ngo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "cnpj", length = 14, unique = true, nullable = false)
	private String cnpj;
	
	@Column(name = "description", columnDefinition="TEXT")
	private String description;
	
	@Builder.Default
	@Column(name = "activated", nullable = false, columnDefinition="BOOLEAN DEFAULT true")
	private Boolean activated = true;

	@Transient
	private boolean favorited;

	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime updatedAt;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;
	
	@JsonProperty(required = true)
	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinTable(name = "ngo_contacts", joinColumns= @JoinColumn(name = "ngo_id"), inverseJoinColumns = @JoinColumn(name = "contact_id"))
	private Set<Contact> contacts;
	
	@OneToMany(targetEntity = NgoMoreInformation.class, cascade=CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="ngo_id")
	private Set<NgoMoreInformation> moreInformations;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinTable(name = "ngo_social_causes", joinColumns= @JoinColumn(name = "ngo_id"), inverseJoinColumns = @JoinColumn(name = "social_cause_id"))
	private Set<SocialCause> causes;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinTable(name = "ngo_pictures", joinColumns= @JoinColumn(name = "ngo_id"), inverseJoinColumns = @JoinColumn(name = "picture_id"))
	private Set<Picture> pictures;
	
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToMany(mappedBy = "favoriteNgos", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<User> users;

	public boolean getFavorited() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken) && this.getUsers() != null) {
			UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

			for (User user: this.getUsers()) {
				if (userPrincipal.getEmail().equals(user.getUsername()))
					favorited = true;
			}
		}

		return favorited;
	}
}
