package tech.devinhouse.pharmacymanagement.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.pharmacymanagement.controller.dto.MedicamentoRequest;
import tech.devinhouse.pharmacymanagement.controller.dto.MedicamentoResponse;
import tech.devinhouse.pharmacymanagement.padroes.DefaultResponse;
import tech.devinhouse.pharmacymanagement.service.MedicamentoService;

import java.util.List;

@RestController
@RequestMapping("/medicamentos")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @GetMapping
    public ResponseEntity<DefaultResponse> buscarTodosOsMedicamentos() {
        List<MedicamentoResponse> responseList = medicamentoService.encontrarTodosOsMedicamentos();

        return new ResponseEntity<>(
                new DefaultResponse<List<MedicamentoResponse>>(
                        HttpStatus.OK.value()
                        , "Dados encontrados com sucesso!"
                        , responseList
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse> buscarMedicamentoPorId(@PathVariable("id") Long id) {
        MedicamentoResponse medicamentoResponse = medicamentoService.encontrarMedicamentoPorId(id);

        return new ResponseEntity<>(
                new DefaultResponse<MedicamentoResponse>(
                        HttpStatus.OK.value()
                        , "Dados encontrados com sucesso!"
                        , medicamentoResponse
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/cadastro")
    public ResponseEntity<DefaultResponse> cadastrarNovoMedicamento(@Valid @RequestBody MedicamentoRequest medicamentoRequest) {
        MedicamentoResponse medicamentoResponse = medicamentoService.cadastrarNovoMedicamento(medicamentoRequest);

        return new ResponseEntity<>(
                new DefaultResponse<MedicamentoResponse>(
                        HttpStatus.CREATED.value()
                        , "Medicamento cadastrado com sucesso!"
                        , medicamentoResponse
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DefaultResponse> atualizarMedicamentoPorId(
            @PathVariable("id") Long id,
            @RequestBody MedicamentoRequest medicamentoRequest) {

        MedicamentoResponse medicamentoResponse = medicamentoService.atualizarMedicamentoPorId(id, medicamentoRequest);

        return new ResponseEntity<>(
                new DefaultResponse<MedicamentoResponse>(
                        HttpStatus.OK.value()
                        , "Cadastro de medicamento atualizado com sucesso!"
                        , medicamentoResponse
                ),
                HttpStatus.OK
        );

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletarMedicamentoPorId(@PathVariable("id") Long id) {
        medicamentoService.deletarMedicamentoPorId(id);
        return ResponseEntity.accepted().build();
    }

}
