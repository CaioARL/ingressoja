package br.com.bd.ingressoja.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Evento {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "ativo_")
    private Boolean ativo;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "cep")
    private String cep;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "imagemURL", nullable = false)
    private String imagemURL;

    @Column(name = "inicio")
    private LocalDateTime inicio;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "numero")
    private String numero;

    @Column(name = "online")
    private Boolean online;

    @Column(name = "qntTotalVendas")
    private Integer qntTotalVendas;

    @Column(name = "qntVendasCanceladasPgto")
    private Integer qntVendasCanceladasPgto;

    @Column(name = "qntVendasCanceladasSolic")
    private Integer qntVendasCanceladasSolic;

    @Column(name = "qntVendasPendentes")
    private Integer qntVendasPendentes;

    @Column(name = "qntVendasProcessadas")
    private Integer qntVendasProcessadas;

    @Column(name = "termino")
    private LocalDateTime termino;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "totalIngressos")
    private Integer totalIngressos;

    @Column(name = "uf")
    private String uf;

    @Column(name = "url_")
    private String url;

    @Column(name = "vendaPausada")
    private Boolean vendaPausada;

    @Column(name = "fk_CategoriaEvento_Id")
    private Integer fkCategoriaEventoId;

    @Column(name = "fk_Produtora_Id")
    private Integer fkProdutoraId;
}