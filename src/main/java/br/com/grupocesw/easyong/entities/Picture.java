package br.com.grupocesw.easyong.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
public class Picture {
	
	private static final String path = "/api/pictures/";	
	public static final String noImage = "no_image.png";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "url", nullable = false, columnDefinition = "TEXT")
	private String url;

	@OneToOne(mappedBy = "picture")
	@JoinColumn(name = "picture_id")
	@ToString.Exclude
	private User user;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "pictures")
	private Set<Ngo> ngos;
	
	public Picture (String url) {
		this.url = url;
	}
	
	public String getUrl() {
		if (!PictureUtil.isURL(url)) {
			String pictureName = (url == null) ? noImage : url;			
			return PictureUtil.getServerUrl(path, pictureName);
		}
		
		return url;
	}

}
