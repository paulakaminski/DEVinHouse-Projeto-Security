package tech.devinhouse.pharmacymanagement.service;

import org.springframework.stereotype.Service;
import tech.devinhouse.pharmacymanagement.controller.dto.MedicamentoRequest;
import tech.devinhouse.pharmacymanagement.controller.dto.MedicamentoResponse;
import tech.devinhouse.pharmacymanagement.dataprovider.model.MedicamentoModel;
import tech.devinhouse.pharmacymanagement.dataprovider.repository.MedicamentoRepository;
import tech.devinhouse.pharmacymanagement.exception.BadRequestException;
import tech.devinhouse.pharmacymanagement.exception.NotFoundException;
import tech.devinhouse.pharmacymanagement.exception.ServerSideException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public List<MedicamentoResponse> encontrarTodosOsMedicamentos() {
        try {
            List<MedicamentoModel> entityList = medicamentoRepository.findAll();

            List<MedicamentoResponse> responseList = new ArrayList<>();

            for (MedicamentoModel medicamentoModel :
                    entityList) {
                responseList.add(
                        new MedicamentoResponse(medicamentoModel.getNome()
                                , medicamentoModel.getLaboratorio()
                                , medicamentoModel.getDosagem()
                                , medicamentoModel.getDescricao()
                                , medicamentoModel.getPrecoUnitario()
                                , medicamentoModel.getTipo())
                );
            }

            return responseList;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerSideException("Erro ao pesquisar medicamentos, mensagem localizada: " + e.getLocalizedMessage());
        }

    }

    public MedicamentoResponse encontrarMedicamentoPorId(Long id) {
        try {
            MedicamentoModel medicamentoModel = medicamentoRepository.findById(id)
                    .orElseThrow(()->new NotFoundException("Medicamento não encontrado pelo id: " + id));

            return new MedicamentoResponse(medicamentoModel.getNome()
                    , medicamentoModel.getLaboratorio()
                    , medicamentoModel.getDosagem()
                    , medicamentoModel.getDescricao()
                    , medicamentoModel.getPrecoUnitario()
                    , medicamentoModel.getTipo()
            );
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerSideException("Erro ao pesquisar medicamento, mensagem localizada: " + e.getLocalizedMessage());
        }

    }

    public MedicamentoResponse cadastrarNovoMedicamento(MedicamentoRequest medicamentoRequest) {
        try {
            validarSeJaExisteMedicamentoCadastrado(medicamentoRequest);

            MedicamentoModel medicamentoModel = medicamentoRepository.save(
                    new MedicamentoModel(medicamentoRequest.getNome()
                            , medicamentoRequest.getLaboratorio()
                            , medicamentoRequest.getDosagem()
                            , medicamentoRequest.getDescricao()
                            , medicamentoRequest.getPrecoUnitario()
                            , medicamentoRequest.getTipo()
                    ));

            return new MedicamentoResponse(medicamentoModel.getNome()
                    , medicamentoModel.getLaboratorio()
                    , medicamentoModel.getDosagem()
                    , medicamentoModel.getDescricao()
                    , medicamentoModel.getPrecoUnitario()
                    , medicamentoModel.getTipo()
            );
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerSideException("Erro ao cadastrar medicamento, mensagem localizada: " + e.getLocalizedMessage());
        }

    }

    public MedicamentoResponse atualizarMedicamentoPorId(Long id, MedicamentoRequest medicamentoRequest) {
        try {
            MedicamentoModel medicamentoModel = medicamentoRepository.findById(id)
                    .orElseThrow(()->new NotFoundException("Medicamento não encontrado pelo id: " + id));

            if(medicamentoRequest.getNome().isEmpty()
                    || medicamentoRequest.getLaboratorio().isEmpty()
                    || medicamentoRequest.getDosagem().isEmpty()
                    || medicamentoRequest.getTipo().isEmpty()) {
                throw new BadRequestException("Os campos de preenchimento obrigatório não podem ser nulos!");
            }

            medicamentoModel.setNome(medicamentoRequest.getNome());
            medicamentoModel.setLaboratorio(medicamentoRequest.getLaboratorio());
            medicamentoModel.setDosagem(medicamentoRequest.getDosagem());
            medicamentoModel.setDescricao(medicamentoRequest.getDescricao());
            medicamentoModel.setPrecoUnitario(medicamentoRequest.getPrecoUnitario());
            medicamentoModel.setTipo(medicamentoRequest.getTipo());

            medicamentoRepository.save(medicamentoModel);

            return new MedicamentoResponse(medicamentoModel.getNome()
                    , medicamentoModel.getLaboratorio()
                    , medicamentoModel.getDosagem()
                    , medicamentoModel.getDescricao()
                    , medicamentoModel.getPrecoUnitario()
                    , medicamentoModel.getTipo()
            );
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerSideException("Erro ao atualizar cadastro de medicamento, mensagem localizada: " + e.getLocalizedMessage());
        }

    }

    public void deletarMedicamentoPorId(Long id) {
        try {
            MedicamentoModel medicamentoModel = medicamentoRepository.findById(id)
                    .orElseThrow(()->new NotFoundException("Farmácia não encontrada pelo id: " + id));

            medicamentoRepository.deleteById(id);

        } catch (NotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ServerSideException("Erro ao deletar cadastro de medicamento, mensagem localizada: " + e.getLocalizedMessage());
        }

    }

    private void validarSeJaExisteMedicamentoCadastrado(MedicamentoRequest medicamentoRequest) {
        List<MedicamentoModel> medicamentoEntities = medicamentoRepository.findAll();

        for (MedicamentoModel medicamentoModel :medicamentoEntities) {
            if (Objects.equals(medicamentoRequest.getNome().toUpperCase(), medicamentoModel.getNome().toUpperCase())
                    && Objects.equals(medicamentoRequest.getLaboratorio().toUpperCase(), medicamentoModel.getLaboratorio().toUpperCase())
                    && Objects.equals(medicamentoRequest.getDosagem().toUpperCase(), medicamentoModel.getDosagem().toUpperCase())) {
                throw new BadRequestException("Já existe um medicamento cadastrado com os dados informados!");
            }
        }
    }

}
