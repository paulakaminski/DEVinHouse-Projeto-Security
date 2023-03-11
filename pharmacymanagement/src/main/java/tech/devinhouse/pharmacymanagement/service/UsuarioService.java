package tech.devinhouse.pharmacymanagement.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tech.devinhouse.pharmacymanagement.controller.dto.UsuarioRequest;
import tech.devinhouse.pharmacymanagement.controller.dto.UsuarioResponse;
import tech.devinhouse.pharmacymanagement.dataprovider.model.UsuarioModel;
import tech.devinhouse.pharmacymanagement.dataprovider.repository.UsuarioRepository;
import tech.devinhouse.pharmacymanagement.exception.BadRequestException;
import tech.devinhouse.pharmacymanagement.exception.NotFoundException;
import tech.devinhouse.pharmacymanagement.exception.ServerSideException;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponse encontrarUsuario(UsuarioRequest usuarioRequest) {
        List<UsuarioModel> usuarioEntities = usuarioRepository.findAll();

        UsuarioResponse usuarioResponse = new UsuarioResponse();
        for (UsuarioModel usuarioModel : usuarioEntities) {
            if(Objects.equals(usuarioRequest.getEmail(), usuarioModel.getEmail())
                    && Objects.equals(usuarioRequest.getSenha(), usuarioModel.getSenha())
            ) {
                usuarioResponse.setId(usuarioModel.getId());
            }
        }

        if(usuarioResponse.getId() == null) {
            throw new NotFoundException("Usuário não encontrado com os dados informados!");
        }

        return usuarioResponse;

    }

    public UsuarioResponse criarNovoUsuario(UsuarioRequest usuarioRequest) {
        try {
            List<UsuarioModel> usuarioEntities = usuarioRepository.findAll();

            for (UsuarioModel usuarioModel : usuarioEntities) {
                if(Objects.equals(usuarioRequest.getEmail(), usuarioModel.getEmail())
                ) {
                    throw new BadRequestException("Já existe um usuário cadastrado com o email informado!");
                }
            }

            usuarioRequest.setSenha(new BCryptPasswordEncoder().encode(usuarioRequest.getSenha()));

            UsuarioModel usuarioModel = usuarioRepository.save(new UsuarioModel(usuarioRequest.getEmail()
                    , usuarioRequest.getSenha()
            ));

            return new UsuarioResponse(
                    usuarioModel.getId()
            );
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerSideException("Erro ao cadasrtrar usuário, mensagem localizada: " + e.getLocalizedMessage());
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioModel user = this.usuarioRepository.findUserByLogin(username);

        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado.");
        }

        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}
