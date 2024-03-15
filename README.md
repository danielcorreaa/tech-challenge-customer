
# Microsserviço tech-challenge-customer

Microsserviço responsável pelo gerenciamento de clientes


## Autores

- [@danielcorreaa](https://github.com/danielcorreaa)


## Documentação da API

### Cadastro, atualização e buscas de clientes

#### Cadastrar clientes

```http
  POST api/v1/customers
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `cpf` | `string` | **Obrigatório**. Cpf do clientes |
| `nome` | `string` | **Obrigatório**. Nome do clientes |
| `email` | `string` |  E-mail do clientes |

#### Atualizar clientes

```http
  PUT api/v1/customers
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `cpf` | `string` | **Obrigatório**. Cpf do clientes |
| `nome` | `string` | **Obrigatório**. Nome do clientes |
| `email` | `string` |  E-mail do clientes |


#### Buscar clientes
```http
  GET api/v1/customers/find/${cpf}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `cpf`      | `string` | **Obrigatório**. O Cpf do cliente que você~quer |

#### Excluir clientes
```http
  DELETE api/v1/customers/delete/${cpf}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `cpf`      | `string` | **Obrigatório**. O Cpf do cliente que você~quer excluir |



