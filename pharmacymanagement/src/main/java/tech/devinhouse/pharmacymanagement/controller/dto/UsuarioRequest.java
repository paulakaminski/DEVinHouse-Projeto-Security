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
public class UsuarioRequest {
    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String senha;
}
