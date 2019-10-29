#!/bin/bash
# Usage: sudo ./enodeb_terminal.sh <path and name of csv model file>
#	 ex: sudo ./enodeb_terminal.sh ../file_test13.csv
# Description:	This script will 
#			1} add and bind the ip addresses to the local loopback interface (lo)
#			2} run 10 instances of eNB from enodeb directory which takes a param of the csv file of the model and outputs the logs to the /tmp dir for debugging

# 1} add the ip's to the local loopback (sudo ip addr add 1.1.1.x/32 dev lo)
for i in $(seq 20)
do
	cmdIpaddition="ip addr add 1.1.1.${i}/32 dev lo"
	echo "${cmdIpaddition}"
	${cmdIpaddition} >> /tmp/ipAddition.txt
done
echo "########"


# 2} Running 10 instances of eNB and redirecting the logs to /tmp directory

ip=2
port=12345
pwd
#cd "$(dirname "$0")"
for i in $(seq 10)
do
	cmd="java -jar ${HOME}/enodeb/target/sctpclient-1.0-SNAPSHOT-jar-with-dependencies.jar 1.1.1.1 7891 1.1.1.${ip} ${port}"
	${cmd} > /tmp/${ip-1}_eNB.txt 2>&1 &
	ip=$((ip+1))
	port=$((port+1))
	echo "Running: ${cmd}"
done

