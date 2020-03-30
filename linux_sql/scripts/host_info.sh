#!/bin/bash

#Check if the correct amount of arguements are given
if [ $# != 5 ];
then
	echo "Invalid amount of arguements"
	echo "Usage: ./host_info.sh psql_host psql_port db_name psql_user psql_password"
	return 1
fi

#Assign the input variables to the appropriate variables
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#hardware information
hostname=$(hostname -f)
cpu_number=$(lscpu  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(lscpu | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(lscpu | egrep "^Model name:" | awk '{$1=$2="";print $0}' | xargs)
cpu_mhz=$(lscpu | egrep "^CPU MHz:" | awk '{print $3}' | xargs)
L2_cache=$(lscpu | egrep "^L2 cache:" | awk '{print $3}' | xargs)
total_mem=$(cat /proc/meminfo | egrep "^MemTotal:" | awk '{print $2}' | xargs)
timestamp=$(date "+%Y-%m-%d-%H:%M:%S")

#Construct the insert statement
insert_statement="INSERT INTO host_info (
					  hostname, cpu_number, cpu_architecture, 
					  cpu_model, cpu_mhz, L2_cache, total_mem, 
					  timestamp
					) 
					VALUES 
					  (
					    $hostname, $cpu_number, $cpu_architecture, 
					    $cpu_model, $cpu_mhz, $L2_cache, 
					    $total_mem, $timestamp
					  )
					"

# Call the psql container
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c $insert_statement

exit 0
