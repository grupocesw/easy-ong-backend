package br.com.grupocesw.easyong.response.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String state;

}
