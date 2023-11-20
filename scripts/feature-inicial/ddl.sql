CREATE DATABASE IF NOT EXISTS `ingressoja` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `ingressoja`;

CREATE TABLE Usuario (
    id INT PRIMARY KEY,
    email VARCHAR(255),
    senha VARCHAR(255)
);

CREATE TABLE CategoriaEvento (
    id INT PRIMARY KEY,
    ativo_ BOOLEAN,
    nome VARCHAR(255)
);

CREATE TABLE Administrador (
    id INT PRIMARY KEY,
    ativo_ BOOLEAN,
    nome VARCHAR(255),
    fk_Usuario_id INT,
    FOREIGN KEY (fk_Usuario_id) REFERENCES Usuario(id) ON DELETE CASCADE
);

CREATE TABLE Produtora (
    id INT PRIMARY KEY,
    ativo_ BOOLEAN,
    cnpj VARCHAR(18),
    nomeFantasia VARCHAR(255),
    publicToken VARCHAR(255),
    razaoSocial VARCHAR(255),
    fk_Usuario_id INT,
    FOREIGN KEY (fk_Usuario_id) REFERENCES Usuario(id) ON DELETE CASCADE
);

CREATE TABLE Evento (
    id INT PRIMARY KEY,
    ativo_ BOOLEAN,
    bairro VARCHAR(255),
    cep VARCHAR(9),
    cidade VARCHAR(255),
    descricao TEXT,
    imagemURL VARCHAR(255),
    inicio DATETIME,
    logradouro VARCHAR(255),
    numero VARCHAR(20),
    online BOOLEAN,
    qntTotalVendas INT,
    qntVendasCanceladasPgto INT,
    qntVendasCanceladasSolic INT,
    qntVendasPendentes INT,
    qntVendasProcessadas INT,
    termino DATETIME,
    titulo VARCHAR(255),
    totalIngressos INT,
    uf_ VARCHAR(50),
    url VARCHAR(255),
    vendaPausada BOOLEAN,
    fk_CategoriaEvento_id INT,
    fk_Produtora_id INT,
    FOREIGN KEY (fk_CategoriaEvento_id) REFERENCES CategoriaEvento(id) ON DELETE CASCADE,
    FOREIGN KEY (fk_Produtora_id) REFERENCES Produtora(id) ON DELETE CASCADE
);

CREATE TABLE Comprador (
    id INT PRIMARY KEY,
    nome VARCHAR(255),
    cpf_ VARCHAR(14),
    ativo_ BOOLEAN,
    fk_Usuario_id INT,
    FOREIGN KEY (fk_Usuario_id) REFERENCES Usuario(id) ON DELETE CASCADE
);

CREATE TABLE Despesa (
    id INT PRIMARY KEY,
    descricao VARCHAR(255),
    valor DOUBLE,
    fk_Evento_id INT,
    FOREIGN KEY (fk_Evento_id) REFERENCES Evento(id) ON DELETE CASCADE
);

CREATE TABLE Pedido (
    id INT PRIMARY KEY,
    dataHora DATETIME,
    statusPagamento VARCHAR(255),
    statusPedido VARCHAR(255),
    urlPagamento VARCHAR(255),
    valorTotal DOUBLE,
    fk_Comprador_id INT,
    FOREIGN KEY (fk_Comprador_id) REFERENCES Comprador(id) ON DELETE CASCADE
);

CREATE TABLE TipoDeIngresso (
    id INT PRIMARY KEY,
    ativo_ BOOLEAN,
    descricao VARCHAR(255),
    inicio DATETIME,
    nome VARCHAR(255),
    quantidadeDisponivel INT,
    quantidadeTotal INT,
    termino DATETIME,
    valor DOUBLE,
    fk_Evento_id INT,
    FOREIGN KEY (fk_Evento_id) REFERENCES Evento(id) ON DELETE RESTRICT
);

CREATE TABLE ItemPedido (
    id INT PRIMARY KEY,
    cpf_ VARCHAR(14),
    ingressante VARCHAR(255),
    utilizado BOOLEAN,
    fk_TipoDeIngresso_id INT,
    fk_Pedido_id INT,
    FOREIGN KEY (fk_TipoDeIngresso_id) REFERENCES TipoDeIngresso(id) ON DELETE CASCADE,
    FOREIGN KEY (fk_Pedido_id) REFERENCES Pedido(id) ON DELETE RESTRICT
);
