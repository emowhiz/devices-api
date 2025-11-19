#!/bin/bash
mvn clean install
docker compose -f docker-compose-prod.yml up -d
