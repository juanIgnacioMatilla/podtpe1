cd ../../../../target
tar -xzf ./tpe1-g12-client-2024.1Q-bin.tar.gz
cd ./tpe1-g12-client-2024.1Q

chmod +x administrationClient.sh
chmod +x emergencyCareClient.sh
chmod +x waitingRoomClient.sh

echo "Test should pass: Doctor Juan is unavailable"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Juan -Dlevel=2
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=checkDoctor -Ddoctor=Juan
echo -e "\n"

echo "Test should pass: Doctor Juan is available"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Juan -Davailability=available
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=checkDoctor -Ddoctor=Juan
echo -e "\n"

echo "Test should pass: Doctor Juan is unavailable"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Juan -Davailability=unavailable
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=checkDoctor -Ddoctor=Juan
echo -e "\n"

echo "Test should pass: Doctor Tomi is attending"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Tomi -Dlevel=3
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Tomi -Davailability=available
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Cristian -Dlevel=3
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=carePatient -Droom=1
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=checkDoctor -Ddoctor=Tomi
echo -e "\n"

echo "Test should fail: Doctor Pedro doesn't exist "
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=checkDoctor -Ddoctor=Pedro
echo -e "\n"
