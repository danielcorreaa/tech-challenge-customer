Feature: Customer

  Scenario: Insert Customer
    When Castrate um novo cliente com um cpf valido e email valido
    Then deve consegui cadastrar com sucesso
    And deve retornar cliente cadastrado

  Scenario: Find Customer
    Given que tenho o cliente cadastrado
    When Quando fizer uma busca pelo cpf
    Then deve retornar cliente cadastrado

  Scenario: Update Customer
    Given que tenho o cliente cadastrado
    When efetuar solicitacao de alteração do cliente
    Then o cliente é atualizado com sucesso
    And deve retornar dados atualizado

