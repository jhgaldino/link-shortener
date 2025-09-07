# Link Shortener API

Este é um serviço simples de encurtamento de URLs construído com Spring Boot. Ele permite que os usuários enviem uma URL longa e recebam uma URL curta que redireciona para a original.

## Funcionalidades

- Encurta URLs longas para um formato compacto usando uma conversão para Base62.
- Redireciona códigos curtos para suas URLs longas originais.
- Valida a entrada para garantir que apenas URLs válidas sejam processadas.
- Evita a duplicação de entradas no banco de dados para a mesma URL longa.
- URL base configurável através do arquivo `application.properties`.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **Maven**
- **H2 Database** (banco de dados em memória)

## Como Executar o Projeto

### Pré-requisitos

- JDK 17 ou superior
- Apache Maven

### Passos

1.  **Clone o repositório:**
    ```sh
    git clone <url-do-seu-repositorio>
    ```

2.  **Navegue até o diretório do projeto:**
    ```sh
    cd link-shortener
    ```

3.  **Execute a aplicação com o Maven Wrapper:**
    - No Windows:
      ```sh
      mvnw.cmd spring-boot:run
      ```
    - No Linux/macOS:
      ```sh
      ./mvnw spring-boot:run
      ```

4.  A aplicação estará disponível em `http://localhost:8081`.

## Documentação da API

A API expõe dois endpoints principais:

### 1. Encurtar uma URL

Cria um novo link curto para a URL longa fornecida.

- **Endpoint:** `POST /shorten`
- **Método:** `POST`
- **Corpo da Requisição (JSON):**
  ```json
  {
    "longUrl": "https://www.google.com/search?q=spring+boot"
  }
  ```
- **Resposta de Sucesso (200 OK):**
  - **Content-Type:** `text/plain`
  - **Corpo:** A URL curta completa.
    ```
    http://localhost:8081/b
    ```
- **Resposta de Erro (400 Bad Request):**
  - Ocorre se o campo `longUrl` estiver em branco ou não for uma URL válida.

### 2. Redirecionar para a URL Original

Redireciona o usuário para a URL longa original com base no código curto.

- **Endpoint:** `GET /{shortCode}`
- **Método:** `GET`
- **Parâmetro de URL:**
  - `shortCode`: O código gerado pelo endpoint de encurtamento (ex: `b`).
- **Exemplo de Uso:**
  - Acessar `http://localhost:8081/b` em um navegador.
- **Resposta de Sucesso (302 Found):**
  - Redireciona para a URL longa correspondente.
- **Resposta de Erro (404 Not Found):**
  - Ocorre se o `shortCode` não existir no banco de dados.
