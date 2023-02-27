package tech.devinhouse.pharmacymanagement.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FarmaciaResponse {
    private String razaoSocial;
    private String cnpj;
    private String nomeFantasia;
    private String email;
    private String telefoneFixo;
    private String telefoneCelular;
    private EnderecoResponse enderecoResponse;
}
