#!/usr/bin/env bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

docker-compose -f "$DIR"/../docker-compose.yml -p promena-jasper-report stop -t 3 $1 && docker-compose -f "$DIR"/../docker-compose.yml -p promena-jasper-report rm -f $1 && docker-compose -f "$DIR"/../docker-compose.yml -p promena-jasper-report build && docker-compose -f "$DIR"/../docker-compose.yml -p promena-jasper-report create $1 && docker-compose -f "$DIR"/../docker-compose.yml -p promena-jasper-report start $1