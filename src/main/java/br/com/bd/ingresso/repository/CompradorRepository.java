package br.com.bd.ingresso.repository;

import br.com.bd.ingresso.model.Comprador;
import br.com.bd.ingresso.model.Usuario;
import br.com.bd.ingresso.model.dto.CompradorDto;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface CompradorRepository extends JpaRepository<Comprador, Long> {
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

    @Query("select new br.com.bd.ingresso.model.dto.CompradorDto(cp.nome, cp.cpf, usu.email, usu.senha) from Comprador cp "
            + "join cp.usuario usu "
            + "where usu.email = :email and usu.senha = :senha")
    CompradorDto findByEmailAndSenha(@Param("email") String email, @Param("senha") String senha);
}