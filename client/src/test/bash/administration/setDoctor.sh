cd ../../../../target
tar -xzf ./tpe1-g12-client-2024.1Q-bin.tar.gz
cd ./tpe1-g12-client-2024.1Q

chmod +x administrationClient.sh
chmod +x waitingRoomClient.sh
chmod +x emergencyCareClient.sh

echo "Test should success"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Juan -Dlevel=2
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Juan -Davailability=available
echo -e "\n"

echo "Test should fail: No doctor found"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Mauro -Davailability=available
echo -e "\n"


echo "Test should fail: Doctor is attending"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Tomi -Dlevel=3
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Tomi -Davailability=available
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Cristian -Dlevel=3
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=carePatient -Droom=1
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Tomi -Davailability=available
echo -e "\n"

echo "Test should fail: Cannot set doctor attending status"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Marcelo -Dlevel=2
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Marcelo -Davailability=attending
echo -e "\n"
