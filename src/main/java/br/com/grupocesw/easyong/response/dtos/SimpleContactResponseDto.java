package br.com.grupocesw.easyong.response.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleContactResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String website;
	private List<String> phones;

}
