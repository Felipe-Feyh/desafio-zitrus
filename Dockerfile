# Estágio final
FROM quay.io/wildfly/wildfly

# Cria o diretório de dados para o H2 em /tmp
RUN mkdir -p /tmp/data

# Adiciona um usuário administrador para o WildFly
RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#70365 --silent

# Copia o WAR para o diretório de implantações do WildFly
COPY ./target/*.war /opt/jboss/wildfly/standalone/deployments/healthcare.war

# Define o comando padrão para iniciar o WildFly
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
