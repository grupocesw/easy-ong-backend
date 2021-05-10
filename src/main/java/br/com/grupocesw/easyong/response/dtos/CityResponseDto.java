package br.com.grupocesw.easyong.response.dtos;

import java.io.Serializable;

import br.com.grupocesw.easyong.entities.City;
import lombok.Data;

@Data
public class CityResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String state;

	public CityResponseDto(City city) {
		id = city.getId();
		name = city.getName();
		state = String.format("%s - %s", city.getState().getName(), city.getState().getAbbreviation());
	}
}
