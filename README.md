# Apache Camel project integrating with MySql

> System integration:
>
> Rest API to JDBC
>

- Crie um arquivo '.env' e defina as variáveis de ambientes baseado no .env.example

- Se for executar o projeto apartir do jar, exporte primeiro as variáveis (no Linux)

```bash
export $(cat .env | xargs)
```

- Listar os usuários (GET)

```
http://localhost:8080/users
```

- Adicionar um novo usuário (POST)

```
http://localhost:8080/users
```

- Body (json)

```
{
    "nome": "Anna Carolina"
}
```
