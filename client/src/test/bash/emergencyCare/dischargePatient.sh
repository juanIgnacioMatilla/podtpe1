cd ../../../../target
tar -xzf ./tpe1-g12-client-2024.1Q-bin.tar.gz
cd ./tpe1-g12-client-2024.1Q
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Juan -Dlevel=2
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Mauro -Dlevel=5
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Mauro -Davailability=available
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Juan -Davailability=available
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Cristian -Dlevel=3
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=carePatient -Droom=2
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=dischargePatient -Droom=2 -Ddoctor=Mauro -Dpatient=Cristian
