#!/bin/bash

# shellcheck disable=SC2164
cd ~/hyperion-oj/HyperionOJ-Cloud/

git pull

mvn clean

mvn compile

mvn install

rm -rf ~/HyperionOJ-WEB/release/

mv ~/hyperion-oj/HyperionOJ-Cloud/hyperionoj-web/target/hyperionoj-web-1.0.0.jar ~/HyperionOJ-WEB/release/


