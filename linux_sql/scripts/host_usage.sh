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


timestamp=$(date "+%Y-%m-%d-%H:%M:%S")
hostname=$(hostname -f)
memory_free=$(vmstat --unit M | awk 'FNR == 3 {print $4}')
cpu_idle=$(vmstat | awk 'FNR == 3 {print $15}')
cpu_kernel=$(vmstat | awk 'FNR == 3 {print $14}')
disk_io=$(vmstat -d | awk 'FNR == 3 {print $10}')
disk_available=$(df -BM /| awk 'FNR == 2 {print $4}')

# Construct the insert statement
insert_statement="INSERT INTO host_usage (timestamp,host_id,memory_free,cpu_idle,cpu_kernel,disk_io,disk_available) 
				  VALUES ($timestamp,(SELECT id FROM host_info WHERE hostname = $hostname),$memory_free,$cpu_idle,$cpu_kernel,$disk_io,$disk_available)"

psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c $insert_statement

exit 0
