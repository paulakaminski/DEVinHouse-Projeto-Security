package tech.devinhouse.pharmacymanagement.dataprovider.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "farmacia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FarmaciaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "razao_social")
    @NotNull
    @NotEmpty
    private String razaoSocial;

    @NotNull
    @NotEmpty
    private String cnpj;

    @Column(name = "nome_fantasia")
    @NotNull
    @NotEmpty
    private String nomeFantasia;

    @NotNull
    @NotEmpty
    private String email;

    @Column(name = "telefone_fixo")
    private String telefoneFixo;

    @Column(name = "telefone_celular")
    @NotNull
    @NotEmpty
    private String telefoneCelular;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "endereco_id")
    @NotNull
    private EnderecoModel enderecoModel;

    public FarmaciaModel(String razaoSocial, String cnpj, String nomeFantasia, String email, String telefoneFixo, String telefoneCelular, EnderecoModel enderecoModel) {
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
        this.email = email;
        this.telefoneFixo = telefoneFixo;
        this.telefoneCelular = telefoneCelular;
        this.enderecoModel = enderecoModel;
    }
}
