package br.com.grupocesw.easyong.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class AuthResponse {

    @NotEmpty
    private final String accessToken;

    @Builder.Default
    private String tokenType = "Bearer";

}