package br.com.navita.controlepatrimonial.repository;

import br.com.navita.controlepatrimonial.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The interface Marca repository.
 */
@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
    /**
     * Exists marca by nome boolean.
     *
     * @param nome the nome
     * @return the boolean
     */
    boolean existsMarcaByNome(@Param("nome") String nome);
}
