#!/bin/bash

#create a timestamp function
timestamp() {
	date "+%Y-%m-%d %H-%M-%S"
}

#hardware
hostname=$(hostname -f)
cpu_number=$( lscpu  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$( lscpu | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$( lscpu | egrep "^Model name:" | awk '{$1=$2="";print $0}' | xargs)
cpu_mhz=$(lscpu | egrep "^CPU MHz:" | awk '{print $3}' | xargs)
l2_cache=$( lscpu | egrep "^L2 cache:" | awk '{print $3}' | xargs)
total_mem=$(cat /proc/meminfo | egrep "^MemTotal:" | awk '{print $2}' | xargs)
timestamp= timestamp
#usage
memory_free=$(vmstat --unit M | awk 'FNR == 3 {print $4}')
cpu_idle=$(vmstat | awk 'FNR == 3 {print $15}')
cpu_kernel=$(vmstat | awk 'FNR == 3 {print $14}')
disk_io=$(vmstat -d | awk 'FNR == 3 {print $10}')
disk_available=$(df -BM /| awk 'FNR == 2 {print $4}')