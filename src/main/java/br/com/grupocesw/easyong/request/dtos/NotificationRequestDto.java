package br.com.grupocesw.easyong.request.dtos;

import br.com.grupocesw.easyong.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Type required")
	private NotificationType type;

	@NotEmpty(message = "Title required")
	private String title;

	private String description;

}