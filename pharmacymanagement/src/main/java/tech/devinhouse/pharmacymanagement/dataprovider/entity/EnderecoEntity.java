package tech.devinhouse.pharmacymanagement.dataprovider.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "endereco")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String cep;

    @NotNull
    private String logradouro;

    @NotNull
    private Integer numero;

    @NotNull
    private String bairro;

    @Column(name = "cidade")
    @NotNull
    private String localidade;

    @NotNull
    private String uf;

    private String complemento;

    @NotNull
    private String latitude;

    @NotNull
    private String longitude;

    public EnderecoEntity(String cep, String logradouro, Integer numero, String bairro, String localidade, String uf, String complemento, String latitude, String longitude) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
