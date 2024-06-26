![Captura de pantalla 2024-06234-26 030513](https://github.com/Hack-Gamer001/G16-proyecto-aplicativo-taqueria/assets/119648000/f0b64bad-b761-4d0f-ba0d-9b33c261b1d6)

para tener y conectar con la base de datos en nube entrar https://www.cloudclusters.io/cloud/
registrarse o iniciar session , en mi caso cuentas temporales

![image](https://github.com/Hack-Gamer001/G16-proyecto-aplicativo-taqueria/assets/119648000/f997693c-3f44-4b4d-9a46-0fdba3080189)
en mi caso busco SQL SERVER

![image](https://github.com/Hack-Gamer001/G16-proyecto-aplicativo-taqueria/assets/119648000/ed995d7c-f2c6-43d3-8dcf-3a8647ee6ebe)

seleccionar la version de sql server (2017,2019,2022) y entrar a prueba gratuita de 7 dias 

![image](https://github.com/Hack-Gamer001/G16-proyecto-aplicativo-taqueria/assets/119648000/ee34bf04-992c-4ebb-a634-b8ef8e8bfdbb)
llenar datos aleatorios para la verificacion y esperar a que se cree el servidor

![image](https://github.com/Hack-Gamer001/G16-proyecto-aplicativo-taqueria/assets/119648000/28634862-0d7d-400d-8f7c-fee90fd3a172)

entrar a manage

![image](https://github.com/Hack-Gamer001/G16-proyecto-aplicativo-taqueria/assets/119648000/caab8d1a-da65-4f43-9577-052397c1ff47)

aca estan todos los accesos para copiar en android studio

![image](https://github.com/Hack-Gamer001/G16-proyecto-aplicativo-taqueria/assets/119648000/5e345f34-ae30-4bc9-bb60-7c6a0ce96856)

crear la base de datos y los usuarios o administradores

![image](https://github.com/Hack-Gamer001/G16-proyecto-aplicativo-taqueria/assets/119648000/d282bc58-49bb-4cdc-b7d3-e399ee229bd4)

con esos datos configurar 

![image](https://github.com/Hack-Gamer001/G16-proyecto-aplicativo-taqueria/assets/119648000/d86efdaa-086c-4d4e-88d3-8d42dda1c89a)

crear el usuario con super o superusuario o administrador y una contraseña

en el dataconeccion de android studio cambiar estas variables 

<FONT SIZE=6 COLOR="red">E</FONT><FONT SIZE=4>sto es una </FONT>

public class DatabaseConnection {

private static final String URL = "jdbc:jtds:<span style="color:blue;">sqlserver://mssql-157912-0.cloudclusters.net:18171</span>/<span style="color:green;">Taqueria</span>";
private static final String USER = "Gremio";
private static final String PASSWORD = "123qwe123QWE-";



