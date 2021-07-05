package br.com.grupocesw.easyong.response.dtos;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtAuthenticationResponseDto {

    private String username;
    private String accessToken;
    private String expiration;

    @Builder.Default
    private String tokenType = "Bearer";
}
