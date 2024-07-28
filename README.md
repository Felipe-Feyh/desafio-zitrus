# Sistema de autorizações

## Descrição
Teste Zitrus.

## Requisitos
- Java 8
- Maven
- Docker

## Como Compilar
1. Clone o repositório:
    ```bash
    git clone https://github.com/Felipe-Feyh/desafio-zitrus
    ```
2. Navegue até o diretório do projeto:

3. Compile o projeto usando Maven:
    ```bash
    mvn clean package
    ```

## Como Executar
### Utilizando Docker
1. Construa a imagem Docker:
    ```bash
    docker build -t zitrus .
    ```
2. Execute o container:
    ```bash
    docker run -p 8080:8080 zitrus 
    ```
   
3. Execute a aplicação:
   http://localhost:8080/healthcare/

## Estrutura do Banco de Dados
A estrutura do banco de dados é gerenciada pelo Liquibase. O arquivo de changelog está localizado em `src/main/resources/liquibase/master.xml`.

## Licença
Este projeto está licenciado sob os termos da licença MIT.
