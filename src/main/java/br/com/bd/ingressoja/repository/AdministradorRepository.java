package br.com.bd.ingressoja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bd.ingressoja.model.Administrador;
import br.com.bd.ingressoja.model.Usuario;

public interface AdministradorRepository extends JpaRepository<Administrador, Long>{
    
    Administrador findByUsuario(Usuario usuario);
}
