#!/bin/bash

# shellcheck disable=SC2164
cd ~/HyperionOJ/HyperionOJ-Cloud

git pull

mvn clean

mvn compile

mvn install

rm -rf ~/HyperionOJ-WEB/release/

mv ~/HyperionOJ/HyperionOJ-Cloud/hyperionoj-web/target/hyperionoj-web-1.0.0.jar ~/HyperionOJ-WEB/release/


