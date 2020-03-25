#!/usr/bin/env bash

#started by: "sh startaudit.sh src/main/resources/application.properties"

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

echo "Starting logstash ..."
osascript -e 'tell application "Terminal" to do script "cd '$DIR'/logstash-6.3.0/
echo '$1' | sudo -S bin/logstash"'
sleep 60

echo "Starting Triplewave for file system logs ..."
osascript -e 'tell application "Terminal" to do script "cd '$DIR'/TripleWave-fileSystemLog/
echo '$1' | ./start.sh"'
sleep 5
echo "ready to start application"


#######################################################
echo "start client components"
timestamp="$(date +%Y-%m-%d.%H-%M-%S)"
mkdir auditfiles
cd auditfiles
tmpfile=${DIR}/auditfiles/auditoutput"."${timestamp}.txt
properties="src/main/resources/application.properties"

echo "file created for auditlogs: "${tmpfile}
osascript -e 'tell application "Terminal" to do script "cd '${DIR}'
echo '$1' | sh '${DIR}'/auditlog.sh '${tmpfile}'"'

osascript -e 'tell application "Terminal" to do script "cd '${DIR}'
echo '$1' | sh '${DIR}'/readlog.sh '${tmpfile}' '${properties}'"'
