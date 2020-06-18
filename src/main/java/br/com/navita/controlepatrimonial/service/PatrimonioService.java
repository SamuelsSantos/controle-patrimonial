package br.com.navita.controlepatrimonial.service;

import br.com.navita.controlepatrimonial.dto.PatrimonioDTO;
import br.com.navita.controlepatrimonial.exception.RecordNotFoundException;
import br.com.navita.controlepatrimonial.model.Patrimonio;
import br.com.navita.controlepatrimonial.repository.MarcaRepository;
import br.com.navita.controlepatrimonial.repository.PatrimonioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * The type Patrimonio service.
 */
@Component
public class PatrimonioService {

    @Autowired
    private PatrimonioRepository repository;

    @Autowired
    private MarcaRepository marcaRepository;

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Patrimonio> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * Save patrimonio.
     *
     * @param patrimonio the patrimonio
     * @return the patrimonio
     * @throws RecordNotFoundException the record not found exception
     */
    public Patrimonio save(PatrimonioDTO patrimonio) throws RecordNotFoundException {

        if (!marcaRepository.existsById(patrimonio.getMarcaId()))
            throw new RecordNotFoundException(patrimonio.getMarcaId());

        return save(patrimonio.toObjeto());
    }

    private Patrimonio save(Patrimonio patrimonio) {
        return repository.save(patrimonio);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List findAll() {
        return repository.findAll();
    }

    /**
     * Update patrimonio.
     *
     * @param patrimonio the patrimonio
     * @return the patrimonio
     * @throws RecordNotFoundException the record not found exception
     */
    public Patrimonio update(PatrimonioDTO patrimonio) throws RecordNotFoundException {

        Optional<Patrimonio> record = repository.findById(patrimonio.getId());
        if (!record.isPresent())
            throw new RecordNotFoundException(patrimonio.getId());

        return save(patrimonio);
    }
}
