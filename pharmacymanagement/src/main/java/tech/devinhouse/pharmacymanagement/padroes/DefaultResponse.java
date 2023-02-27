package tech.devinhouse.pharmacymanagement.padroes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultResponse<T> {
    private Integer status;
    private String message;
    private T data;
}
