package br.com.grupocesw.easyong.dto;

import java.io.Serializable;

import br.com.grupocesw.easyong.entities.Picture;

public class PictureDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String url;

	public PictureDTO() {}

	public PictureDTO(Picture picture) {
		url = "http://localhost:8080/api/pictures/" + picture.getName();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
