# Proyecto Hibernate - Gestión de Videojuegos

Este proyecto demuestra el uso de **Hibernate (JPA)** para la persistencia de datos en una base de datos MySQL, junto con ejemplos de conexión a **MongoDB** y **BaseX** (las vistas en clase). La aplicación modela un sistema de gestión de videojuegos, desarrolladoras, jugadores y compras.
 
## Tecnologías utilizadas
- **Java 21**
- **Maven** - Gestión de dependencias y construcción
- **Hibernate ORM** (JPA) - [Maven Repository](https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-core) - Proveedor de JPA para el mapeo objeto-relacional
- **MySQL Connector/J** - [Maven Repository](https://mvnrepository.com/artifact/com.mysql/mysql-connector-j) - Driver JDBC para conectar con MySQL
- **MongoDB Java Driver** (sync) - [Maven Repository](https://mvnrepository.com/artifact/org.mongodb/mongodb-driver-sync) - Cliente para conectar con MongoDB
- **BaseX Client** - [Maven Repository](https://mvnrepository.com/artifact/org.basex/basex) - Cliente para conectar con BaseX
- **SLF4J Simple** - [Maven Repository](https://mvnrepository.com/artifact/org.slf4j/slf4j-simple) - Implementación de logging

## Estructura del proyecto - Miscellaneous
1. pom.xml - Gestión de dependencias
2. hibernate.cfg.xml - Configuración alternativa de Hibernate
3. database.properties - Propiedades de conexión a MySQL

## Estructura del proyecto - Clases
1. HibernatePlay.java - Clase para ejecutar toda la parte de Hibernate
2. MongoDBPlay.java - Clase para ejecutar toda la parte de MongoDB
3. BaseXPlay.java - Clase para ejecutar toda la parte de BaseX
   1. hibernate/
      1. OptionHibernate.java
   2. manager/
      1. CaseOfUseManager.java
   3. models/
      1. Desarrolladora.java
      2. Videojuego.java
      3. Jugador.java
      4. Compra.java
   4. utils/
      1. HibernateUtility.java
   5. src/main/resources
      1. simplelogger.properties