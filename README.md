# Apache Camel project integrating with MySql

> System integration:
>
> Rest API to JDBC
>

- O comando abaixo cria o arquivo '.env' baseado no .env.example, depois preencha o valor das variáveis

> No Windows crie o arquivo '.env' setando o encoding para UTF-8 (usando o comando abaixo irá setar Cp1252)

```bash
cat .env.example > .env
```

- Execute o docker-compose para criar e executar um cantainer com o banco de dados

```bash
docker-compose up -d
```

- Se for executar o projeto apartir do jar, exporte primeiro as variáveis (no Linux)

```bash
export $(cat .env | xargs)
```

- Empacotar o projeto

> No Linux (se as variáveis tiverem sido setadas)

```
mvn package
```

> No Windows


```
mvn package -DDB_USER=username -DDB_PASSWORD=password
```

- Executar o JAR

```
java -jar -DDB_USER=username -DDB_PASSWORD=password target\demo-camel-0.0.1-SNAPSHOT.jar
```

- Se for executar pela IDE, setar as variaveis em Run Configurations > environment

![Variaveis no Run configurations](./images/definir_variaveis_na_IDE.png)

- Listar os usuários (GET)

```
http://localhost:8080/users
```

- Adicionar um novo usuário (numa lista - porque posso adicionar mais de um ao mesmo tempo) (POST)

```
http://localhost:8080/users
```

- Body (json)

```
[{
    "nome": "Anna Carolina"
}]
```

- Documentação dos recursos da API

```
http://localhost:8080/swagger-ui.html
```

