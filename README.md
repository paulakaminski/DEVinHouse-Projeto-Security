# DEVinHouse-Projeto-Security
O quinto projeto avaliativo do curso DEVinHouse - Senai/SC - Turma CLAMED teve como proposta a continuação do back-end do sistema PharmacyManagement, desenvolvido no quarto projeto do curso, para a implementação dos atributos de segurança e testes necessários na aplicação.

Foi utilizado o Spring Security com JWT, juntamente com a linguagem Java, e testes unitários utilizando Junit e MockMVC.

Para realização de testes na aplicação, após o clone do projeto, efetuar:
- Criação do database pharmacy_management no PostgreSQL;
- Alterar os dados de usuário e senha do Postgre no arquivo application.properties;
- Realizar a criação das tabelas conforme o arquivo create.sql que está na pasta script;
- Realizar o insert de informações iniciais na base de dados conforme o arquivo import.sql que está na pasta resources;
- Rodar os testes unitários dos controllers (usuário, farmácia e medicamento), alterando o email de login do usuário que deseja logar para efetuar os testes.
