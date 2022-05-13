# geradorCPFCNPJ
API para gerar e validar CPF e CNPJ

Rotas GET:

/api/status -> retorna o status da aplicação
/api/gerar-cpf/{uf}/{pontuacao} -> gera um novo cpf 
    Parâmetros:
        uf = Qualquer sigla de estado com 2 letras ou ZZ para indiferente
        pontuacao = Se deseja imprimir o resultado gerado com ou sem a pontuação padrão
/api/validar-cpf/{cpf} -> validar um número de cpf existente
    Parâmetros:
        cpf = cpf a ser validado

/api/gerar-cnpj/{filial}/{pontuacao} -> gerar um novo cnpj
    Parâmetros:
        filial = número da filial (1 a 9999)
        pontuacao = Se deseja imprimir o resultado gerado com ou sem a pontuação padrão
/api/validar-cnpj/{cnpj} -> validar um cnpj existente
    Parâmetros:
        cnpj = cnpj a ser validado
