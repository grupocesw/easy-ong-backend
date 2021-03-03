package br.com.grupocesw.easyong.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "pictures")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Picture implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final String path = "/api/pictures/";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;

	@OneToOne(mappedBy = "picture", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "picture_id")
	private User user;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "pictures")
	private Set<Ngo> ngos = new HashSet<>();
	
	public Picture (String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return ServletUriComponentsBuilder
		.fromCurrentContextPath()
		.build()
		.toUriString()
		.concat(path)
		.concat(this.name);
	}
	
	public static String getNoImageUrl() {
		return ServletUriComponentsBuilder
		.fromCurrentContextPath()
		.build()
		.toUriString()
		.concat(path)
		.concat("no_image.png");
	}
}
