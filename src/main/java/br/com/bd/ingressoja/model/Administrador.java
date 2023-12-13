package br.com.bd.ingressoja.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.bd.ingressoja.model.dto.PerfilDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Administrador {

    @Id 
    @Column(name = "id")
    private Long id;

    @Column(name = "ativo_")
    private Boolean ativo;

    @Column(name = "nome")
    private String nome;

    @OneToOne
    @JoinColumn(name = "fk_Usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    public PerfilDto toPerfildto() {
    return new PerfilDto(this.nome, null, this.usuario.getEmail(), this.usuario.getSenha());
    }
}
