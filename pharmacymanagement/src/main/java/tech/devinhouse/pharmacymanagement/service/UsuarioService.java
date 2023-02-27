package tech.devinhouse.pharmacymanagement.service;

import org.springframework.stereotype.Service;
import tech.devinhouse.pharmacymanagement.controller.dto.UsuarioRequest;
import tech.devinhouse.pharmacymanagement.controller.dto.UsuarioResponse;
import tech.devinhouse.pharmacymanagement.dataprovider.entity.UsuarioEntity;
import tech.devinhouse.pharmacymanagement.dataprovider.repository.UsuarioRepository;
import tech.devinhouse.pharmacymanagement.exception.BadRequestException;
import tech.devinhouse.pharmacymanagement.exception.NotFoundException;
import tech.devinhouse.pharmacymanagement.exception.ServerSideException;

import java.util.List;
import java.util.Objects;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponse encontrarUsuario(UsuarioRequest usuarioRequest) {
        List<UsuarioEntity> usuarioEntities = usuarioRepository.findAll();

        UsuarioResponse usuarioResponse = new UsuarioResponse();
        for (UsuarioEntity usuarioEntity : usuarioEntities) {
            if(Objects.equals(usuarioRequest.getEmail(), usuarioEntity.getEmail())
                    && Objects.equals(usuarioRequest.getSenha(), usuarioEntity.getSenha())
            ) {
                usuarioResponse.setId(usuarioEntity.getId());
            }
        }

        if(usuarioResponse.getId() == null) {
            throw new NotFoundException("Usuário não encontrado com os dados informados!");
        }

        return usuarioResponse;

    }

    public UsuarioResponse criarNovoUsuario(UsuarioRequest usuarioRequest) {
        try {
            List<UsuarioEntity> usuarioEntities = usuarioRepository.findAll();

            for (UsuarioEntity usuarioEntity : usuarioEntities) {
                if(Objects.equals(usuarioRequest.getEmail(), usuarioEntity.getEmail())
                ) {
                    throw new BadRequestException("Já existe um usuário cadastrado com o email informado!");
                }
            }

            UsuarioEntity usuarioEntity = usuarioRepository.save(new UsuarioEntity(usuarioRequest.getEmail()
                    , usuarioRequest.getSenha()
            ));

            return new UsuarioResponse(
                    usuarioEntity.getId()
            );
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerSideException("Erro ao cadasrtrar usuário, mensagem localizada: " + e.getLocalizedMessage());
        }

    }

}
