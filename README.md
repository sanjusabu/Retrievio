# Retrievio

> Intelligent document assistant powered by Retrieval-Augmented Generation (RAG).

Retrievio is a document question-answering application that allows users to upload a PDF and ask questions about its contents.

Instead of sending the entire document to an LLM for every query, Retrievio uses **Retrieval-Augmented Generation (RAG)** to find the most relevant parts of the document and provide them as context to the LLM.

## Architecture
<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/c2291b6f-cac0-467f-952a-1b6dcbe28bec" />

# RAG Pipeline

Retrievio follows the following RAG workflow.

## 1. Document Upload

The user uploads a PDF through the Angular frontend.

```text
PDF
 ↓
Angular
 ↓
Spring Boot API
```

The backend extracts the text from the document.

> Retrievio currently does not persist the original PDF file.

---

## 2. Document Chunking

The extracted document text is split into smaller chunks.

```text
Document
   ↓
Text Extraction
   ↓
Chunking
   ↓
Chunk 1
Chunk 2
Chunk 3
...
```

Chunking allows the retrieval system to work with smaller and more relevant sections of the document.

---

## 3. Embedding Generation

Each chunk is converted into a numerical vector representation using an embedding model.

```text
"Software Engineer at UKG..."
             ↓
        Embedding Model
             ↓
     [0.021, -0.182, ...]
```

These vectors represent the semantic meaning of the text.

---

## 4. Vector Storage

The generated embeddings are stored in PostgreSQL using pgvector along with their corresponding document chunks.

```text
PostgreSQL + pgvector

┌─────────────────────────────────┐
│ Document Chunk                  │
│                                 │
│ text                            │
│ embedding                       │
│ document_id                     │
└─────────────────────────────────┘
```

---

## 5. Query Embedding

When the user asks a question, the question is converted into an embedding using the embedding model.

```text
User Question
      ↓
Embedding Model
      ↓
Query Vector
```

---

## 6. Similarity Search

The query vector is compared against the stored document vectors.

Retrievio uses vector similarity to find the chunks that are semantically closest to the user's question.

```text
Query Vector
      │
      ▼
Vector Similarity Search
      │
      ▼
Top Relevant Chunks
```

---

## 7. Context + LLM

The retrieved chunks are supplied as context to the LLM along with the user's question.

```text
Retrieved Context
       +
User Question
       │
       ▼
     Qwen3
       │
       ▼
Final Answer
```

This allows the LLM to answer questions based on the uploaded document rather than relying only on its general knowledge.


## Video Demo
https://drive.google.com/file/d/1fxK0QGA3FFFT-VJ_Dt2-zw6q8ktn9-XR/view?usp=drive_link



## Retrievio - Full Flow

### Landing Page
<img width="1459" height="794" alt="image" src="https://github.com/user-attachments/assets/0440f026-1298-4789-a3d7-a56180056f11" />

### Query About the Document
<img width="1459" height="794" alt="image" src="https://github.com/user-attachments/assets/28260c66-5d0f-4cef-bd21-8ec129dcb261" />
<img width="1459" height="794" alt="image" src="https://github.com/user-attachments/assets/c13fb44e-5825-4720-bab4-7a8e7e0eae29" />

### Chunks saved in the PostgreSQL DB with Vector Embeddings
<img width="992" height="203" alt="image" src="https://github.com/user-attachments/assets/c22f8a0a-b63e-4f29-8c43-70a2dc7ce341" />

### Non-relevant Query
<img width="1067" height="309" alt="image" src="https://github.com/user-attachments/assets/8a9c475b-8741-4a5e-86ae-8051311b8fc6" />

### On clicking Upload New Document DB is cleared and we return to Landing Page
<img width="1121" height="457" alt="image" src="https://github.com/user-attachments/assets/90037011-6a50-4325-9888-15431ce8f917" />
<img width="1423" height="776" alt="image" src="https://github.com/user-attachments/assets/f08f2d23-bd8f-4190-a53e-a84d45862803" />

