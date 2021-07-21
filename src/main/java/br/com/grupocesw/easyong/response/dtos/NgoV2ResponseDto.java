package br.com.grupocesw.easyong.response.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NgoV2ResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String cnpj;
	private String description;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "America/Sao_Paulo")
	private LocalDateTime createdAt;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "America/Sao_Paulo")
	private LocalDateTime updatedAt;

	private boolean favorite;
	private SimpleContactResponseDto contacts;
	private Set<PictureResponseDto> pictures;
	private Set<NgoMoreInformationResponseDto> moreInformations;
	private Set<SocialCauseResponseDto> causes;
	private LocationResponseDto location;

}
