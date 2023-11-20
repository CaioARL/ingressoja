package br.com.bd.ingressoja.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompradorDto {
  private String nome;
  private String cpf;
  private String email;
  private String senha;
}