package br.com.bd.ingresso.repository;

import br.com.bd.ingresso.model.Comprador;
import br.com.bd.ingresso.model.Usuario;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface CompradotRepository extends JpaRepository<Comprador, Long> {
    Comprador findByUsuario(Usuario usuario);

    Comprador findByCpf(String cpf);

    // Pega próximo id
    @Query(value = "SELECT MAX(id) FROM Comprador")
    Long findMaxId();

    default Long findNextId() {
        Long maxId = findMaxId();
        if (maxId != null) {
            return maxId + 1;
        } else {
            return 1L;
        }
    }
}