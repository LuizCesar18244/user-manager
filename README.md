# user-manager
# API REST para gerenciamento de usuários

A API usará por padrão a porta 8080.

O arquivo data.sql contém a carga de dados iniciais para a utilização da API como dados do usuário.  
  nome: Juca.<br>      	
  cpf: 05936840018<br>
  senha: 123456<br>
  perfil: administrador<br>

Assim como os dados primários referentes aos cargos e permissões.    

OBS: referente aos usuários foi adicionado o atributo "senha", com o objetivo de tornar a API mais segura utilizando criptografia neste campo para isso. 

Foi utilizado Cache para a requisição de listar todos os usuários com o objetivo de aprimorar a perfomance.

Além disso a API foi documentada utilizando o swagger, esta documentação pode ser acessada através do link http://localhost:8080/swagger-ui.html 

A expiração do Token por padrão é de um dia para facilitar os testes.