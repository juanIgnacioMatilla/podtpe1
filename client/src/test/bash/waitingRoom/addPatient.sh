cd ../../../../target
tar -xzf ./tpe1-g12-client-2024.1Q-bin.tar.gz
cd ./tpe1-g12-client-2024.1Q

echo "Test should pass: Added patient Cristian"
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Cristian -Dlevel=3
echo -e "\n"

echo "Test should fail: Added repeated patient Cristian"
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Cristian -Dlevel=3
echo -e "\n"

echo "Test should fail: Added patient Cristian while he's being attended"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Juan -Dlevel=3
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Juan -Davailability=available
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=carePatient -Droom=1
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Cristian -Dlevel=3
echo -e "\n"

echo "Test should fail: Added patient Fer that has already been attended"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Mauro -Dlevel=4
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Mauro -Davailability=available
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Fer -Dlevel=4
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=carePatient -Droom=2
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=dischargePatient -Droom=2 -Ddoctor=Mauro -Dpatient=Fer
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Fer -Dlevel=4
echo -e "\n"


