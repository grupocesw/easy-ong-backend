package br.com.grupocesw.easyong.entities;

import br.com.grupocesw.easyong.utils.AppUtil;
import br.com.grupocesw.easyong.utils.PictureUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "pictures")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString(of = { "id", "url" })
public class Picture implements Serializable {
	
	private static final String path = "/api/v1/pictures/";
	public static final String noImage = "noImage.png";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "url", nullable = false, columnDefinition = "TEXT")
	private String url;

	@Transient
	private String name;

	@JsonIgnore
	@OneToOne(mappedBy = "picture")
	@JoinColumn(name = "picture_id")
	private User user;
	
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToMany(mappedBy = "pictures", cascade = {
		CascadeType.DETACH,
		CascadeType.REFRESH,
		CascadeType.PERSIST,
		CascadeType.MERGE })
	private Set<Ngo> ngos;
	
	public String getUrl() {
		if (!PictureUtil.isURL(url)) {
			String pictureName = (url == null) ? noImage : url;			
			return AppUtil.getRootUrlAppConcatPath(path + pictureName);
		}
		
		return url;
	}

	public String getName() {
		return url;
	}
}
