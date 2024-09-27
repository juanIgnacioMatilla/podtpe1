cd ../../../../target
tar -xzf ./tpe1-g12-client-2024.1Q-bin.tar.gz
cd ./tpe1-g12-client-2024.1Q

chmod +x administrationClient.sh
chmod +x emergencyCareClient.sh
chmod +x waitingRoomClient.sh

echo "Test should pass: Cristian should be attended by Maxi"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Juan -Dlevel=1
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Juan -Davailability=available
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Fer -Dlevel=4
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Fer -Davailability=available
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Maxi -Dlevel=3
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Maxi -Davailability=available
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Cristian -Dlevel=3
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=carePatient -Droom=1
echo -e "\n"

echo "Test should fail: Invalid room"
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=carePatient -Droom=2
echo -e "\n"

echo "Test should fail: Room occupied"
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=carePatient -Droom=1
echo -e "\n"

echo "Test should fail: No available doctor to attend patient"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Hernan -Dlevel=5
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=carePatient -Droom=2
echo -e "\n"

echo "Test should pass: Lucas (4) should be attended by Fer (4)"
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Lucas -Dlevel=4
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=carePatient -Droom=2
echo -e "\n"
