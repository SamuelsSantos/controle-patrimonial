package br.com.navita.controlepatrimonial.dto;

import br.com.navita.controlepatrimonial.model.Patrimonio;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The type Patrimonio dto.
 */
@Data
@ApiModel(value = "Request Patrimonio")
public class PatrimonioDTO {

    @JsonIgnore
    private Long id;

    @NotBlank(message = "O campo 'nome' não foi informado.")
    @Size(max = 100, message = "O campo 'nome' não pode ser maior que {max}.")
    private String nome;

    @NotBlank(message = "O campo 'descrição' não foi informado.")
    @Size(max = 255, message = "O campo 'descrição' não pode ser maior que {max}.")
    private String descricao;

    @NotNull(message = "O campo 'marcaId' não pode ser nulo.")
    private Long marcaId;

    /**
     * To objeto patrimonio.
     *
     * @return the patrimonio
     */
    public Patrimonio toObjeto() {
        return new Patrimonio(nome, descricao, marcaId);
    }

}
