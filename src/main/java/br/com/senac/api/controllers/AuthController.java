package br.com.senac.api.controllers;

import br.com.senac.api.dtos.LoginRequestDto;
import br.com.senac.api.dtos.TokenResponseDto;
import br.com.senac.api.seguranca.JwtService;
import br.com.senac.api.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto) {
        try {
            Authentication autenticacao = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getSenha()));

            String token = jwtService.gerarToken(autenticacao.getName());
            return ResponseEntity.ok(new TokenResponseDto(token));
        } catch (org.springframework.security.core.AuthenticationException e) {
            return ResponseEntity
                    .status(401)
                    .body(ResponseUtil.parserMensagem("Login ou senha inválidos!"));
        }
    }
}
