#!/bin/bash

docker compose up --build &
java -jar pap-0.1.jar
