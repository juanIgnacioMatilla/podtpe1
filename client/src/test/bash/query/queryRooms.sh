cd ../../../../target
tar -xzf ./tpe1-g12-client-2024.1Q-bin.tar.gz
cd ./tpe1-g12-client-2024.1Q

echo "Test should failed: No rooms"
./queryClient.sh  -DserverAddress=localhost:50051 -Daction=queryRooms -DoutPath=roomsTest.csv
cat ./roomTest.csv
echo -e "\n"

echo "Test should pass: 5 rooms"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
./queryClient.sh  -DserverAddress=localhost:50051 -Daction=queryRooms -DoutPath=roomsTest.csv
cat roomTest.csv
echo -e "\n"


echo "Test should pass: 4 free rooms 1 occupied"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Maxi -Dlevel=3
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Maxi -Davailability=available
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Cristian -Dlevel=3
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=carePatient -Droom=1
./queryClient.sh  -DserverAddress=localhost:50051 -Daction=queryRooms -DoutPath=roomsTest.csv
cat roomTest.csv
echo -e "\n"
