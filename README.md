![image](https://github.com/user-attachments/assets/fcc0863c-8495-4081-aebc-5e0f1a01f39f)# mrs-call-history-rx
Challenge Backend Calls History (Spring WebFlux , Postgress compose , Redis compose , Swagger, handlerError, ReactiveRepository)

**Consideraciones:**

1.- Java 17

2.- Gradle.

3.- Habilitar Lombok.

4.- Instalar Redis Insight para monitoreo de cache.

5.- Tener Instalador Docker

## 3. Ejecución del proyecto

1.- Bajar el repositorio 

git clone https://github.com/Smithpizarro/mrs-call-history-rx.git

2.- Abrir el proyecto en Intellij IDEA

3.- Ir a File despues ir a la opcion Setting

4.- En Build, Execution, Deployment  seleccionar el JDK 17

     ![image](https://github.com/user-attachments/assets/7d6fc616-752b-404a-9d67-66495e83c867)

5.- Seleccionar Ok.

6.-  Ir a Project Structure 

7.-  Seleccionar seleccionar el SDK de java 17 instalado.

![image](https://github.com/user-attachments/assets/85566108-0a5a-47a5-91c1-814164fec35b)

8.- En el panel Gradble , seleccionar Clean y despues Install

![image](https://github.com/user-attachments/assets/4259eaae-2552-4a04-b9e2-c75ef475a174)

9.-  Abrir una Consola  e ir a la ruta de proyecto

![image](https://github.com/user-attachments/assets/663cae0c-657d-427d-a58f-a7952406b791)

10.- ejecutar el comando:
      docker compose up
      
9.- Ejecutar el MrsCallHistoryRxApplication

![image](https://github.com/user-attachments/assets/ca59dead-fa0b-47f4-a180-f860126b5af5)

## 3. Escenarios de prueba

**3.1 Creacion de una peticion llamando a servicio mock rate habilitado para el calculado:**

 **Servicio Mock ok 200**
 https://run.mocky.io/v3/e649656e-f9a9-4153-9bf7-8cf22361f1b9
 
![image](https://github.com/user-attachments/assets/8082cc2f-a55f-4558-b4d5-6b7d7c3f375d)

Ejecutar la peticion CreateCall del postman  

![image](https://github.com/user-attachments/assets/5b548037-b3e6-44c7-bc9c-13d3ebe726e5)

https://github.com/Smithpizarro/mrs-call-history-rx/blob/main/ApiCallHistory.postman_collection.json

![image](https://github.com/user-attachments/assets/105b0463-1e7b-4892-a05a-05dddf00ec27)

 **Resultado :** Se guardo la peticin en la Bd y guardo el rate o porcentaje en la memoria de Redis con 5min a expirar

 **3.2 Creacion de una peticion llamando a servicio mock rate No habilitado :**

 **Servicio Mock Error 500**
[ https://run.mocky.io/v3/e649656e-f9a9-4153-9bf7-8cf22361f1b9](https://run.mocky.io/v3/43000da6-1324-4993-afe4-883e44ca766f)
 ![image](https://github.com/user-attachments/assets/1dce3407-df53-4b34-a018-ca2682383131)


Ejecutar la peticion CreateCall del postman  

![image](https://github.com/user-attachments/assets/ae216608-7d05-461e-aa83-df810230ab72)

https://github.com/Smithpizarro/mrs-call-history-rx/blob/main/ApiCallHistory.postman_collection.json

![image](https://github.com/user-attachments/assets/a8c6fca4-3f0e-4dac-998a-f880a0445f32)


 **Resultado :** Se guardo la peticiOn en la Bd y se consuto al rate o porcentaje de la memoria de Redis 

 **3.3  Creacion de una peticion tipo Error llamando al servicio mock rate No habilitado y la llave en la memoria ya expirado :**

 **Servicio Mock Error 500**
[ https://run.mocky.io/v3/e649656e-f9a9-4153-9bf7-8cf22361f1b9](https://run.mocky.io/v3/43000da6-1324-4993-afe4-883e44ca766f)

![image](https://github.com/user-attachments/assets/2d3c2779-7f92-4c09-bcd3-220af3f81338)

![image](https://github.com/user-attachments/assets/c97c25a5-b9e1-4610-bfaf-8ccdef8e2726)


Ejecutar la peticion CreateCall del postman  

![image](https://github.com/user-attachments/assets/89545373-aabc-4179-9e7f-00a497043a81)

https://github.com/Smithpizarro/mrs-call-history-rx/blob/main/ApiCallHistory.postman_collection.json

![image](https://github.com/user-attachments/assets/3abde8b6-1c1a-433b-bf32-9cf658305868)


 **Resultado :** Se guardo la petición tipo Error en la Bd, pero respondio con un mensaje de Error


**3.4  Listado de la peticiones guardadas en la BD**

Ejecutar la peticion GetCallHistory del postman 
![image](https://github.com/user-attachments/assets/cedeb80f-45dc-438f-bea2-830bd4709d5c)

**3.5  Ruta del swagger**

http://localhost:8092/swagger-doc/webjars/swagger-ui/index.html
![image](https://github.com/user-attachments/assets/03b57378-bad1-4b71-9d36-d77ff848846a)



