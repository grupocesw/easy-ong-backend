package br.com.grupocesw.easyong.response.dtos;

import br.com.grupocesw.easyong.entities.Address;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddressResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Integer number;
	private String street;
	private String complement;
	private String zipCode;
	private String latitude;
	private String longitude;
	private String district;
	private String city;

	public AddressResponseDto(Address address) {
		id = address.getId();
		number = address.getNumber();
		street = address.getStreet();
		complement = address.getComplement();
		zipCode = address.getZipCode();
		latitude = address.getLatitude();
		longitude = address.getLongitude();
		district = address.getDistrict();
		city = String.format("%s - %s", address.getCity().getName(), address.getCity().getState().getAbbreviation());
	}
}
