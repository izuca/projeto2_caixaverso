CREATE TABLE produto (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    taxa_juros_anual NUMERIC(12,9) NOT NULL,
    prazo_maximo_meses INTEGER
);
