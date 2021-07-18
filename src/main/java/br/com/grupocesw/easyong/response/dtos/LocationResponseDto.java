package br.com.grupocesw.easyong.response.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String title;
	private String description;
	private String address;
	private String latitude;
	private String longitude;

}
