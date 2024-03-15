
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

### Cadastro, buscas de solicitações de exclusão de clientes

#### Cadastrar solicitação 

```http
  POST api/v1/solicitation/exclude
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `cpf` | `string` | **Obrigatório**. Cpf do clientes |
| `nome` | `string` | **Obrigatório**. Nome do clientes |
| `telefone` | `string` | **Obrigatório**. Telefone do clientes |
| `city` | `string` | **Obrigatório**. Cidade (Endereço) do clientes |
| `cep` | `string` | **Obrigatório**. Cep (Endereço) do clientes |
| `state` | `string` |**Obrigatório**. Estado (Endereço) do clientes |
| `street` | `string` | **Obrigatório**. Rua (Endereço) do clientes |
| `number` | `string` | **Obrigatório**. Numero (Endereço)do clientes |
| `neighborhood` | `string` |**Obrigatório**. Bairro (Endereço) do clientes |

#### Buscar solicitação 

```http
  GET api/v1/solicitation/exclude/find/${cpf}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `cpf`      | `string` | **Obrigatório**. O Cpf do cliente que você~quer |

#### Buscar solicitaçoes

```http
  GET api/v1/solicitation/exclude/find?exclude=false&page=0&size=10
```

| Parâmetro   | Tipo       |  Default|Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |:--- |
| `exclude`    |   `boolean` |false |Valor false retorna todas as solicitações em espera  |
| `page`      | `int` | 0| Valor 0 retornara do primeiro registro até o valor  do size|
| `size`      | `int` | 10 |Quantidade de registro que retornaram na resposta|

### Pesquisa de noficação
#### Noticações de pagamentos não aprovados 

#### Buscar Noficicações

```http
  GET /api/v1/notify/find/${orderId}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `orderId`      | `string` | **Obrigatório**. Código do pedido com pagamento reprovado |

## Documentação Saga

### Padrão escolhido: Coreografia 

#### Razão de utilizar a coreografia
*Escolhi o padrão coreografado para evitar deixar tudo centralizado no serviço de pedidos, no caso de acontecer alguma falha no serviço de pedidos toda a operação de notificar cliente e enviar os pedidos pagos para a cozinha seria paralizada, com a coreografia mesmo que tenha algum problema com o serviço de pedidos, a cozinha ainda recebe os pedidos com pagamentos aprovados, nao parando a produção de pedidos pagos, e os clientes recebem notificaçao de problemas com o pagamento.*

#### Desenho da solução








## Stack utilizada


**Back-end:** Java, Spring Boot,  Mysql, Kafka

