package tech.devinhouse.pharmacymanagement.service;

import org.springframework.stereotype.Service;
import tech.devinhouse.pharmacymanagement.controller.dto.EnderecoResponse;
import tech.devinhouse.pharmacymanagement.controller.dto.FarmaciaRequest;
import tech.devinhouse.pharmacymanagement.controller.dto.FarmaciaResponse;
import tech.devinhouse.pharmacymanagement.dataprovider.model.EnderecoModel;
import tech.devinhouse.pharmacymanagement.dataprovider.model.FarmaciaModel;
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
            List<FarmaciaModel> entityList = farmaciaRepository.findAll();

            List<FarmaciaResponse> responseList = new ArrayList<>();

            for (FarmaciaModel farmaciaModel :entityList) {
                responseList.add(
                        new FarmaciaResponse(farmaciaModel.getRazaoSocial()
                                , farmaciaModel.getCnpj()
                                , farmaciaModel.getNomeFantasia()
                                , farmaciaModel.getEmail()
                                , farmaciaModel.getTelefoneFixo()
                                , farmaciaModel.getTelefoneCelular()
                                , new EnderecoResponse(farmaciaModel.getEnderecoModel().getCep()
                                , farmaciaModel.getEnderecoModel().getLogradouro()
                                , farmaciaModel.getEnderecoModel().getNumero()
                                , farmaciaModel.getEnderecoModel().getBairro()
                                , farmaciaModel.getEnderecoModel().getLocalidade()
                                , farmaciaModel.getEnderecoModel().getUf()
                                , farmaciaModel.getEnderecoModel().getComplemento()))
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
            FarmaciaModel farmaciaModel = farmaciaRepository.findById(id)
                    .orElseThrow(()->new NotFoundException("Farmácia não encontrada pelo id: " + id));

            return new FarmaciaResponse(farmaciaModel.getRazaoSocial()
                    , farmaciaModel.getCnpj()
                    , farmaciaModel.getNomeFantasia()
                    , farmaciaModel.getEmail()
                    , farmaciaModel.getTelefoneFixo()
                    , farmaciaModel.getTelefoneCelular()
                    , new EnderecoResponse(farmaciaModel.getEnderecoModel().getCep()
                    , farmaciaModel.getEnderecoModel().getLogradouro()
                    , farmaciaModel.getEnderecoModel().getNumero()
                    , farmaciaModel.getEnderecoModel().getBairro()
                    , farmaciaModel.getEnderecoModel().getLocalidade()
                    , farmaciaModel.getEnderecoModel().getUf()
                    , farmaciaModel.getEnderecoModel().getComplemento())
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

            EnderecoModel enderecoModel = enderecoRepository.save(
                    new EnderecoModel(enderecoResponse.getCep()
                            , enderecoResponse.getLogradouro()
                            , enderecoResponse.getNumero()
                            , enderecoResponse.getBairro()
                            , enderecoResponse.getLocalidade()
                            , enderecoResponse.getUf()
                            , enderecoResponse.getComplemento()
                            , farmaciaRequest.getLatitude()
                            , farmaciaRequest.getLongitude())
            );

            FarmaciaModel farmaciaModel = farmaciaRepository.save(
                    new FarmaciaModel(farmaciaRequest.getRazaoSocial()
                            , farmaciaRequest.getCnpj()
                            , farmaciaRequest.getNomeFantasia()
                            , farmaciaRequest.getEmail()
                            , farmaciaRequest.getTelefoneFixo()
                            , farmaciaRequest.getTelefoneCelular()
                            , enderecoModel)
            );

            return new FarmaciaResponse(farmaciaModel.getRazaoSocial()
                    , farmaciaModel.getCnpj()
                    , farmaciaModel.getNomeFantasia()
                    , farmaciaModel.getEmail()
                    , farmaciaModel.getTelefoneFixo()
                    , farmaciaModel.getTelefoneCelular()
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
            FarmaciaModel farmaciaModel = farmaciaRepository.findById(id)
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

            EnderecoModel enderecoModel = enderecoRepository.findById(farmaciaModel.getEnderecoModel().getId()).get();

            EnderecoResponse enderecoResponse = cepService.buscaCep(farmaciaRequest.getCep());
            enderecoResponse.setNumero(farmaciaRequest.getNumero());
            enderecoResponse.setComplemento(farmaciaRequest.getComplemento());

            enderecoModel.setCep(enderecoResponse.getCep());
            enderecoModel.setLogradouro(enderecoResponse.getLogradouro());
            enderecoModel.setNumero(enderecoResponse.getNumero());
            enderecoModel.setBairro(enderecoResponse.getBairro());
            enderecoModel.setLocalidade(enderecoResponse.getLocalidade());
            enderecoModel.setUf(enderecoResponse.getUf());
            enderecoModel.setComplemento(enderecoResponse.getComplemento());
            enderecoModel.setLatitude(farmaciaRequest.getLatitude());
            enderecoModel.setLongitude(farmaciaRequest.getLongitude());

            enderecoRepository.save(enderecoModel);

            farmaciaModel.setRazaoSocial(farmaciaRequest.getRazaoSocial());
            farmaciaModel.setCnpj(farmaciaRequest.getCnpj());
            farmaciaModel.setNomeFantasia(farmaciaRequest.getNomeFantasia());
            farmaciaModel.setEmail(farmaciaRequest.getEmail());
            farmaciaModel.setTelefoneFixo(farmaciaModel.getTelefoneFixo());
            farmaciaModel.setTelefoneCelular(farmaciaRequest.getTelefoneCelular());

            farmaciaRepository.save(farmaciaModel);

            return new FarmaciaResponse(farmaciaModel.getRazaoSocial()
                    , farmaciaModel.getCnpj()
                    , farmaciaModel.getNomeFantasia()
                    , farmaciaModel.getEmail()
                    , farmaciaModel.getTelefoneFixo()
                    , farmaciaModel.getTelefoneCelular()
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
            FarmaciaModel farmaciaModel = farmaciaRepository.findById(id)
                    .orElseThrow(()->new NotFoundException("Farmácia não encontrada pelo id: " + id));

            EnderecoModel enderecoModel = enderecoRepository.findById(farmaciaModel.getEnderecoModel().getId()).get();

            farmaciaRepository.deleteById(id);

            enderecoRepository.deleteById(enderecoModel.getId());
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerSideException("Erro ao deletar cadastro de farmácia, mensagem localizada: " + e.getLocalizedMessage());
        }

    }

    private void validarSeJaExisteFarmaciaCadastrada(FarmaciaRequest farmaciaRequest) {
        List<FarmaciaModel> farmaciaEntities = farmaciaRepository.findAll();

        for (FarmaciaModel farmaciaModel :farmaciaEntities) {
            if (Objects.equals(farmaciaRequest.getCnpj(), farmaciaModel.getCnpj())) {
                throw new BadRequestException("Já existe uma farmácia cadastrada com o cnpj informado!");
            }
            if (Objects.equals(farmaciaRequest.getCep(), farmaciaModel.getEnderecoModel().getCep())
                    && Objects.equals(farmaciaRequest.getNumero(), farmaciaModel.getEnderecoModel().getNumero())
                    && Objects.equals(farmaciaRequest.getComplemento(), farmaciaModel.getEnderecoModel().getComplemento())) {
                throw new BadRequestException("Já existe uma farmácia cadastrada no endereço informado!");
            }
        }
    }

}
