package br.com.navita.controlepatrimonial.controller;

import br.com.navita.controlepatrimonial.dto.MarcaDTO;
import br.com.navita.controlepatrimonial.exception.OperationNotAllowedException;
import br.com.navita.controlepatrimonial.exception.RecordAlreadyExistsException;
import br.com.navita.controlepatrimonial.exception.RecordNotFoundException;
import br.com.navita.controlepatrimonial.model.Marca;
import br.com.navita.controlepatrimonial.service.MarcaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
class MarcaControllerTest {

    @MockBean
    private MarcaService service;

    @Autowired
    private MarcaController controller;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Marca> marcas;

    @BeforeEach
    void setUp() {
        this.marcas = new ArrayList<>();
        marcas.add(new Marca(1L, "Teste1"));
        marcas.add(new Marca(2L, "Teste2"));
    }

    @Test
    void showAll() {
        when(service.findAll()).thenReturn(this.marcas);
        Assertions.assertEquals(new ResponseEntity<>(this.marcas, HttpStatus.OK), controller.showAll());
    }

    @Test
    void findById() {
        Marca marca = new Marca(1L, "Teste1");
        when(service.findById(1l)).thenReturn(Optional.of(marca));
        Assertions.assertEquals(new ResponseEntity<>(marca, HttpStatus.OK), controller.findById(1l));
    }

    @Test
    void create() throws RecordAlreadyExistsException {
        MarcaDTO marcaDTO = new MarcaDTO();
        marcaDTO.setNome("Teste");
        Marca marca = new Marca(1L, "Teste");
        when(service.save(marcaDTO)).thenReturn(marca);
        Assertions.assertEquals(new ResponseEntity<>(marca, HttpStatus.CREATED), controller.create(marcaDTO));
    }

//    @Test
//    void createDuplicado()  {
//        MarcaDTO marcaDTO = new MarcaDTO();
//        marcaDTO.setNome("Teste");
//
//        RecordAlreadyExistsException e = assertThrows(
//                RecordAlreadyExistsException.class,
//                () -> when(service.save(marcaDTO)).thenThrow(new RecordAlreadyExistsException()));
//
//        controller.create(marcaDTO);
//        Assertions.assertEquals(new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST),
//                controller.create(marcaDTO));
//    }

    @Test
    void update() throws RecordAlreadyExistsException, RecordNotFoundException {
        MarcaDTO marcaDTO = new MarcaDTO();
        marcaDTO.setNome("Teste1");
        Marca marca = new Marca(1L, "Teste1");
        when(service.update(marcaDTO)).thenReturn(marca);
        Assertions.assertEquals(new ResponseEntity<>(marca, HttpStatus.OK), controller.update(1L, marcaDTO));
    }

    @Test
    void delete() throws OperationNotAllowedException {
        doNothing().when(service).deleteById(1l);
        controller.delete(1l);
    }
}