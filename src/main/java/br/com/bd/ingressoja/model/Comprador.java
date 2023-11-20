package br.com.bd.ingressoja.model;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comprador implements Serializable {
  private static final long serialVersionUID = 2L;

  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "nome")
  private String nome;

  @Column(name = "cpf_")
  private String cpf;

  @Column(name = "ativo_")
  private int ativo;

  @OneToOne
  @JoinColumn(name = "fk_Usuario_id", referencedColumnName = "id")
  private Usuario usuario;
}