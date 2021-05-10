package br.com.grupocesw.easyong.response.dtos;

import java.io.Serializable;

import br.com.grupocesw.easyong.entities.State;
import lombok.Data;

@Data
public class StateResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String abbreviation;
	private String country;

	public StateResponseDto(State state) {
		id = state.getId();
		name = state.getName();
		abbreviation = state.getAbbreviation();
		country = String.format("%s - %s", state.getCountry().getName(), state.getCountry().getAbbreviation());
	}
}
