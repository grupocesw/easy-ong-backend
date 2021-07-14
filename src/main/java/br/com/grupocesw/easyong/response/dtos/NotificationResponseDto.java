package br.com.grupocesw.easyong.response.dtos;

import br.com.grupocesw.easyong.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseDto {

    private static final long serialVersionUID = 1L;

    private Long id;
    private NotificationType type;
    private PictureResponseDto picture;
    private String title;
    private String description;

}
