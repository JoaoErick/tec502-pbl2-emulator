FROM alpine

# Entrando no diretório raiz
WORKDIR /root

# Instalando o OpenJDK
RUN apk add openjdk8

# Instalando o Apache Ant
RUN apk add apache-ant

# Adicionando a pasta do projeto e o arquivo de build no container
ADD src /root/src
COPY build.xml /root

# Definindo a partir de qual ponto o container irá executar
ENTRYPOINT ant