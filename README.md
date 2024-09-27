# Requisitos

Deben tener instalados maven y Java 21 para poder correr el proyecto.

# Compilación

En la raíz del proyecto se debe correr inicialmente:

```bash
mvn clean install
```

# Ejecución

En la raíz del proyecto:

```bash
chmod +x run_client.sh
chmod +x run_server.sh
```

Primero, se debe levantar el servidor, se le puede pasar el puerto a elección a traves de -Dport=[puerto], pero el default es 50051:

```bash
./run_server.sh -Dport=[puerto]
```

Luego, para correr cada servicio, se debe ejecutar desde la carpeta raíz del proyecto(es importante agregar el ./run_client.sh al principio del comando):

### 1. Servicio de Administración

```bash
./run_client.sh ./administrationClient.sh -DserverAddress=xx.xx.xx.xx:yyyy \
-Daction=actionName [ -Ddoctor=doctorName | -Dlevel=levelNumber | -Davailability=availabilityName ]
```

### 2. Servicio de Sala de Espera

```bash
./run_client.sh ./waitingRoomClient.sh -DserverAddress=xx.xx.xx.xx:yyyy \
-Daction=actionName [ -Dpatient=patientName | -Dlevel=levelNumber ]
```

### 3. Servicio de Atención de Emergencias

```bash
./run_client.sh ./emergencyCareClient.sh -DserverAddress=xx.xx.xx.xx:yyyy \
-Daction=actionName [ -Droom=roomNumber | -Ddoctor=doctorName | -Dpatient=patientName ]
```

### 4. Servicio de Notificación al Personal

```bash
./run_client.sh ./doctorPagerClient.sh -DserverAddress=xx.xx.xx.xx:yyyy \
-Daction=actionName -Ddoctor=doctorName
```

### 5. Servicio de Consulta

```bash
./run_client.sh ./queryClient.sh -DserverAddress=xx.xx.xx.xx:yyyy \
-Daction=actionName -DoutPath=filePath.csv [ -Droom=roomNumber ]
```

# TESTS

Los tests se encuentran en la carpeta client/src/tests/bash

Se le deben dar permisos de ejecucion a cada test de la forma

```bash
chmod +x [test].sh

```

luego este le dara los permisos necesarios a los clientes dentro de la ejecucion del test.

Para correr cada uno de estos se debe reiniciar el servidor para no dejar informacion previa en este y luego correr el test

los query tests generan csvs y luego los eliminan para no generar clutter. Si se quiere ver el csv se debe ejecutar el servicio de consulta por su cuenta.
