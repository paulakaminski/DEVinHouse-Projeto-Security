package tech.devinhouse.pharmacymanagement.dataprovider.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "medicamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "preco_unitario")
    @NotNull
    private Double precoUnitario;

    @NotNull
    @NotEmpty
    private String tipo;

    public MedicamentoModel(String nome
            , String laboratorio
            , String dosagem
            , String descricao
            , Double precoUnitario
            , String tipo) {
        this.nome = nome;
        this.laboratorio = laboratorio;
        this.dosagem = dosagem;
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
        this.tipo = tipo;
    }

}
