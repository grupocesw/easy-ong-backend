package br.com.grupocesw.easyong.entities;

import java.io.Serializable;
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
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ngos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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
	private String cnpj;
	
	@NotEmpty(message="Name required")
	@Column(name = "description", nullable = false, columnDefinition="TEXT")
	private String description;
	
	@Column(name = "activated", nullable = false, columnDefinition="boolean default false")
	private Boolean activated = true;

	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "created_at", columnDefinition = "TIMESTAMP")
	private LocalDateTime createAt;
	
	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "updated_at", columnDefinition = "TIMESTAMP")
	private LocalDateTime updateAt;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;
	
	@ManyToMany
	@JoinTable(name = "ngo_contacts", joinColumns= @JoinColumn(name = "ngo_id"), inverseJoinColumns = @JoinColumn(name = "contact_id"))
	private Set<Contact> contacts = new HashSet<>();
	
	@OneToMany(targetEntity=MoreInformationNgo.class, mappedBy="ngo",cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<MoreInformationNgo> moreInformations = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name = "ngo_social_causes", joinColumns= @JoinColumn(name = "ngo_id"), inverseJoinColumns = @JoinColumn(name = "social_cause_id"))
	private Set<SocialCause> causes = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name = "ngo_pictures", joinColumns= @JoinColumn(name = "ngo_id"), inverseJoinColumns = @JoinColumn(name = "picture_id"))
	private Set<Picture> pictures = new HashSet<>();
	
	@JsonIgnore
	@ManyToMany(mappedBy = "favoriteNgos")
	private Set<User> users = new HashSet<>();

}
