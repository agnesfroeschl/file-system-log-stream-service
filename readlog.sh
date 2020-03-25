#!/bin/bash

auditfile=$1
propertyFile=$2
address=""
port=5043

index=0
old_index=0
old_numlines=0

# get IP Address from client
ip=$(ifconfig en0 |awk '/inet / {print $2; }')
# get hostname from client
hostName=$(hostname | { read hostName; echo "$hostName";})
# add "host" tag with ip and hostname information
host="<host hostName=\"$hostName\" ip=\"$ip\"/>"


readApplicationProperties(){
    if [ -f "$propertyFile" ]
    then
      echo "$propertyFile found."

      while IFS='=' read -r key value
      do
        key=$(echo $key | tr '.' '_')
        eval ${key}=\${value}
        echo $key
      done < "$propertyFile"

        address=${server_address}
   #     port=${server_port}
        echo send log data to http://""$address":"$port
    else
      echo ".properties file not found."
    fi
}

readLogs(){
echo redlog startet - auditfile: $auditfile
    while true
    do
        numlines=$(wc -l $auditfile | awk '{ print $1 }')
         if [ "$old_numlines" -lt "$numlines" ]; then

            index=0
            #echo "new index set: " "$index"
            #echo "numlines: " "$numlines"
            #echo "new old_index set: " "$old_index"

                while IFS= read -r line; do
                index=$(($index + 1))
                #echo "index: " "$index"
                    # read lines until index reaches total number of lines
                    if [ "$index" -ge 3 ] && [ "$index" -gt "$old_index" ] && [ "$index" -le "$numlines" ]; then
                        IFS=' ' read -a fields <<< "$line";
                        if [ "${fields[0]}" == "</record>" ]; then
                            ARRAY+=("$host")
                            ARRAY+=("$line")
                            message=${ARRAY[*]};
                            ARRAY=()
                            curl -X POST -i -H "Content-type: text/xml" -X POST http://""$address":"$port -d "$message";
                            old_index="$index"
                         else
                            ARRAY+=("$line")
                        fi
                    fi
                    old_numlines="$numlines"
                done < $auditfile
         fi
    done
}


readApplicationProperties
readLogs