package br.com.navita.controlepatrimonial.controller;

import br.com.navita.controlepatrimonial.dto.PatrimonioDTO;
import br.com.navita.controlepatrimonial.exception.RecordNotFoundException;
import br.com.navita.controlepatrimonial.model.Marca;
import br.com.navita.controlepatrimonial.model.Patrimonio;
import br.com.navita.controlepatrimonial.service.MarcaService;
import br.com.navita.controlepatrimonial.service.PatrimonioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PatrimonioControllerTest {

    @MockBean
    private MarcaService marcaService;

    @MockBean
    private PatrimonioService service;

    @Autowired
    private PatrimonioController controller;

    private List<Patrimonio> bens;

    @BeforeEach
    void setUp() {

        Patrimonio patrimonio = mock(Patrimonio.class);
        patrimonio.setMarca(mock(Marca.class));

        this.bens = new ArrayList<>();
        bens.add(patrimonio);
    }

    @Test
    void showAll() {
        when(service.findAll()).thenReturn(this.bens);
        Assertions.assertEquals(new ResponseEntity<>(this.bens, HttpStatus.OK), controller.showAll());
    }

    @Test
    void findById() {
        Patrimonio patrimonio = mock(Patrimonio.class);
        patrimonio.setMarca(mock(Marca.class));
        when(service.findById(1l)).thenReturn(Optional.of(patrimonio));
        Assertions.assertEquals(new ResponseEntity<>(patrimonio, HttpStatus.OK), controller.findById(1l));
    }

    @Test
    void create() throws RecordNotFoundException {
        PatrimonioDTO patrimonioDTO = mock(PatrimonioDTO.class);
        Patrimonio patrimonio = mock(Patrimonio.class);
        when(service.save(patrimonioDTO)).thenReturn(patrimonio);
        Assertions.assertEquals(new ResponseEntity<>(patrimonio, HttpStatus.CREATED), controller.create(patrimonioDTO));
    }

    @Test
    void update() throws RecordNotFoundException {
        PatrimonioDTO patrimonioDTO = mock(PatrimonioDTO.class);
        Patrimonio patrimonio = mock(Patrimonio.class);
        when(service.update(patrimonioDTO)).thenReturn(patrimonio);
        Assertions.assertEquals(new ResponseEntity<>(patrimonio, HttpStatus.OK), controller.update(1l, patrimonioDTO));
    }

    @Test
    void delete() {
        doNothing().when(service).deleteById(1l);
        controller.delete(1l);
    }
}