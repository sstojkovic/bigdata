#!/bin/bash

if [[ -z "$ES_DATA_DIR" ]]; then
  echo "ES_DATA_DIR environment variable is not set"
  echo "Set it and make sure it points to dataset folder"
  exit 1
fi

docker-compose down
docker-compose up --build