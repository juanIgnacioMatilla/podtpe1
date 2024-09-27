cd ../../../../target
tar -xzf ./tpe1-g12-client-2024.1Q-bin.tar.gz
cd ./tpe1-g12-client-2024.1Q

chmod +x administrationClient.sh
chmod +x emergencyCareClient.sh
chmod +x waitingRoomClient.sh

#ROOMS
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
#DOCTORS
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Juan -Dlevel=2
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Mauro -Dlevel=5
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Tomi -Dlevel=1
#SET AVAILABLE DOCTORS
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Mauro -Davailability=available
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Juan -Davailability=available
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Tomi -Davailability=available
#PATIENTS
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Cristian -Dlevel=3
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Dillom -Dlevel=2
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Papo -Dlevel=5
#CAREPATIENT
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=carePatient -Droom=2
#CAREALLPATIENTS
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=careAllPatients

./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=dischargePatient -Droom=2 -Ddoctor=Mauro -Dpatient=Papo
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=dischargePatient -Droom=1 -Ddoctor=Juan -Dpatient=Dillom

