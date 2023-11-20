package br.com.bd.ingressoja.model;

import javax.persistence.Id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "email")
  private String email;

  @Column(name = "senha")
  private String senha;
}