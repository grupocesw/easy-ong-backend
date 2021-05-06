package br.com.grupocesw.easyong.entities;

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
import lombok.Builder;
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
@Builder
@ToString
public class Picture {
	
	private static final String path = "/api/pictures/";
	
	public static final String noImage = "no_image.png";
	
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
	private Set<Ngo> ngos;
	
	public Picture (String name) {
		this.name = name;
	}
	
	public String getUrl() {
		
		String pictureName = (name == null) ? noImage : name;
		
		return ServletUriComponentsBuilder
			.fromCurrentContextPath()
			.build()
			.toUriString()
			.concat(path)
			.concat(pictureName);
	}

}
