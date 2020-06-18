
# Controle Patrimonial  
  
Api Rest Controle Patrimonial. 

Documentação com mais detalhes no Swagger-UI.
  
- Java 1.8  
- Spring 2.3.1.RELEASE  
- Postgres Latest  
- [Swagger](http://localhost:8085/swagger-ui.html#/)  - Obs: Serviço precisa estar iniciado.
- [Postman](https://documenter.getpostman.com/view/1130955/SzzkccN2?version=latest#0ab85ed5-9714-46b3-a431-bf35babdd338)

### Passos para testar

#### Empacotar
```bash
mvn package
```

### Testes
```
mvn test
```

### Executar 
Obs: 
- Sem o Docker é necessário ajustar o arquivo de propriedades _/resources/application.properties_.
```
 ./mvnw spring-boot:run
```
  
### Configurações  
  
Docker:  
- mvn package
- docker volume create --name=database-data
- cd /docker && docker-compose -up -d
    
Ide:  
- resources/application.properties  
- endpoint: http://localhost:8085

Swagger:
- Gerar o token 
- clicar no authorize
- Preencher o campo value com Bearer {seu token}
  Obs: Importante colocar a palava Bearer antes de colar seu token.
- Postman
  Collection e as enviroments: _/postman_.
Arquivo de **carga inicial** encontra-se em: _/resources/data.sql_. A configuração está como create-drop para gerar o banco a cada startup. 
  
## Endpoints  
  
### Authorization  
_/auth_
  
As entidades de autorização, autenteticação foram estendias do pacote security do spring. O user details foi incluído o campo email.

Jwt - AUTHORIZATION Bearer Token

#### Criar usuário

```bash
curl --location --request POST 'http://localhost:8085/auth/signup' \
--data-raw '{
    "username": "adm",
    "email": "email@teste.com",
    "password": "1234567"
}'
```

#### Login

```bash
curl --location --request POST 'http://localhost:8085/auth/signin' \
--data-raw '{
    "username": "adm",
    "password": "abc1mmmmmm"
}'
```

### Marca 

_/api/marcas_

Requer autenticação -> Bearer Token recebida no campo token da resposta do login.

#### Criar
```bash
curl --location --request POST 'http://localhost:8085/api/marcas' \
--header 'Authorization: Bearer {seu token}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "nome": "Computadorxxxx"
}'
```
#### Atualizar

Obs: Não é permitido marcas com mesmo nome.

```bash
curl --location --request PUT 'http://localhost:8085/api/marcas/4' \
--header 'Authorization: Bearer {seu token}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "nome": "Mobyx"
}'
```

#### Buscar por Identificador
```bash
curl --location --request GET 'http://localhost:8085/api/marcas/1' \
--header 'Authorization: Bearer {seu token}' \
```
#### Excluir
```bash
curl --location --request DELETE 'http://localhost:8085/api/marcas/1' \
--header 'Authorization: Bearer {seu token}' \
```
#### Lista de marcas
```bash
curl --location --request GET 'http://localhost:8085/api/marcas' \
--header 'Authorization: Bearer {seu token}' \
```

#### Bens
_/api/bens_

#### Criar 
```bash
curl --location --request POST 'http://localhost:8085/api/bens' \
--header 'Authorization: Bearer {seu token}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "nome": "Computador Dell",
    "descricao": "Descricao computador",
    "marcaId": 10
}'
```

#### Atualizar
```bash
curl --location --request PUT 'http://localhost:8085/api/bens/1' \
--header 'Authorization: Bearer {seu token}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "nome": "Computador Dell xxx",
    "descricao": "Descricao computador",
    "marcaId": 10
}'
```
#### Buscar por Identificador
```bash
curl --location --request GET 'http://localhost:8085/api/bens/2' \
--header 'Authorization: Bearer {seu token}' \
```
#### Excluir
```bash
curl --location --request DELETE 'http://localhost:8085/api/bens/1' \
--header 'Authorization: Bearer {seu token}' \
```

#### Lista
```bash
curl --location --request GET 'http://localhost:8085/api/bens' \
--header 'Authorization: Bearer {seu token}' \
```