package br.com.bd.ingressoja.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PerfilDto {
    private String nome;
    private String cpf;
    private String email;
    private String senha;
}