# Setup

This guide explains how to set up and run Retrievio locally.

## Prerequisites

Make sure the following are installed:

- Java 21
- Gradle
- Node.js
- npm
- Angular CLI
- PostgreSQL
- pgvector
- Ollama (if using Ollama for the LLM/embedding models)

---

## 1. Clone the Repository

```bash
git clone https://github.com/sanjusabu/Retrievio.git
cd Retrievio
```

---

## 2. Database Setup

Retrievio uses PostgreSQL with the `pgvector` extension for storing and searching embeddings.

Create a PostgreSQL database and enable the vector extension:

```sql
CREATE EXTENSION IF NOT EXISTS vector;
```

Make sure the database is accessible from the Spring Boot application.

---

## 3. Environment Variables

Set the database credentials in your terminal.

```bash
export DATABASE_URL="jdbc:postgresql://<host>:5432/<database>"
export DATABASE_USERNAME="<username>"
export DATABASE_PASSWORD="<password>"
```

If using Google GenAI, set:

```bash
export GOOGLE_API_KEY="<your-api-key>"
```

> Never commit API keys, database passwords, or other secrets to GitHub.

You can verify that the variables are available with:

```bash
printf 'DATABASE_USERNAME=<%s>\n' "$DATABASE_USERNAME"
printf 'DATABASE_URL=<%s>\n' "$DATABASE_URL"
printf 'PASSWORD_SET=<%s>\n' "${DATABASE_PASSWORD:+yes}"
```

---

## 4. Backend Configuration

The backend reads the database configuration from environment variables.

Example `application.yml`:

```yaml
spring:
  datasource:
    url: ${DATABASE_URL}
    username: ${DATABASE_USERNAME}
    password: ${DATABASE_PASSWORD}

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: false

ai:
  ollama:
    base-url: http://localhost:11434
```

Update the AI configuration based on the provider and models being used.

---

## 5. Run the Backend

Navigate to the backend project:

```bash
cd backend
```

Run the Spring Boot application:

```bash
./gradlew bootRun
```

The backend runs on:

```text
http://localhost:8080
```

If environment variables are not available in the current shell, they can be supplied directly when starting the application:

```bash
DATABASE_URL="$DATABASE_URL" DATABASE_USERNAME="$DATABASE_USERNAME" DATABASE_PASSWORD="$DATABASE_PASSWORD" ./gradlew --no-daemon clean bootRun
```

---

## 6. Ollama Setup

If using Ollama locally, make sure Ollama is running:

```bash
ollama serve
```

Pull the required models:

```bash
ollama pull qwen3
```

For the embedding model:

```bash
ollama pull nomic-embed-text
```

The default Ollama endpoint is:

```text
http://localhost:11434
```

---

## 7. Frontend Setup

Navigate to the frontend project:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Start the Angular development server:

```bash
ng serve
```

The frontend will be available at:

```text
http://localhost:4200
```

---

## 8. Run Retrievio

Once PostgreSQL, the backend, and the frontend are running:

```text
Frontend
http://localhost:4200
        │
        ▼
Spring Boot Backend
http://localhost:8080
        │
        ├── PostgreSQL + pgvector
        │
        └── Ollama / AI Model
```

Open the frontend in your browser:

```text
http://localhost:4200
```

Upload a PDF and start asking questions about the document.

---

## 9. API Endpoints

### Upload Document

```http
POST /api/v1/documents/upload
```

Example:

```bash
curl -X POST   http://localhost:8080/api/v1/documents/upload   -F "file=@resume.pdf"
```

The API returns a document ID.

---

### Ask a Question

```http
POST /api/v1/chat/ask
```

The backend:

1. Generates an embedding for the question.
2. Searches PostgreSQL using vector similarity.
3. Retrieves the most relevant chunks.
4. Sends the retrieved context and question to the LLM.
5. Returns the generated answer.

---

### Delete Document Chunks

```http
DELETE /api/v1/documents/chunks/{documentId}
```

This removes the chunks associated with the active document when the user chooses **Upload New Document**.

---

## 10. Important Notes

- The original uploaded PDF is currently not persisted.
- The document text is processed and the resulting chunks and embeddings are stored in PostgreSQL.
- Chat prompts are currently not persisted.
- The application uses UUIDs to identify documents.
- Keep database credentials and API keys outside the source code.
- The exact AI configuration depends on whether Ollama or another supported AI provider is being used.
