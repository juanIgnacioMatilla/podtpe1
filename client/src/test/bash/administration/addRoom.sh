cd ../../../../target
tar -xzf ./tpe1-g12-client-2024.1Q-bin.tar.gz
cd ./tpe1-g12-client-2024.1Q

chmod +x administrationClient.sh

echo "Test should success"
./administrationClient.sh -DserverAddress=localhost:50051 -Daction=addRoom
echo -e "\n"
