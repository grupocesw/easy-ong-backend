package br.com.grupocesw.easyong.request.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Size(max = 7, message = "Number must contain a maximum of 7 digits")
	private Integer number;

	@NotEmpty(message = "Title required")
	@Size(max = 255, message = "Title must contain a maximum of 255 digits")
	private String title;

	@NotEmpty(message = "Description required")
	@Size(max = 255, message = "Description must contain a maximum of 255 digits")
	private String description;

	@NotEmpty(message = "Street required")
	@Size(max = 255, message = "Street must contain a maximum of 255 digits")
	private String street;
	
	@Size(max = 255, message = "Complement must contain a maximum of 255 digits")
	private String complement;
	
	@Size(min = 8, max = 8, message = "Zip Code must contain 8 digits")
	private String zipCode;

	@Size(max = 12, message = "Latitude must contain a maximum of 12 digits")
	private String latitude;

	@Size(max = 12, message = "Longitude must contain a maximum of 12 digits")
	private String longitude;
	
	@Size(max = 12, message = "District must contain a maximum of 255 digits")
	private String district;
	
	@NotEmpty(message = "City ID required")
	private Long cityId;

}