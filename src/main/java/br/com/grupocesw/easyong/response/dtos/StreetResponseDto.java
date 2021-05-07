package br.com.grupocesw.easyong.response.dtos;

import java.io.Serializable;

import br.com.grupocesw.easyong.entities.Street;
import lombok.Data;

@Data
public class StreetResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String cep;
	private String district;
	private String city;

	public StreetResponseDto(Street street) {
		id = street.getId();
		name = street.getName();
		cep = street.getZipCode().getNumber();
		district = street.getDistrict().getName();
		city = street.getDistrict().getCity().getName();
	}
}
