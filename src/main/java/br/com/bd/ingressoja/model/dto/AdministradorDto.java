package br.com.bd.ingressoja.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdministradorDto {
    private String nome;
    private String email;
    private String senha;

    public PerfilDto toPerfildto() {
    return new PerfilDto(this.nome, null, this.email, this.senha);
}
}