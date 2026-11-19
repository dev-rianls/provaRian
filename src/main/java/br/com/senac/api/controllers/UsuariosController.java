package br.com.senac.api.controllers;

import br.com.senac.api.dtos.UsuariosRequestDto;
import br.com.senac.api.services.UsuariosService;
import br.com.senac.api.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {
    @Autowired
    private UsuariosService usuariosService;

    @PostMapping("/criar")
    public ResponseEntity<?> criar(@RequestBody UsuariosRequestDto usuario) {
        try {
            var criado = usuariosService.criar(usuario);
            return ResponseEntity
                    .status(201)
                    .body(java.util.Map.of("id", criado.getId(), "login", criado.getLogin()));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ResponseUtil.parserMensagem(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(ResponseUtil.parserMensagem(e.getMessage()));
        }
    }
}
