package tech.devinhouse.pharmacymanagement.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoResponse {
    private String cep;
    private String logradouro;
    private Integer numero;
    private String bairro;
    private String localidade;
    private String uf;
    private String complemento;
}
