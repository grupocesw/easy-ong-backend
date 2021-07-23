package br.com.grupocesw.easyong.response.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtAuthenticationResponseDto {

    private String subject;
    private String accessToken;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Sao_Paulo")
    private String expiration;

    @Builder.Default
    private String tokenType = "Bearer";
}
