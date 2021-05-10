package br.com.grupocesw.easyong.response.dtos;

import java.io.Serializable;

import br.com.grupocesw.easyong.entities.Picture;
import lombok.Data;

@Data
public class PictureResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String url;

	public PictureResponseDto(Picture picture) {
		if(picture != null) {
			id = picture.getId();
			url = picture.getUrl();
		} else {
			url = (new Picture()).getUrl();
		}			
	}
}
