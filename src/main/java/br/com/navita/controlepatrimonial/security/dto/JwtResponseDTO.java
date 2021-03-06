package br.com.navita.controlepatrimonial.security.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * The type Jwt response dto.
 */
@Data
@ApiModel(value = "Response Token")
public class JwtResponseDTO {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;

    /**
     * Instantiates a new Jwt response dto.
     *
     * @param jwt      the jwt
     * @param id       the id
     * @param username the username
     * @param email    the email
     */
    public JwtResponseDTO(String jwt, Long id, String username, String email) {
        this.token = jwt;
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
