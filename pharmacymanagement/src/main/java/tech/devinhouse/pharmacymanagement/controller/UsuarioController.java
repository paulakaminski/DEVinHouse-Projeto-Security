package tech.devinhouse.pharmacymanagement.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.pharmacymanagement.controller.dto.UsuarioRequest;
import tech.devinhouse.pharmacymanagement.controller.dto.UsuarioResponse;
import tech.devinhouse.pharmacymanagement.dataprovider.entity.UsuarioEntity;
import tech.devinhouse.pharmacymanagement.padroes.DefaultResponse;
import tech.devinhouse.pharmacymanagement.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public ResponseEntity<DefaultResponse> loginUsuario (@RequestBody UsuarioRequest usuarioRequest) {
        UsuarioResponse usuarioResponse = usuarioService.encontrarUsuario(usuarioRequest);

        return new ResponseEntity<>(
                new DefaultResponse<UsuarioResponse>(
                        HttpStatus.OK.value()
                        , "Usuário encontrado com sucesso!"
                        , usuarioResponse
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/cadastro")
    public ResponseEntity<DefaultResponse> cadastrarNovoUsuario (@Valid @RequestBody UsuarioRequest usuarioRequest) {
        UsuarioResponse usuarioResponse = usuarioService.criarNovoUsuario(usuarioRequest);

        return new ResponseEntity<>(
                new DefaultResponse<UsuarioResponse>(
                        HttpStatus.CREATED.value()
                        , "Usuário cadastrado com sucesso!"
                        , usuarioResponse
                ),
                HttpStatus.CREATED
        );
    }

}
