# language: pt
Funcionalidade: API - Customer

  Cenário: Cadastrar Cliente
    Dado que quero cadastrar um cliente
    Quando informar um cpf válido e informar um email válido
    Entao devo conseguir cadastrar cliente com sucesso

  Cenário: Busca por cliente
    Dado que quero pesquisar um cliente
    Quando quando efetuar pesquisa
    Entao o cliente deve ser apresentado

  Cenário: Atualizar Cliente
    Dado que quero atualizar um cliente
    Quando alterar nome ou email
    Entao  o cliente deve ser atualizado

  Cenário: Deletar Cliente
    Dado que quero deletar um cliente
    Quando informar um cpf existente
    Entao  o cliente deve ser deletado