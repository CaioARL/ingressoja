package br.com.bd.ingressoja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.bd.ingressoja.model.Administrador;
import br.com.bd.ingressoja.model.Usuario;
import br.com.bd.ingressoja.model.dto.AdministradorDto;

public interface AdministradorRepository extends JpaRepository<Administrador, Long>{
    
    Administrador findByUsuario(Usuario usuario);


    @Query("select new br.com.bd.ingressoja.model.dto.AdministradorDto(ad.nome, usu.email, usu.senha) from Administrador ad "
            + "join ad.usuario usu "
            + "where usu.email = :email and usu.senha = :senha")
    AdministradorDto findByEmailAndSenha(@Param("email") String email, @Param("senha") String senha);
}
