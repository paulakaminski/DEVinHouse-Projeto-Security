package tech.devinhouse.pharmacymanagement.padroes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultErrorResponse {
    private int status;
    private String message;
    private String error;
    private String cause;
}
