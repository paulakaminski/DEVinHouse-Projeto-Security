package tech.devinhouse.pharmacymanagement.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.pharmacymanagement.controller.dto.FarmaciaRequest;
import tech.devinhouse.pharmacymanagement.controller.dto.FarmaciaResponse;
import tech.devinhouse.pharmacymanagement.padroes.DefaultResponse;
import tech.devinhouse.pharmacymanagement.service.FarmaciaService;

import java.util.List;

@RestController
@RequestMapping("/farmacia")
public class FarmaciaController {

    private final FarmaciaService farmaciaService;

    public FarmaciaController(FarmaciaService farmaciaService) {
        this.farmaciaService = farmaciaService;
    }

    @GetMapping
    public ResponseEntity<DefaultResponse> encontrarTodasAsFarmacias() {
        List<FarmaciaResponse> responseList = farmaciaService.encontrarTodasAsFarmacias();

        return new ResponseEntity<>(
                new DefaultResponse<List<FarmaciaResponse>>(
                        HttpStatus.OK.value()
                        , "Dados encontrados com sucesso!"
                        , responseList
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse> encontrarFarmaciaPorId(@PathVariable("id") Long id) {
        FarmaciaResponse farmaciaResponse = farmaciaService.encontrarFarmaciaPorId(id);

        return new ResponseEntity<>(
                new DefaultResponse<FarmaciaResponse>(
                        HttpStatus.OK.value()
                        , "Dados encontrados com sucesso!"
                        , farmaciaResponse
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/cadastro")
    public ResponseEntity<DefaultResponse> salvarNovaFarmacia(@Valid @RequestBody FarmaciaRequest farmaciaRequest) {
        FarmaciaResponse farmaciaResponse = farmaciaService.cadastrarNovaFarmacia(farmaciaRequest);

        return new ResponseEntity<>(
                new DefaultResponse<FarmaciaResponse>(
                        HttpStatus.CREATED.value()
                        , "Farmácia cadastrada com sucesso!"
                        , farmaciaResponse
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DefaultResponse> atualizarFarmaciaPorId(@Valid @PathVariable("id") Long id, @RequestBody FarmaciaRequest farmaciaRequest) {
        FarmaciaResponse farmaciaResponse = farmaciaService.atualizarFarmaciaPorId(id, farmaciaRequest);

        return new ResponseEntity<>(
                new DefaultResponse<FarmaciaResponse>(
                        HttpStatus.OK.value()
                        , "Cadastro de farmácia atualizado com sucesso!"
                        , farmaciaResponse
                ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletarFarmaciaPorId(@PathVariable("id") Long id) {
        farmaciaService.deletarFarmaciaPorId(id);
        return ResponseEntity.accepted().build();
    }

}
