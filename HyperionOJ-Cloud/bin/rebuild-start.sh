#!/bin/bash

bash stop-web.sh
bash stop-judge.sh
bash build.sh
bash start-judge.sh
bash start-web.sh