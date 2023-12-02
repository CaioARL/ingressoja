package br.com.bd.ingressoja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bd.ingressoja.model.CategoriaEvento;

public interface CategoriaEventoRepository extends JpaRepository<CategoriaEvento, Long> {

}