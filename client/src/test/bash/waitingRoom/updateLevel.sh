cd ../../../../target
tar -xzf ./tpe1-g12-client-2024.1Q-bin.tar.gz
cd ./tpe1-g12-client-2024.1Q

chmod +x administrationClient.sh
chmod +x emergencyCareClient.sh
chmod +x waitingRoomClient.sh

echo "Test should pass: Update Cristian level from 1 to 2"
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Cristian -Dlevel=1
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=updateLevel -Dpatient=Cristian -Dlevel=2
echo -e "\n"

echo "Test should fail: Update level to nonexistent patient"
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=updateLevel -Dpatient=Tomi -Dlevel=4
echo -e "\n"

echo "Test should fail: Update Cristian to invalid level"
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=updateLevel -Dpatient=Cristian -Dlevel=6
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=updateLevel -Dpatient=Cristian -Dlevel=-1
echo -e "\n"

echo "Test should pass: Keep arrival order when updating a level"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Tomi -Dlevel=4
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Tomi -Davailability=available

./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Maxi -Dlevel=3
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Fer -Dlevel=4
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=updateLevel -Dpatient=Maxi -Dlevel=4
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=carePatient -Droom=1
echo -e "\n"
