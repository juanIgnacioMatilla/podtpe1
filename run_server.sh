#!/usr/bin/env bash

cd server/target
tar -xzf ./tpe1-g12-server-2024.1Q-bin.tar.gz
cd ./tpe1-g12-server-2024.1Q
./run-server.sh
