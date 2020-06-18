package br.com.navita.controlepatrimonial.service;

import br.com.navita.controlepatrimonial.dto.MarcaDTO;
import br.com.navita.controlepatrimonial.exception.OperationNotAllowedException;
import br.com.navita.controlepatrimonial.exception.RecordAlreadyExistsException;
import br.com.navita.controlepatrimonial.exception.RecordNotFoundException;
import br.com.navita.controlepatrimonial.model.Marca;
import br.com.navita.controlepatrimonial.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * The type Marca service.
 */
@Component
public class MarcaService {

    /**
     * The constant MSG_MARCA_EXISTE.
     */
    public static final String MSG_MARCA_EXISTE = "Já exite uma marca cadastrada com o nome: %s.";

    @Autowired
    private MarcaRepository repository;

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Marca> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * Save marca.
     *
     * @param marca the marca
     * @return the marca
     * @throws RecordAlreadyExistsException the record already exists exception
     */
    public Marca save(MarcaDTO marca) throws RecordAlreadyExistsException {

        Marca newMarca = new Marca();
        newMarca.setNome(marca.getNome());
        return save(newMarca);
    }

    /**
     * Save marca.
     *
     * @param marca the marca
     * @return the marca
     * @throws RecordAlreadyExistsException the record already exists exception
     */
    public Marca save(Marca marca) throws RecordAlreadyExistsException {
        if (repository.existsMarcaByNome(marca.getNome()))
            throw new RecordAlreadyExistsException(
                    String.format(MSG_MARCA_EXISTE, marca.getNome()));

        return repository.save(marca);
    }

    /**
     * Update marca.
     *
     * @param marca the marca
     * @return the marca
     * @throws RecordAlreadyExistsException the record already exists exception
     * @throws RecordNotFoundException      the record not found exception
     */
    public Marca update(MarcaDTO marca) throws RecordAlreadyExistsException, RecordNotFoundException {
        if (repository.existsMarcaByNome(marca.getNome()))
            throw new RecordAlreadyExistsException(
                    String.format(MSG_MARCA_EXISTE, marca.getNome()));

        Optional<Marca> record = repository.findById(marca.getId());
        if (!record.isPresent())
            throw new RecordNotFoundException(marca.getId());

        record.get().setNome(marca.getNome());
        return save(record.get());
    }

    /**
     * Delete by id.
     *
     * @param id the id
     * @throws OperationNotAllowedException the operation not allowed exception
     */
    public void deleteById(Long id) throws OperationNotAllowedException {
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new OperationNotAllowedException("O registro não pode ser excluido.");
        }
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List findAll() {
        return repository.findAll();
    }

}
