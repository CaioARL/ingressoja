package br.com.bd.ingresso.repository;

import br.com.bd.ingresso.model.Usuario;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmailAndSenha(String email, String senha);

    Usuario findByEmail(String email);

    // Pega próximo id
    @Query(value = "SELECT MAX(id) FROM Usuario")
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