# Pŕe requisitos #
- AWS Services (S3 e SES)
- Máquina linux
- nginx 1.12.2
- Java 11
- Maven
- Docker
- Banco de dados PostgreSQL

# Buildando a imagem docker #
- Acessar caminho onde está o código fonte (raiz)
- Executar "mvn install -U -P docker"

# Colocando imagens no S3 #
- Criar um bucket no S3 com o nome de sua prefêrencia. ex: onebr-images
- Copiar todas as imagens localizadas no diretório **"imagens"** do projeto para o bucket S3 criado.

# Configurando variaveis de ambiente e diretório de imagens #
- Criar diretório /home/onebr/images
- Copiar o arquivo **vars.env** localizado no diretório **"config"** do projeto, para um diretório de sua escolha. ex: /home/onebr
- Preencher as propriedades do arquivo com os valores necessários.

**vars.env** file
```
	JWT_SECRET=<qualquer_password_se_sua_escolha>
	AWS_KEY=<key_de_acesso_aws>
	AWS_SECRET=<secret_de_acesso_aws>
	AWS_S3_BUCKET_IMAGES=<nome_do_bucket_onde_estao_as_imagens>
	DATABASE_HOST=<endereco_do_banco_de_dados>
	DATABASE_PORT=<porta_do_banco_de_dados>
	DATABASE_NAME=<nome_do_banco_de_dados>
	DATABASE_USER=<usuario_do_banco_de_dados>
	DATABASE_PASSWORD=<password_do_banco_de_dados>
```
\* o usuário associado a AWS_KEY deve ter permissão ao S3 e aos SES(o SES deve conter os emails **system@onehealthbr.com** e **system@onehealthbr.com** configurados para envio).

\* o banco de dados informado deve estar **completamente vazio**, pois os scripts de migração serão executados automaticamente pelo flyway ao executar o sistema pela primeira vez

# Configurando o nginx para servir imagens e api #
- Configurar um location **"location /api"** com *proxy_pass* para **http://localhost:8080**
ex:
```
	location /api {
      	proxy_set_header   Host              $host;
        proxy_set_header   X-Real-IP         $remote_addr;
        proxy_set_header   X-Forwarded-For   $proxy_add_x_forwarded_for;
        proxy_set_header   X-Forwarded-Proto $scheme;
	    
	    rewrite /api/(.*) /$1  break;
        proxy_pass http://localhost:8080;
	}

```
- Configurar um location **"location /api/images"** com *alias* para **/home/onebr/images**
ex:
```
	location /api/images {
	    alias /home/onebr/images;
	}

```
# Adicionando extension necessaria ao banco de dados #
- Acessar banco de dados
- Executar "CREATE EXTENSION unaccent;" \*utilizada para que o sistema possa efetuar pesquisas de palavras acentuadas

# Rodando o sistema #
- Após o build da imagem docker, deve existir uma imagem com o nome **onebr-service** com a tag **0.0.1**
- Executar "docker run --net=host -p 8080:8080 --name onebr-service -v /home/onebr/images/:/home/onebr/images/ --env-file /home/onebr/vars.env -d onebr-service:0.0.1"