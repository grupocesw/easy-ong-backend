package br.com.grupocesw.easyong.dtos;

import java.io.Serializable;

import br.com.grupocesw.easyong.entities.Picture;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PictureDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String url;

	public PictureDTO(Picture picture) {
		url = (picture == null) ? Picture.getNoImageUrl() : picture.getUrl();
	}
}
