package tech.devinhouse.pharmacymanagement.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoResponse {
    private String nome;
    private String laboratorio;
    private String dosagem;
    private String descricao;
    private Double precoUnitario;
    private String tipo;
}
