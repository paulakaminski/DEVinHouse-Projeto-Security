package tech.devinhouse.pharmacymanagement.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoRequest {
    @NotNull
    @NotEmpty
    private String nome;

    @NotNull
    @NotEmpty
    private String laboratorio;

    @NotNull
    @NotEmpty
    private String dosagem;


    private String descricao;

    @NotNull
    private Double precoUnitario;

    @NotNull
    @NotEmpty
    private String tipo;
}
