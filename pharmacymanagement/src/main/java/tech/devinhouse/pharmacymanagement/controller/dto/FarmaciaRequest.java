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
public class FarmaciaRequest {
    @NotNull
    @NotEmpty
    private String razaoSocial;

    @NotNull
    @NotEmpty
    private String cnpj;

    @NotNull
    @NotEmpty
    private String nomeFantasia;

    @NotNull
    @NotEmpty
    private String email;

    private String telefoneFixo;

    @NotNull
    @NotEmpty
    private String telefoneCelular;

    @NotNull
    @NotEmpty
    private String cep;

    @NotNull
    private Integer numero;

    private String complemento;

    @NotNull
    @NotEmpty
    private String latitude;

    @NotNull
    @NotEmpty
    private String longitude;
}
