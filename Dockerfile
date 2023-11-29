# Use uma imagem base do Ubuntu 18.04
FROM ubuntu:18.04 AS maven_builder

# Instalar o Maven, o OpenJDK 11, curl, git e bash
RUN apt update && apt install -y maven openjdk-11-jdk curl git bash

# Configurar o diretório de trabalho
WORKDIR /app

# Adicionar o arquivo pom.xml separadamente para aproveitar o cache
ADD pom.xml /app/pom.xml

# Configurar o git para usar HTTPS em vez de git
RUN git config --global url."https://".insteadOf git://

# Argumentos para o build (TIER, CONTEXT_USE, MOBILE_APP)
ARG TIER
ARG CONTEXT_USE
ARG MOBILE_APP

# Instalar o NVM (Node Version Manager)
SHELL ["/bin/bash", "--login", "-c"]
RUN curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.5/install.sh | bash

# Ativar o NVM
SHELL ["/bin/bash", "--login", "-c"]
RUN source ~/.nvm/nvm.sh && nvm install 14 && nvm alias default 14

# Usar repositórios externos (JAR e NPM)
RUN mvn dependency:go-offline -B
ADD . /app
RUN cd /app && mvn package -X -Dcronapp.profile=${TIER} -Dcronapp.useContext=${CONTEXT_USE} -Dcronapp.mobileapp=${MOBILE_APP}

# Configurar o Tomcat como a fase final
FROM tomcat:9.0.17-jre11

# Remover aplicativos padrão do Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Criar um grupo e usuário 'tomcat'
RUN groupadd tomcat && useradd -s /bin/false -M -d /usr/local/tomcat -g tomcat tomcat

# Copiar o WAR gerado pelo Maven para o diretório webapps do Tomcat
COPY --from=maven_builder /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Definir as permissões corretas para o Tomcat
RUN chown tomcat:tomcat -R /usr/local/tomcat

# Mudar para o usuário 'tomcat'
USER tomcat
