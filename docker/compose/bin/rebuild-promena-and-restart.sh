#!/usr/bin/env bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

mvn -f "$DIR"/../../../promena/transformer/pom.xml clean install -Dmaven.test.skip=true && mvn -f "$DIR"/../../../promena/executable/pom.xml clean package && "$DIR"/restart-service.sh promena