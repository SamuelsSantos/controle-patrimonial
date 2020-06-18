package br.com.navita.controlepatrimonial.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * The type Marca dto.
 */
@Data
@ApiModel(value = "Request Marca")
public class MarcaDTO {

    @JsonIgnore
    private Long id;

    @NotBlank(message = "O campo 'nome' não foi informado.")
    @Size(max = 100)
    @ApiModelProperty(value = "Identificação da marca.")
    private String nome;
}
