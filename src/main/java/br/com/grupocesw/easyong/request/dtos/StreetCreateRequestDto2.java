package br.com.grupocesw.easyong.request.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.grupocesw.easyong.entities.City;
import br.com.grupocesw.easyong.entities.Country;
import br.com.grupocesw.easyong.entities.District;
import br.com.grupocesw.easyong.entities.State;
import br.com.grupocesw.easyong.entities.Street;
import br.com.grupocesw.easyong.entities.ZipCode;
import lombok.Data;

@Data
public class StreetCreateRequestDto2 implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Name required")
	@Size(min = 3, max = 255, message = "Name must contain between 3 and 255 characters")
	private String name;
	
	@NotEmpty(message = "Cep required")
	@Size(min = 8, max = 8, message = "Cep must contain 8 digits")
	private String cep;
	
	@NotEmpty(message = "District required")
	private String district;
	
	@NotEmpty(message = "City required")
	private String city;
	
	@NotEmpty(message = "State required")
	private String state;
	
	@NotEmpty(message = "State Abbreviation required")
	private String stateAbbreviation;
	
	@NotEmpty(message = "Country required")
	private String country;
	
	@NotEmpty(message = "Country Abbreviation required")
	private String countryAbbreviation;

    public Street build() {
    	
    	return Street.builder()
    		.name(name)
    		.zipCode(
    			ZipCode.builder()
    				.number(cep).build())
    		.district(
				District.builder()
					.name(district)
					.city(
						City.builder()
							.name(city)
							.state(
								State.builder()
								.name(state)
								.abbreviation(stateAbbreviation)
								.country(
									Country.builder()
										.name(country)
										.abbreviation(countryAbbreviation).build()
								).build()
						).build()
				).build()
    		).build();
    }

}