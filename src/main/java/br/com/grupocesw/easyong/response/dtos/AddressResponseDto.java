package br.com.grupocesw.easyong.response.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String title;
	private String description;
	private String number;
	private String street;
	private String complement;
	private String zipCode;
	private String latitude;
	private String longitude;
	private String district;
	private String city;

}
