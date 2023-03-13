package tech.devinhouse.pharmacymanagement.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.pharmacymanagement.controller.dto.JwtResponse;
import tech.devinhouse.pharmacymanagement.controller.dto.UsuarioRequest;
import tech.devinhouse.pharmacymanagement.controller.dto.UsuarioResponse;
import tech.devinhouse.pharmacymanagement.padroes.DefaultResponse;
import tech.devinhouse.pharmacymanagement.security.JwtTokenProvider;
import tech.devinhouse.pharmacymanagement.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUsuario (@RequestBody UsuarioRequest usuarioRequest) throws Exception {

        authenticate(usuarioRequest.getEmail(), usuarioRequest.getSenha());

        final UserDetails userDetails = usuarioService.loadUserByUsername(usuarioRequest.getEmail());

        final String token = jwtTokenProvider.generateToken(userDetails.getUsername());

        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }

    private void authenticate(String login, String senha) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, senha));
        } catch (BadCredentialsException e) {
            throw new Exception("Credenciais inválidas", e);
        }
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
