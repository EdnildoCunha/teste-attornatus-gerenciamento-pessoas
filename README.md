# API Spring Boot para Gerenciamento de Pessoas

Esta é uma API Spring Boot simples para gerenciar informações de pessoas e seus endereços. Ela oferece funcionalidades como criar, editar, consultar e listar pessoas, criar endereços para pessoas, listar endereços da pessoa e definir um endereço principal para a pessoa. A seguir, você encontrará informações sobre como configurar, executar e utilizar esta API.

## Pré-requisitos

- Java 11 instalado
- Spring Boot (versão utilizada: 2.7.16)
- Ferramenta de desenvolvimento (por exemplo: IntelliJ IDEA, Eclipse)


## Executando a API

1. Clone este repositório para sua máquina:

```git clone https://github.com/EdnildoCunha/teste-attornatus-gerenciamento-pessoas.git```

2. Abra o projeto em sua ferramenta de desenvolvimento.

3. Execute a classe principal `PessoasApplication` para iniciar o aplicativo Spring Boot.

A API estará acessível em `http://localhost:8010`. Você pode ajustar a porta no arquivo `application.properties` se necessário.

## Acesso ao banco de dados
Está configurado o banco de dados H2 em memória:

1. Acessar o banco H2 com: http://localhost:8010/h2-console.
2. Configurar o username e o password confirme o application.properties.

## Testes
Para rodar os testes: 
```mvn test```