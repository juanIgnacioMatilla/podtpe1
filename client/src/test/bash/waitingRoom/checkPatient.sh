cd ../../../../target
tar -xzf ./tpe1-g12-client-2024.1Q-bin.tar.gz
cd ./tpe1-g12-client-2024.1Q

chmod +x administrationClient.sh
chmod +x emergencyCareClient.sh
chmod +x waitingRoomClient.sh

echo "Test should pass: Cristian patients ahead should be 2"
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Cristian -Dlevel=1
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Hector -Dlevel=1
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Maxi -Dlevel=2
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Fer -Dlevel=3
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=checkPatient -Dpatient=Cristian
echo -e "\n"

echo "Test should fail: Invalid patient"
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=checkPatient -Dpatient=Rodri
echo -e "\n"
