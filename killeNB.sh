#!/bin/bash
# Description: This file is for killing all the running insatnces of eNBs
# Usage: sudo ./killeNB.sh 
cmd1=`ps -ef|grep "sctpclient-1\.0-SNAPSHOT-jar"|awk '{print $2}'`
echo "${cmd1} ####> "
for process in "${cmd1[@]}"
do	
	echo "killing ${process}"
	`kill -9 ${process}`
done
