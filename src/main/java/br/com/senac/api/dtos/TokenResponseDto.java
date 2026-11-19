package br.com.senac.api.dtos;

public class TokenResponseDto {
    private String token;
    private String tipo = "Bearer";

    public TokenResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }
}
