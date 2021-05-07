package br.com.grupocesw.easyong.request.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.grupocesw.easyong.entities.City;
import br.com.grupocesw.easyong.entities.District;
import br.com.grupocesw.easyong.entities.Street;
import br.com.grupocesw.easyong.entities.ZipCode;
import lombok.Data;

@Data
public class StreetCreateRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Name required")
	@Size(min = 3, max = 255, message = "Name must contain between 3 and 255 characters")
	private String name;
	
	@NotEmpty(message = "Cep required")
	@Size(min = 8, max = 8, message = "Cep must contain 8 digits")
	private String cep;
	
	@NotEmpty(message = "District required")
	private String districtName;
	
	@NotNull(message = "City required")
	private City city;

    public Street build() {
    	
    	return Street.builder()
    		.name(name)
    		.zipCode(
    			ZipCode.builder()
    				.number(cep).build())
    		.district(
    			District.builder()
    				.name(districtName)
    				.city(city)
    				.build()
    		).build();
    }

}