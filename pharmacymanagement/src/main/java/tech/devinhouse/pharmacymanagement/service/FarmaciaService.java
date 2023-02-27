package tech.devinhouse.pharmacymanagement.service;

import org.springframework.stereotype.Service;
import tech.devinhouse.pharmacymanagement.controller.dto.EnderecoResponse;
import tech.devinhouse.pharmacymanagement.controller.dto.FarmaciaRequest;
import tech.devinhouse.pharmacymanagement.controller.dto.FarmaciaResponse;
import tech.devinhouse.pharmacymanagement.dataprovider.entity.EnderecoEntity;
import tech.devinhouse.pharmacymanagement.dataprovider.entity.FarmaciaEntity;
import tech.devinhouse.pharmacymanagement.dataprovider.repository.EnderecoRepository;
import tech.devinhouse.pharmacymanagement.dataprovider.repository.FarmaciaRepository;
import tech.devinhouse.pharmacymanagement.exception.BadRequestException;
import tech.devinhouse.pharmacymanagement.exception.NotFoundException;
import tech.devinhouse.pharmacymanagement.exception.ServerSideException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FarmaciaService {

    private final FarmaciaRepository farmaciaRepository;
    private final EnderecoRepository enderecoRepository;
    private final CepService cepService;

    public FarmaciaService(FarmaciaRepository farmaciaRepository, EnderecoRepository enderecoRepository, CepService cepService) {
        this.farmaciaRepository = farmaciaRepository;
        this.enderecoRepository = enderecoRepository;
        this.cepService = cepService;
    }

    public List<FarmaciaResponse> encontrarTodasAsFarmacias() {
        try {
            List<FarmaciaEntity> entityList = farmaciaRepository.findAll();

            List<FarmaciaResponse> responseList = new ArrayList<>();

            for (FarmaciaEntity farmaciaEntity:entityList) {
                responseList.add(
                        new FarmaciaResponse(farmaciaEntity.getRazaoSocial()
                                , farmaciaEntity.getCnpj()
                                , farmaciaEntity.getNomeFantasia()
                                , farmaciaEntity.getEmail()
                                , farmaciaEntity.getTelefoneFixo()
                                , farmaciaEntity.getTelefoneCelular()
                                , new EnderecoResponse(farmaciaEntity.getEnderecoEntity().getCep()
                                , farmaciaEntity.getEnderecoEntity().getLogradouro()
                                , farmaciaEntity.getEnderecoEntity().getNumero()
                                , farmaciaEntity.getEnderecoEntity().getBairro()
                                , farmaciaEntity.getEnderecoEntity().getLocalidade()
                                , farmaciaEntity.getEnderecoEntity().getUf()
                                , farmaciaEntity.getEnderecoEntity().getComplemento()))
                );

            }

            return responseList;

        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerSideException("Erro ao pesquisar farmácias, mensagem localizada: " + e.getLocalizedMessage());
        }

    }

    public FarmaciaResponse encontrarFarmaciaPorId(Long id) {
        try {
            FarmaciaEntity farmaciaEntity = farmaciaRepository.findById(id)
                    .orElseThrow(()->new NotFoundException("Farmácia não encontrada pelo id: " + id));

            return new FarmaciaResponse(farmaciaEntity.getRazaoSocial()
                    , farmaciaEntity.getCnpj()
                    , farmaciaEntity.getNomeFantasia()
                    , farmaciaEntity.getEmail()
                    , farmaciaEntity.getTelefoneFixo()
                    , farmaciaEntity.getTelefoneCelular()
                    , new EnderecoResponse(farmaciaEntity.getEnderecoEntity().getCep()
                    , farmaciaEntity.getEnderecoEntity().getLogradouro()
                    , farmaciaEntity.getEnderecoEntity().getNumero()
                    , farmaciaEntity.getEnderecoEntity().getBairro()
                    , farmaciaEntity.getEnderecoEntity().getLocalidade()
                    , farmaciaEntity.getEnderecoEntity().getUf()
                    , farmaciaEntity.getEnderecoEntity().getComplemento())
            );
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerSideException("Erro ao pesquisar farmácia, mensagem localizada: " + e.getLocalizedMessage());
        }

    }

    public FarmaciaResponse cadastrarNovaFarmacia(FarmaciaRequest farmaciaRequest) {
        try {
            validarSeJaExisteFarmaciaCadastrada(farmaciaRequest);

            EnderecoResponse enderecoResponse = cepService.buscaCep(farmaciaRequest.getCep());
            enderecoResponse.setNumero(farmaciaRequest.getNumero());
            enderecoResponse.setComplemento(farmaciaRequest.getComplemento());

            EnderecoEntity enderecoEntity = enderecoRepository.save(
                    new EnderecoEntity(enderecoResponse.getCep()
                            , enderecoResponse.getLogradouro()
                            , enderecoResponse.getNumero()
                            , enderecoResponse.getBairro()
                            , enderecoResponse.getLocalidade()
                            , enderecoResponse.getUf()
                            , enderecoResponse.getComplemento()
                            , farmaciaRequest.getLatitude()
                            , farmaciaRequest.getLongitude())
            );

            FarmaciaEntity farmaciaEntity = farmaciaRepository.save(
                    new FarmaciaEntity(farmaciaRequest.getRazaoSocial()
                            , farmaciaRequest.getCnpj()
                            , farmaciaRequest.getNomeFantasia()
                            , farmaciaRequest.getEmail()
                            , farmaciaRequest.getTelefoneFixo()
                            , farmaciaRequest.getTelefoneCelular()
                            , enderecoEntity)
            );

            return new FarmaciaResponse(farmaciaEntity.getRazaoSocial()
                    , farmaciaEntity.getCnpj()
                    , farmaciaEntity.getNomeFantasia()
                    , farmaciaEntity.getEmail()
                    , farmaciaEntity.getTelefoneFixo()
                    , farmaciaEntity.getTelefoneCelular()
                    , enderecoResponse
            );
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerSideException("Erro ao cadastrar farmácia, mensagem localizada: " + e.getLocalizedMessage());
        }

    }

    public FarmaciaResponse atualizarFarmaciaPorId(Long id, FarmaciaRequest farmaciaRequest) {
        try {
            FarmaciaEntity farmaciaEntity = farmaciaRepository.findById(id)
                    .orElseThrow(()->new NotFoundException("Farmácia não encontrada pelo id: " + id));

            if(farmaciaRequest.getRazaoSocial().isEmpty()
                    || farmaciaRequest.getCnpj().isEmpty()
                    || farmaciaRequest.getNomeFantasia().isEmpty()
                    || farmaciaRequest.getEmail().isEmpty()
                    || farmaciaRequest.getTelefoneCelular().isEmpty()
                    || farmaciaRequest.getCep().isEmpty()
                    || farmaciaRequest.getLatitude().isEmpty()
                    || farmaciaRequest.getLongitude().isEmpty()) {
                throw new BadRequestException("Os campos de preenchimento obrigatório não podem ser nulos!");
            }

            EnderecoEntity enderecoEntity = enderecoRepository.findById(farmaciaEntity.getEnderecoEntity().getId()).get();

            EnderecoResponse enderecoResponse = cepService.buscaCep(farmaciaRequest.getCep());
            enderecoResponse.setNumero(farmaciaRequest.getNumero());
            enderecoResponse.setComplemento(farmaciaRequest.getComplemento());

            enderecoEntity.setCep(enderecoResponse.getCep());
            enderecoEntity.setLogradouro(enderecoResponse.getLogradouro());
            enderecoEntity.setNumero(enderecoResponse.getNumero());
            enderecoEntity.setBairro(enderecoResponse.getBairro());
            enderecoEntity.setLocalidade(enderecoResponse.getLocalidade());
            enderecoEntity.setUf(enderecoResponse.getUf());
            enderecoEntity.setComplemento(enderecoResponse.getComplemento());
            enderecoEntity.setLatitude(farmaciaRequest.getLatitude());
            enderecoEntity.setLongitude(farmaciaRequest.getLongitude());

            enderecoRepository.save(enderecoEntity);

            farmaciaEntity.setRazaoSocial(farmaciaRequest.getRazaoSocial());
            farmaciaEntity.setCnpj(farmaciaRequest.getCnpj());
            farmaciaEntity.setNomeFantasia(farmaciaRequest.getNomeFantasia());
            farmaciaEntity.setEmail(farmaciaRequest.getEmail());
            farmaciaEntity.setTelefoneFixo(farmaciaEntity.getTelefoneFixo());
            farmaciaEntity.setTelefoneCelular(farmaciaRequest.getTelefoneCelular());

            farmaciaRepository.save(farmaciaEntity);

            return new FarmaciaResponse(farmaciaEntity.getRazaoSocial()
                    , farmaciaEntity.getCnpj()
                    , farmaciaEntity.getNomeFantasia()
                    , farmaciaEntity.getEmail()
                    , farmaciaEntity.getTelefoneFixo()
                    , farmaciaEntity.getTelefoneCelular()
                    , enderecoResponse
            );
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerSideException("Erro ao atualizar cadastro de farmácia, mensagem localizada: " + e.getLocalizedMessage());
        }

    }

    public void deletarFarmaciaPorId(Long id) {
        try {
            FarmaciaEntity farmaciaEntity = farmaciaRepository.findById(id)
                    .orElseThrow(()->new NotFoundException("Farmácia não encontrada pelo id: " + id));

            EnderecoEntity enderecoEntity = enderecoRepository.findById(farmaciaEntity.getEnderecoEntity().getId()).get();

            farmaciaRepository.deleteById(id);

            enderecoRepository.deleteById(enderecoEntity.getId());
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerSideException("Erro ao deletar cadastro de farmácia, mensagem localizada: " + e.getLocalizedMessage());
        }

    }

    private void validarSeJaExisteFarmaciaCadastrada(FarmaciaRequest farmaciaRequest) {
        List<FarmaciaEntity> farmaciaEntities = farmaciaRepository.findAll();

        for (FarmaciaEntity farmaciaEntity:farmaciaEntities) {
            if (Objects.equals(farmaciaRequest.getCnpj(), farmaciaEntity.getCnpj())) {
                throw new BadRequestException("Já existe uma farmácia cadastrada com o cnpj informado!");
            }
            if (Objects.equals(farmaciaRequest.getCep(), farmaciaEntity.getEnderecoEntity().getCep())
                    && Objects.equals(farmaciaRequest.getNumero(), farmaciaEntity.getEnderecoEntity().getNumero())
                    && Objects.equals(farmaciaRequest.getComplemento(), farmaciaEntity.getEnderecoEntity().getComplemento())) {
                throw new BadRequestException("Já existe uma farmácia cadastrada no endereço informado!");
            }
        }
    }

}
