# Auth API

API REST de autenticação com JWT, desenvolvida com Java e Spring Boot.

## Tecnologias

- Java
- Spring Boot
- Spring Security
- JWT
- PostgreSQL
- Docker

## Como rodar

**1. Suba o banco de dados:**

```bash
docker-compose up -d
```

**2. Suba o servidor:**

```bash
.\mvnw.cmd spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

## Endpoints

| Método | URL | Descrição |
|--------|-----|-----------|
| POST | /auth/cadastro | Cadastra um novo usuário |
| POST | /auth/login | Realiza login e retorna o token JWT |

## Como autenticar

**1. Cadastre um usuário:**

```json
{
    "nome": "Seu Nome",
    "email": "seu@email.com",
    "senha": "suasenha"
}
```

**2. Faça login e copie o token retornado.**

**3. Em endpoints protegidos, envie o token no header:**
Authorization: Bearer seu_token_aqui

Sem o token, a API retorna `403 Forbidden`.