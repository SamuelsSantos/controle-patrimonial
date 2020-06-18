package br.com.navita.controlepatrimonial.repository;

import br.com.navita.controlepatrimonial.model.Patrimonio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Patrimonio repository.
 */
@Repository
public interface PatrimonioRepository extends JpaRepository<Patrimonio, Long> {
}