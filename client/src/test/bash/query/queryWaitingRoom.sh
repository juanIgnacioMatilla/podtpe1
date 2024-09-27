cd ../../../../target
tar -xzf ./tpe1-g12-client-2024.1Q-bin.tar.gz
cd ./tpe1-g12-client-2024.1Q

chmod +x administrationClient.sh
chmod +x waitingRoomClient.sh
chmod +x queryClient.sh
chmod +x emergencyCareClient.sh

echo "Test should fail: No patients in waitingRoom"
./queryClient.sh  -DserverAddress=localhost:50051 -Daction=queryWaitingRoom -DoutPath=$PWD/roomsTest.csv
cat $PWD/roomTest.csv
rm $PWD/roomTest.csv
echo -e "\n"

echo "Test should pass: 5 patients"
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Cristian -Dlevel=3
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Mauro -Dlevel=4
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Sebastian -Dlevel=3
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Pato -Dlevel=1
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Duki -Dlevel=5
./queryClient.sh  -DserverAddress=localhost:50051 -Daction=queryWaitingRoom -DoutPath=$PWD/roomsTest.csv
cat $PWD/roomsTest.csv
rm $PWD/roomsTest.csv

echo -e "\n"


