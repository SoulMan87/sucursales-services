## Descripción
Esta API gestiona una lista de franquicias, donde cada franquicia incluye un nombre y un conjunto de sucursales. Cada sucursal está compuesta por un nombre y un inventario de productos disponibles en esa ubicación. A su vez, cada producto contiene un nombre y una cantidad de stock.

La aplicación está desarrollada en Java 21 utilizando Spring Boot 3.3.4, y emplea programación reactiva y una base de datos MySQL 8.0.

## Requisitos Previos
1. JDK 17 ó 21
2. Configurar la variable de entorno JAVA_HOME con la ruta de instalación de JDK 17
3. MySQL 8.0.*
4. Docker: Guía de instalación
5. Terraform: Instrucciones de instalación
6. AWS CLI: Guía de inicio e instalación

## Para iniciar el servicio

1. Compila la aplicación con el siguiente comando: `mvn clean package`
2. Crea la imagen Docker de la aplicación con el siguiente comando: `docker build -t sucursales-services-app .`
3. Ejecuta la imagen Docker localmente con:
   `docker run -e SERVER_PORT=8080 -e DB_CONNECTION=r2dbc:mysql://host.docker.internal:3306/franquiciasdb -e DB_USERNAME=usuario_db -e DB_PASSWORD=password_db -p 8080:8080 franquicias-app:latest`

## Para crear una instancia de AWS RDS con MySQL 8.0
En el directorio terraform-project, Terraform está configurado para crear la infraestructura de una instancia de AWS RDS con MySQL 8.0.
1. Navega a la carpeta terraform-project.
2. Inicializa Terraform ejecutando: `terraform init`
3. Revisa el plan de infraestructura con: `terraform plan`
4. Aplica la configuración de infraestructura con: `terraform apply`

## Creación de tablas en la base de datos
Dentro del directorio scripts se encuentra el archivo franquicias.sql, que contiene las instrucciones DDL para crear las tablas necesarias para esta aplicación.

1. Dirígete a la carpeta scripts.
2. Ejecuta el siguiente comando para crear las tablas `mysql -h MYSQL_HOST -u USER_NAME -p franquiciasdb < franquicias.sql`

## Para ejecutar los tests unitarios
Para ejecutar los tests y generar un informe de cobertura, utiliza el siguiente comando: `mvn test jacoco:report`