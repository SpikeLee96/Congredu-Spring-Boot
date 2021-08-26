# Congredu-Spring-Boot
Protótipo de site educacional utilizando Spring Boot.
Projeto Congredu, que inicialmente foi desenvolvido em Node(https://github.com/SpikeLee96/Congredu) , foi portado por mim para Spring Boot com finalidade de aprimorar conhecimentos no framework. 
Algumas funcionalidades foram adicionadas, como sessão de usuário, encriptação de senha, e portal de opções.

Para iniciar a aplicação primeiramente instale a linguagem Java, a IDE Eclipse, e o banco de dados PostgreSQL através dos seguintes links:
https://www.oracle.com/br/java/technologies/javase-jdk11-downloads.html
https://www.eclipse.org/downloads/
https://www.postgresql.org/download/

1. Ao instalar o PostgreSQL, inclua nas opções de instalação o SGBD pgAdmin 4;
2. Inicie o pgAdmin 4 e crie um banco de dados com o nome "usersDB", na porta 5432;
3. No pgAdmin 4, clique no banco criado "usersDB" com o botão direito do mouse e selecione a opção "Query Tool";
4. Cole o conteúdo do arquivo "bd do projeto (PostgreSQL).txt" disposto na raíz repositório do projeto, em seguida, execute o código;
5. Inicie o eclipse e selecione as opções : File > Import > Git > Projects from git (with smart import) > Clone URI > Local folder... (selecione a pasta do projeto baixado) > Next > Next > Next > Finish;
6. Ainda no eclipse, com o projeto importado, clique na pasta raíz do projeto com o botão direito do mouse, selecione a opção "Configure", e em seguida "Add Gradle Nature";
7. Quando o eclipse finalizar o download das dependencias estabelecidas no gradle, clique novamente na pasta raíz do projeto com o botão direito do mouse, selecione a opção "Run as", e em seguida "3 Spring Boot App";
8. Abra qualquer navegador (recomendados Chrome e Firefox), e cole na barra de endereço "localhost:8081";
9. Aplicativo pronto para uso :)
