cd ../../../../target
tar -xzf ./tpe1-g12-client-2024.1Q-bin.tar.gz
cd ./tpe1-g12-client-2024.1Q

chmod +x administrationClient.sh
chmod +x waitingRoomClient.sh
chmod +x emergencyCareClient.sh



echo "Test should pass: Cristian should be attended by Maxi and then discharged"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Maxi -Dlevel=3
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Maxi -Davailability=available
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Cristian -Dlevel=3
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=carePatient -Droom=1
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=dischargePatient -Droom=1 -Ddoctor=Maxi -Dpatient=Cristian
echo -e "\n"

echo "Test should fail: Nonexistent doctor"
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Tomas -Dlevel=3
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=carePatient -Droom=1
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=dischargePatient -Droom=1 -Ddoctor=Fer -Dpatient=Tomas
echo -e "\n"

echo "Test should fail: Invalid doctor"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Duki -Dlevel=3
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Duki -Davailability=available
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=dischargePatient -Droom=1 -Ddoctor=Duki -Dpatient=Tomas
echo -e "\n"

echo "Test should fail: Invalid room"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Beto -Dlevel=3
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=dischargePatient -Droom=2 -Ddoctor=Maxi -Dpatient=Beto
echo -e "\n"

