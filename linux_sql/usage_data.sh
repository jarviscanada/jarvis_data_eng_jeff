#!/bin/bash

#get cpu information
lscpu_out=`lscpu`
#create a timestamp function
timestamp() {
	date +%Y-%m-%d %H-%M-%S
}

#hardware
hostname=$(hostname -f)
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out" | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out" | egrep "^Model name:" | awk '{$1=$2="";print $0}' | xargs)
cpu_mhz=$(echo "$lscpu_out" | egrep "^CPU MHz:" | awk '{print $3}' | xargs)
l2_cache=$(echo "$lscpu_out" | egrep "^L2 cache:" | awk '{print $3}' | xargs)
total_mem=$(cat /proc/meminfo | egrep "^MemTotal:" | awk '{print $2}' | xargs)
timestamp= timestamp
#usage
memory_free=$(vmstat --unit M | awk 'FNR == 3 {print $4}')
cpu_idle=$(vmstat | awk 'FNR == 3 {print $15}')
cpu_kernel=$(vmstat | awk 'FNR == 3 {print $14}')
disk_io=$(vmstat -d | awk 'FNR == 3 {print $10}')
disk_available=$(df -BM /| awk 'FNR == 2 {print $4}')