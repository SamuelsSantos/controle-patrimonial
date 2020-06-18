package br.com.navita.controlepatrimonial.model;


import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * The type Patrimonio.
 */
@Data
@Entity
@NoArgsConstructor
@Getter
public class Patrimonio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "O campo nome deve ser informado.")
    private String nome;

    private String descricao;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "marca_id")
    @JsonProperty("marcaId")
    @NotNull(message = "O código da marca deve ser informado.")
    @ApiModelProperty(name = "marcaId", dataType = "Long")
    private Marca marca;

    private String numeroTombo;

    /**
     * Pre persist.
     */
    @PrePersist
    public void prePersist() {
        setNumeroTombo(UUID.randomUUID().toString());
    }

    /**
     * Instantiates a new Patrimonio.
     *
     * @param nome      the nome
     * @param descricao the descricao
     * @param marcaId   the marca id
     */
    public Patrimonio(String nome, String descricao, Long marcaId) {
        this.nome = nome;
        this.descricao = descricao;
        this.marca = new Marca();
        this.marca.setId(marcaId);
    }

}
