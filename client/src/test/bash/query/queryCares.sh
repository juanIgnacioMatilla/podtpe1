cd ../../../../target
tar -xzf ./tpe1-g12-client-2024.1Q-bin.tar.gz
cd ./tpe1-g12-client-2024.1Q

chmod +x administrationClient.sh
chmod +x waitingRoomClient.sh
chmod +x queryClient.sh
chmod +x emergencyCareClient.sh

echo "Test should fail: No cares have been done"
./queryClient.sh  -DserverAddress=localhost:50051 -Daction=queryCares -DoutPath=$PWD/roomsTest.csv
cat $PWD/roomTest.csv
rm $PWD/roomTest.csv
echo -e "\n"

echo "Test should pass: 5 patients"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom 
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Cristian -Dlevel=3
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Mauro -Dlevel=4
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Sebastian -Dlevel=3
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Pato -Dlevel=1
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Duki -Dlevel=5
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Jaiba -Dlevel=5
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=AAAAAA -Dlevel=3
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Jaiba -Davailability=available
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=AAAAAA -Davailability=available
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=carePatient -Droom=1
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=carePatient -Droom=2
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=dischargePatient -Droom=1 -Ddoctor=Jaiba -Dpatient=Duki
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=dischargePatient -Droom=2 -Ddoctor=AAAAAA -Dpatient=Cristian
./queryClient.sh -DserverAddress=localhost:50051 -Daction=queryCares -DoutPath=$PWD/roomsTest.csv -Droom=1
cat $PWD/roomsTest.csv
rm $PWD/roomsTest.csv

echo -e "\n"

echo "Test should pass: 5 patients"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom 
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Ernesto -Dlevel=3
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Fabian -Dlevel=4
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Saul -Dlevel=3
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=Hernisbo -Dlevel=1
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=YSY -Dlevel=5
./waitingRoomClient.sh -DserverAddress=localhost:50051 -Daction=addPatient -Dpatient=neo -Dlevel=5
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Jaiba -Dlevel=5
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=AAAAAA -Dlevel=3
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=Jaiba -Davailability=available
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=setDoctor -Ddoctor=AAAAAA -Davailability=available
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=carePatient -Droom=1
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=carePatient -Droom=2
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=dischargePatient -Droom=1 -Ddoctor=Jaiba -Dpatient=YSY
./emergencyCareClient.sh -DserverAddress=localhost:50051 -Daction=dischargePatient -Droom=2 -Ddoctor=AAAAAA -Dpatient=Sebastian
./queryClient.sh -DserverAddress=localhost:50051 -Daction=queryCares -DoutPath=$PWD/roomsTest.csv
cat $PWD/roomsTest.csv
rm $PWD/roomsTest.csv

