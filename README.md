# Requisitos

Deben tener instalados maven y Java 21 instalados para poder correr el proyecto.

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

Primero, se debe levantar el servidor:

```bash
./run_server.sh
```

Luego, para correr cada servicio, se debe ejecutar desde la carpeta raíz del proyecto:

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
./run_client.sh queryClient.sh -DserverAddress=xx.xx.xx.xx:yyyy \
-Daction=actionName -DoutPath=filePath.csv [ -Droom=roomNumber ]
```
