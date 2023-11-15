package br.com.bd.ingresso.repository;

import br.com.bd.ingresso.model.Login;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
    Login findByEmailAndSenha(String email, String senha);
}