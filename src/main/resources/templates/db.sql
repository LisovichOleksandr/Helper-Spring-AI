CREATE EXTENSION IF NOT EXISTS vector;

CREATE TABLE IF NOT EXISTS loaded_document(
    id              UUID PRIMARY KEY,
    filename        VARCHAR(255) NOT NULL,
    content_hash    VARCHAR(65) NOT NULL,
    chunk_count     INTEGER,
    loaded_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT unique_document UNIQUE (filename, content_hash)
);

CREATE INDEX IF NOT EXISTS idx_loaded_document_filename ON loaded_document(filename);

-- Таблица для векторного хранилища
CREATE TABLE IF NOT EXISTS vector_store (
  id        VARCHAR(255) PRIMARY KEY,
  content   TEXT,
  metadata  JSON,
  embedding VECTOR(1024)
);

-- Индекс HNSW для быстрого векторного поиска
CREATE INDEX IF NOT EXISTS vector_store_hnsw_index ON vector_store USING hnsw (embedding vector_cosine_ops);

