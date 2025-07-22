# gh-api

Para rodar o back-end, rode os seguintes comandos:

1 -> mvn wrapper:wrappe (necessário apenas a primeira vez)
2 -> chmod +x mvnw (necessário apenas a primeira vez)
3 -> systemctl status postgresql
  Se estiver desligado:
    sudo systemctl start postgresql
4 -> ./mvnw clean install && ./mvnw spring-boot:run
