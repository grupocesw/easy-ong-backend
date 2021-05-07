package br.com.grupocesw.easyong.request.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.grupocesw.easyong.entities.Address;
import br.com.grupocesw.easyong.entities.Street;
import lombok.Data;

@Data
public class AddressCreateRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Size(max = 10, message = "Number must contain a maximum of 10 digits")
	private Integer number;
	
	@Size(max = 255, message = "Complement must contain a maximum of 255 digits")
	private String complement;

	@Size(max = 12, message = "Latitude must contain a maximum of 12 digits")
	private String latitude;

	@Size(max = 12, message = "Longitude must contain a maximum of 12 digits")
	private String longitude;

	@NotNull(message = "Street required")
	private Street street;

    public Address build() {
    	return Address.builder()
    		.number(number)
    		.complement(complement)
    		.latitude(latitude)
    		.longitude(longitude)
    		.street(street)
			.build();
    }

}