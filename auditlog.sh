#!/bin/bash

echo "save audit file in " $1
(sudo auditreduce -o file='/Users/Agnes/Desktop/test/,/Users/Agnes/Desktop/sample/,/Users/Agnes/Desktop/test-2/,/Users/Agnes/.Trash/,/Volumes/,/Users/Agnes/Dropbox/test/' /dev/auditpipe | praudit -xn) > $1
