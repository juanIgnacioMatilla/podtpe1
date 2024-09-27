cd ../../../../target
tar -xzf ./tpe1-g12-client-2024.1Q-bin.tar.gz
cd ./tpe1-g12-client-2024.1Q

chmod +x administrationClient.sh

echo "Test should success"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Juan -Dlevel=2
echo -e "\n"

echo "Test should fail: Doctor already exists"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Juan -Dlevel=2
echo -e "\n"

echo "Test should fail: Invalid levels for doctor"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Martin -Dlevel=6
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=Martin -Dlevel=0
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addDoctor -Ddoctor=test1 -Dlevel=-1
echo -e "\n"
