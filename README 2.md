Tecnologias:
Java 8
Maven - ADministraci'on del proyecto
Jersey - Ggeneraci'on de la Api Resful
H2k - Injecci'on de dependencias
Grizzly y Embeded Redis - Test de Integraci'on y  ejecucion de la aplicacion sin deployarla server. Cuenta con un Server Embebido de Redis que solo se ejecuta en memoria.
Swagger - Para generaci'on de documentacion de la api y Swagger-UI para visualizar y
Junit - Test
Jacoco - Reporte de Covertura de test
Jedis - Cliente Redis
Redis - Base de Datos
Tomcat 8 - WebServer
Amazon EC2 y ElasticCache - Host de la API


Requisitos para correr la aplicacion en local:
-Java8
-Maven


Para generar el jar y probar la app en local es necesario descargar el proyecto y correr desde el root del mismo el siguiente comando:



mvn clean install -Dout jar



Este comando ejecuta los test, genera el JAR en /target/mutantDetector-{Version}-jar-with-dependencies.jar. En este caso puntual es  ser'ia la ruta es/target/mutantDetector-1.0-jar-with-dependencies.jar)

La aplicacion se puede ejecutar con el siguiente comando por ejemplo:



java -jar target/mutantDetector-1.0-jar-with-dependencies.jar



Una vez inciado, si todo est'a en orden, aparecer'a el siguiente mensaje en la consola

 INFO [main] (ServersManager.java:138) - API - Documentación Online : http://localhost:8082/docs/
 INFO [main] (ServersManager.java:167) - Mutant Detector Iniciado.
Presione una tecla para finalizar...


La aplicacion se encuentra en http://localhost:8082/api/ . Ingresando a http://localhost:8082/docs/ se encuentra swagger-ui donde se puede ver la api completa.

En la Ruta /coverage/jacoco-resources/index.html se puede ver el informe de la cobertura del c'odigo testeado.


Para generar el WAR para deployarlo en un server Tomcat 8 basta con ejecutar mvn clean install
el war se encontrara generado en la ruta /target de igual manera que ocurre con la generacion del JAR.

Se encuentra una version de la aplicacion alajada en AWS EC2:
Documentacion de Swagger:

http://ec2-52-67-163-113.sa-east-1.compute.amazonaws.com:8080/mutantDetector/docs/
Swagger ejecuta las request contra la api en:
http://ec2-52-67-163-113.sa-east-1.compute.amazonaws.com:8080/mutantDetector/api/





