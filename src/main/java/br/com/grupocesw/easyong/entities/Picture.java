package br.com.grupocesw.easyong.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import br.com.grupocesw.easyong.utils.AppUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.grupocesw.easyong.utils.PictureUtil;
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
public class Picture implements Serializable {
	
	private static final String path = "/api/pictures/";	
	public static final String noImage = "no_image.png";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "url", nullable = false, columnDefinition = "TEXT")
	private String url;

	@Transient
	private String name;

	@OneToOne(mappedBy = "picture")
	@JoinColumn(name = "picture_id")
	@ToString.Exclude
	private User user;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "pictures")
	private Set<Ngo> ngos;
	
	public String getUrl() {
		if (!PictureUtil.isURL(url)) {
			String pictureName = (url == null) ? noImage : url;			
			return AppUtil.getRootUrlAppConcatPath(path + "/" + pictureName);
		}
		
		return url;
	}

	public String getName() {
		return url;
	}
}
