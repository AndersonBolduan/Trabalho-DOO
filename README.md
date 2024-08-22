Integrantes do grupo: Manoela Fernandes Simão, Anderson Felipi Bolduan, Patriki de Oliveira Góss, Matheus Samuel Baena Soares, Luana Alvarenga Valim

Instituição de Ensino: Instituto Federal De Santa Catarina - Câmpus Lages

Disciplina: Desenvolvimento de Sistemas Orientado a Objetos

Este protótipo é uma aplicação voltada ao gerenciamento de vendas, desenvolvida em Java e 
utilizando a biblioteca Swing para a interface gráfica. A aplicação adota princípios de programação 
orientada a objetos (OO) e emprega JDBC para a interação com um banco de dados MySQL, permitindo a 
implementação de funcionalidades essenciais como cadastro, edição, exclusão e pesquisa, bem como o 
gerenciamento de vendas e produtos. O design segue o padrão MVC (Model-View-Controller).

Configuração Banco de Dados
O arquivo responsável pela conexão com o banco de dados MySQL é o conexaoMysql.java. Para configurar as credenciais, siga as etapas abaixo:

1.Acesse o arquivo conexaoMysql.java:
	*Localize o arquivo dentro do diretório `src\main\java\model\conexao\`.

2.Edite as seguintes variáveis com as credenciais do seu servidor:
	*`url`: O URL de conexão do banco de dados, geralmente no formato jdbc:mysql://<servidor>:<porta>/<nome_do_banco>.
	*`user`: O nome de usuário do banco de dados.
	*`password`: A senha do banco de dados

3.Salve as alterações e certifique-se de que as credenciais estão corretas e correspondem ao seu servidor MySQL.

Tela de Login:

![Imagem do WhatsApp de 2024-08-22 à(s) 01 13 09_312c8a59](https://github.com/user-attachments/assets/69a04471-42f8-444d-8438-74e09411a7f7)

Tela Home:

![Imagem do WhatsApp de 2024-08-22 à(s) 01 16 17_f8f7c029](https://github.com/user-attachments/assets/fba3c29a-48c8-482a-9a3a-6b7848f18d31)

Tela de Produtos com suas Funcionalidades:
![Imagem do WhatsApp de 2024-08-22 à(s) 01 16 17_bbaa173c](https://github.com/user-attachments/assets/61cc0c6c-31d7-494a-ab68-0525c60e445a)
![Imagem do WhatsApp de 2024-08-22 à(s) 01 13 08_16449a98](https://github.com/user-attachments/assets/502b162b-8a76-400d-9977-48fe4e8f6c4f)

Tela de Clientes com suas funcionalidades:
![Imagem do WhatsApp de 2024-08-22 à(s) 01 16 17_56f1bfe8](https://github.com/user-attachments/assets/cfe04cdc-0129-4adc-9850-3022e7fee3a0)
![Imagem do WhatsApp de 2024-08-22 à(s) 01 13 07_484915e2](https://github.com/user-attachments/assets/14518a30-6969-422b-bb23-b3e5f6d546a8)

Tela de Vendas com suas funcionalidades:
![Imagem do WhatsApp de 2024-08-22 à(s) 01 16 18_d9581283](https://github.com/user-attachments/assets/b666a445-6f73-45ef-af8a-5f2fffb340ba)
![Imagem do WhatsApp de 2024-08-22 à(s) 01 13 08_5ae6729e](https://github.com/user-attachments/assets/ccb1bad7-26e3-4aa9-b929-e109f3892001)

Tela de Usuários com suas funcionalidades:
![Imagem do WhatsApp de 2024-08-22 à(s) 01 16 17_16304f15](https://github.com/user-attachments/assets/82091198-466c-4ba3-a38e-6ccc256ff6bb)
![Imagem do WhatsApp de 2024-08-22 à(s) 01 13 07_289062c7](https://github.com/user-attachments/assets/f8bd5f89-f389-4917-985d-093012f62edd)
