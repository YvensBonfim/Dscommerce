

-----

# DSCommerce - E-commerce API

O **DSCommerce** é uma API RESTful completa para gestão de e-commerce, desenvolvida em **Java** com o ecossistema **Spring Boot**. O sistema oferece um fluxo transacional robusto, englobando catálogo de produtos, carrinho de compras, processamento de pedidos, pagamentos e um controle de acesso rigoroso baseado em perfis (Roles) utilizando OAuth2.

## 🚀 Tecnologias e Frameworks

  * **Java 17**
  * **Spring Boot 3.5.11**
  * **Spring Data JPA & Hibernate**: ORM e mapeamento relacional avançado (chaves compostas, relacionamentos N:N).
  * **Spring Security & OAuth2**: Autenticação via JWT (Resource Server e Authorization Server).
  * **Spring Validation**: Validação de dados (Bean Validation) na entrada da API.
  * **Lombok**: Redução de código boilerplate.
  * **Maven**: Gestão de dependências e build.

## ⚙️ Funcionalidades Principais

  * **Catálogo de Produtos**: Listagem paginada com busca por nome, vinculação a múltiplas categorias e gestão completa (CRUD) restrita a administradores.
  * **Gestão de Pedidos**: Registro de pedidos (checkout) vinculados ao usuário logado, processamento de itens de pedido (carrinho) e status de pagamento.
  * **Segurança Granular**:
      * Endpoints públicos (ex: visualização do catálogo).
      * Endpoints para clientes (`ROLE_CLIENT`): registro de pedidos, visualização do próprio perfil.
      * Endpoints para administradores (`ROLE_ADMIN`): gestão de estoque e produtos.
  * **Tratamento de Exceções Global**: Respostas de erro padronizadas interceptadas por `@ControllerAdvice`.

## 📦 Modelo de Domínio

Abaixo está o diagrama UML representando as entidades do banco de dados e seus relacionamentos:


<img width="672" height="303" alt="image" src="https://github.com/user-attachments/assets/d9e93051-2ee9-4cdf-8dd5-4f0b3e58a334" />


O banco de dados relacional é estruturado com as seguintes lógicas:

  * **User & Role**: Relação de N:N. Utilização de *Projections* (`UserDetailsProjection`) para otimização de consultas nativas no login.
  * **Product & Category**: Relação de N:N para categorização flexível do catálogo.
  * **Order, OrderItem & Payment**:
      * Um pedido (`Order`) pertence a um cliente (`User`) e possui um pagamento (`Payment` - 1:1).
      * O carrinho de compras é gerido pela entidade `OrderItem`, que utiliza uma chave primária composta (`OrderItemPK` combinando `Order` e `Product`).

## 🔗 Endpoints da API

### 🔑 Autenticação e Perfil

| Método | Endpoint | Descrição | Permissão |
| :--- | :--- | :--- | :--- |
| `POST` | `/oauth2/token` | Autenticação de usuário e geração de token JWT | Público |
| `GET` | `/users/me` | Retorna os dados do usuário autenticado | Logado (Admin / Client) |

### 🛍️ Produtos e Categorias

| Método | Endpoint | Descrição | Permissão |
| :--- | :--- | :--- | :--- |
| `GET` | `/products` | Retorna catálogo paginado (suporta filtro `?name=`) | Público |
| `GET` | `/products/{id}` | Busca de produto por ID | Admin / Client |
| `POST` | `/products` | Cadastro de novo produto | Admin |
| `PUT` | `/products/{id}` | Atualização de produto existente | Admin |
| `DELETE` | `/products/{id}` | Remoção de produto | Admin |
| `GET` | `/categories` | Listagem de todas as categorias | Público |

### 🛒 Pedidos (Orders)

| Método | Endpoint | Descrição | Permissão |
| :--- | :--- | :--- | :--- |
| `GET` | `/orders/{id}` | Busca um pedido pelo ID | Admin / Client |
| `POST` | `/orders` | Registra um novo pedido com itens | Client |

## ⚠️ Validações e Tratamento de Erros

A API possui uma camada DTO (Data Transfer Object) estrita que evita a exposição do banco de dados e aplica regras de negócio. Em caso de falha, a API retorna objetos no padrão `CustomErros`, cobrindo:

  * **Status 404 (Not Found)**: Recursos não encontrados (`ResourceNotFoundException`).
  * **Status 422 (Unprocessable Entity)**: Erros de validação semântica (`MethodArgumentNotValidException`), como produtos sem nome ou com preço negativo. Retorna a lista de campos inválidos (`ValidationError` com `FieldMessage`).
  * **Status 403 (Forbidden)**: Violação de permissão de acesso (`ForbidenException`).
  * **Status 400 (Bad Request)**: Erros de integridade do banco de dados (`DatabaseException`).

## 🛠️ Como Executar o Projeto Localmente

1.  **Clone o repositório:**

    ```bash
    git clone https://github.com/seu-usuario/dscommerce.git
    cd dscommerce
    ```

2.  **Configuração do Banco de Dados:**
    Certifique-se de configurar as variáveis de conexão no arquivo `src/main/resources/application.properties` (por padrão, o projeto suporta bancos como H2 ou PostgreSQL via Spring Data).

3.  **Construir e Iniciar a Aplicação:**
    Utilize o wrapper do Maven incluído no projeto:

    ```bash
    # Linux/Mac
    ./mvnw spring-boot:run

    # Windows
    mvnw.cmd spring-boot:run
    ```

## 📄 Licença

Distribuído sob a licença **MIT**. Veja o arquivo [LICENSE](https://www.google.com/search?q=LICENSE) para mais informações.

-----



