package br.com.grupocesw.easyong.payloads;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private String id;
    private String name;
    private String username;   
    private String profilePicture;
}
