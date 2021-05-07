package br.com.grupocesw.easyong.response.dtos;


import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtAuthenticationResponseDto {

    @NonNull
    private String accessToken;
    private String tokenType = "Bearer";
}
