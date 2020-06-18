package br.com.navita.controlepatrimonial.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * The type Marca.
 */
@Data
@Entity
@ApiModel(value = "Marca")
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "nome"),
        })
@AllArgsConstructor
@NoArgsConstructor
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Código")
    private Long id;

    @Column(length = 100)
    @NotBlank(message = "O campo 'nome' não pode ser vazio.")
    @ApiModelProperty(value = "Nome da marca")
    private String nome;

    /**
     * From id marca.
     *
     * @param id the id
     * @return the marca
     */
    public static Marca fromId(Long id) {
        Marca marca = new Marca();
        marca.id = id;
        return marca;
    }
}