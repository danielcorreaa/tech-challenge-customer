
# Microsserviço tech-challenge-customer

Microsserviço responsável pelo gerenciamento de clientes


## Autores

- [@danielcorreaa](https://github.com/danielcorreaa)

## Stack utilizada

**Back-end:** Java, Spring Boot,  Mysql, Kafka

## Documentação da API

### Cadastro, atualização e buscas de clientes

#### Cadastrar cliente

```http
  POST api/v1/customers
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `cpf` | `string` | **Obrigatório**. Cpf do cliente |
| `nome` | `string` | **Obrigatório**. Nome do cliente |
| `email` | `string` |  E-mail do cliente |

#### Atualizar cliente

```http
  PUT api/v1/customers
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `cpf` | `string` | **Obrigatório**. Cpf do cliente |
| `nome` | `string` | **Obrigatório**. Nome do cliente |
| `email` | `string` |  E-mail do cliente |


#### Buscar clientes
```http
  GET api/v1/customers/find/${cpf}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `cpf`      | `string` | **Obrigatório**. O Cpf do cliente que você quer |

#### Excluir clientes
```http
  DELETE api/v1/customers/delete/${cpf}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `cpf`      | `string` | **Obrigatório**. O Cpf do cliente que você quer excluir |

### Cadastro, buscas de solicitações de exclusão de clientes

#### Cadastrar solicitação 

```http
  POST api/v1/solicitation/exclude
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `cpf` | `string` | **Obrigatório**. Cpf do cliente |
| `nome` | `string` | **Obrigatório**. Nome do cliente |
| `telefone` | `string` | **Obrigatório**. Telefone do cliente |
| `city` | `string` | **Obrigatório**. Cidade (Endereço) do cliente |
| `cep` | `string` | **Obrigatório**. Cep (Endereço) do cliente |
| `state` | `string` |**Obrigatório**. Estado (Endereço) do cliente |
| `street` | `string` | **Obrigatório**. Rua (Endereço) do cliente |
| `number` | `string` | **Obrigatório**. Numero (Endereço)do cliente |
| `neighborhood` | `string` |**Obrigatório**. Bairro (Endereço) do cliente |

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
| `exclude`    |   `boolean` |false |Valor false retorna todas as solicitações em espera, valor true retorna todas as solicitações atendidas  |
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


## Relatório RIPD
*RELATÓRIO DE IMPACTO À PROTEÇÃO DE DADOS PESSOAIS*

- [@RIPD](https://danielcorreaa.github.io/tech-challenge-product/RIPD.pdf)

## Documentação Saga

### Padrão escolhido: Coreografia 

#### Razão de utilizar a coreografia
*Escolhi o padrão coreografado para evitar deixar tudo centralizado no serviço de pedidos, no caso de acontecer alguma falha no serviço de pedidos toda a operação de notificar cliente e enviar os pedidos pagos para a cozinha seria paralizada, com a coreografia mesmo que tenha algum problema com o serviço de pedidos, a cozinha ainda recebe os pedidos com pagamentos aprovados, nao parando a produção de pedidos pagos, e os clientes recebem notificaçao de problemas com o pagamento.*

#### Desenho da solução

- [@Desenho Padrão Saga coreografado.](https://danielcorreaa.github.io/tech-challenge-production/images/saga-diagrama.png)

![Desenho Padrão Saga coreografado.](/images/saga-diagrama.png)

## Rodando localmente

Clone o projeto

```bash
  git clone https://github.com/danielcorreaa/tech-challenge-customer.git
```

Entre no diretório do projeto

```bash
  cd tech-challenge-customer
```

Docker

```bash
  docker compose up -d
```

No navegador

```bash
  http://localhost:8085/
```

## Deploy

### Para subir a aplicação usando kubernetes

#### Infraestrutura:

Clone o projeto com a infraestrutura

```bash
  git clone danielcorreaa/tech-challenge-infra-terraform-kubernetes
```
Entre no diretório do projeto

```bash
  cd tech-challenge-infra-terraform-kubernetes/
````

Execute os comandos

```bash   
- run: kubectl apply -f kubernetes/metrics.yaml 
- run: kubectl apply -f kubernetes/mysql/mysql-secrets.yaml 
- run: kubectl apply -f kubernetes/mysql/mysql-configmap.yaml 
- run: kubectl apply -f kubernetes/mysql/mysql-pv.yaml 
- run: kubectl apply -f kubernetes/mysql/mysql-service.yaml 
- run: kubectl apply -f kubernetes/mysql/mysql-statefulset.yaml

- run: kubectl apply -f kubernetes/kafka/kafka-configmap.yaml
- run: kubectl apply -f kubernetes/kafka/zookeeper-deployment.yaml
- run: kubectl apply -f kubernetes/kafka/zookeeper-service.yaml
- run: kubectl apply -f kubernetes/kafka/kafka-deployment.yaml
- run: kubectl apply -f kubernetes/kafka/kafka-service.yaml
- run: kubectl apply -f kubernetes/kafka/kafka-ui-deployment.yaml

````

#### Aplicação:

docker hub [@repositorio](https://hub.docker.com/r/daniel36/tech-challenge-customer/tags)

Clone o projeto

```bash
  git clone https://github.com/danielcorreaa/tech-challenge-customer.git
```

Entre no diretório do projeto

```bash
  cd tech-challenge-customer
```

Execute os comandos
```bash   
- run: kubectl apply -f k8s/customers-deployment.yaml
- run: kubectl apply -f k8s/customers-service.yaml
- run: kubectl apply -f k8s/customers-hpa.yaml
- run: kubectl get svc

````



