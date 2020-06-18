package br.com.navita.controlepatrimonial.controller;

import br.com.navita.controlepatrimonial.dto.MarcaDTO;
import br.com.navita.controlepatrimonial.dto.MessageDTO;
import br.com.navita.controlepatrimonial.exception.OperationNotAllowedException;
import br.com.navita.controlepatrimonial.exception.RecordAlreadyExistsException;
import br.com.navita.controlepatrimonial.exception.RecordNotFoundException;
import br.com.navita.controlepatrimonial.model.Marca;
import br.com.navita.controlepatrimonial.model.Patrimonio;
import br.com.navita.controlepatrimonial.service.MarcaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The type Marca controller.
 */
@RestController
@RequestMapping("/api/marcas")
@Api(value = "marcas", produces = "application/json", consumes = "application/json", description = "Marcas")
public class MarcaController {

    @Autowired
    private MarcaService service;


    /**
     * Show all response entity.
     *
     * @return the response entity
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Patrimonio.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping
    public ResponseEntity<List<Marca>> showAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    /**
     * Find by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Marca.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Marca> findById(@PathVariable("id") Long id) {
        return service.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create response entity.
     *
     * @param marca the marca
     * @return the response entity
     */
    @ApiResponses({
            @ApiResponse(code = 202, message = "Created", response = Marca.class),
            @ApiResponse(code = 400, message = "Já exite uma marca cadastrada com o nome: {nome}."),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PostMapping
    public ResponseEntity<?> create(@RequestBody MarcaDTO marca) {
        try {
            Marca update = service.save(marca);
            return new ResponseEntity<Marca>(update, HttpStatus.CREATED);
        } catch (RecordAlreadyExistsException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageDTO(e.getMessage()));
        }
    }

    /**
     * Update response entity.
     *
     * @param id    the id
     * @param marca the marca
     * @return the response entity
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Marca.class),
            @ApiResponse(code = 400, message = "Já exite uma marca cadastrada com o nome: {nome}."),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody MarcaDTO marca) {
        try {
            marca.setId(id);
            Marca updated = service.update(marca);
            return ResponseEntity.ok().body(updated);
        } catch (RecordAlreadyExistsException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageDTO(e.getMessage()));
        } catch (RecordNotFoundException e) {
            return ResponseEntity
                    .notFound().build();
        }
    }

    /**
     * Delete response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @ApiResponses({
            @ApiResponse(code = 202, message = "Accepted"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "O registro não pode ser excluído"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            if (service.findById(id).isPresent()) {
                service.deleteById(id);
                return ResponseEntity.ok().build();
            } else return ResponseEntity.notFound().build();
        } catch (OperationNotAllowedException e) {
            return new ResponseEntity<MessageDTO>(new MessageDTO(e.getMessage()), HttpStatus.CONFLICT);
        }
    }
}
