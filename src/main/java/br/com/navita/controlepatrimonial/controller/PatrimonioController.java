package br.com.navita.controlepatrimonial.controller;

import br.com.navita.controlepatrimonial.dto.MessageDTO;
import br.com.navita.controlepatrimonial.dto.PatrimonioDTO;
import br.com.navita.controlepatrimonial.exception.RecordNotFoundException;
import br.com.navita.controlepatrimonial.model.Patrimonio;
import br.com.navita.controlepatrimonial.service.PatrimonioService;
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
 * The type Patrimonio controller.
 */
@RestController
@RequestMapping("/api/bens")
@Api(value = "bens", produces = "application/json", consumes = "application/json", description = "Bens")
public class PatrimonioController {

    @Autowired
    private PatrimonioService service;

    /**
     * Show all response entity.
     *
     * @return the response entity
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Patrimonio.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found"),
    })
    @GetMapping
    public ResponseEntity<List<Patrimonio>> showAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    /**
     * Find by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Patrimonio.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Patrimonio> findById(@PathVariable("id") Long id) {
        return service.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create response entity.
     *
     * @param patrimonioDTO the patrimonio dto
     * @return the response entity
     */
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created", response = Patrimonio.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PatrimonioDTO patrimonioDTO) {
        try {
            Patrimonio record = service.save(patrimonioDTO);
            return ResponseEntity.created(null).body(record);
        } catch (RecordNotFoundException e) {
            return new ResponseEntity<MessageDTO>(new MessageDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Update response entity.
     *
     * @param id         the id
     * @param patrimonio the patrimonio
     * @return the response entity
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Patrimonio.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @Valid @RequestBody PatrimonioDTO patrimonio) {
        try {
            patrimonio.setId(id);
            Patrimonio updated = service.update(patrimonio);
            return ResponseEntity.ok().body(updated);
        } catch (RecordNotFoundException e) {
            return new ResponseEntity<MessageDTO>(new MessageDTO(e.getMessage()), HttpStatus.NOT_FOUND);
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
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return service.findById(id)
                .map(record -> {
                    service.deleteById(id);
                    return ResponseEntity.accepted().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
