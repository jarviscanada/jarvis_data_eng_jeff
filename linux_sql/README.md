# Linux Cluster Monitoring Agent

## Introduction
  
The Linux Cluster Administration (LCA) team manages a Linux cluster of 10 nodes/servers which are running CentOS 7. These servers are internally connected through a switch and able to communicate through internal IPv4 addresses. The goal of the monitoring agent is to record the hardware specifications of each node and monitor node resource usages (e.g. CPU/Memory) in realtime. The monitoring agent is implemented using bash scripts and the PostgreSQL database, and the results of the agent will then be stored in the RDBMS database.
  
## Architecture and Design
![Image of Architecture](./assets/Architecture.png)
As shown above, the Linux Cluster Monitoring Agent will have bash scripts running in all servers. A crontab will schedule the scripts to submit a SQL request that collects the current host usage (CPU and Memory) to the psql database.

### PSQL database architecture
The PSQL database `host_data` will contain two tables.
The `host_info` table will record static information about the individual servers when the bash scripts are first installed.

The `host_usage` table will record the cpu usage data from the individual servers from the scheduled cron job.

`host_info` will record the following information
* `id` a unique self-incrementing id that will help keep track of the servers as well as serve as the primary key for the table
* `hostname` the fully qualified hostname of the server, assumed to be unique amonst the different servers.
* `cpu_number` the amount of cpu cores that the machine has.
* `cpu_architecture` the CPU's architecture.
* `cpu_model` the model of the CPU.
* `cpu_mhz` measures the CPU speed
* `L2_cache` how much space (in kB) the L2 cache has.
* `total_memory` the total amount of memory (in kB) the CPU has.
* `timestamp` when was the information recorded, measured in UTC time.

`host_usage` table will record the following information
* `timestamp`will record the UTC time when the entry was sent to the DB.
* `host_id` will refrence the host id from `hosts_info` table to identify which computer sent the report.
* `memory_free` will record how much of the memory (in MB) is not in use.
* `cpu_idle` will record the percentage of CPU that is idle.
* `cpu_kernel` will record the percentage of the kernel is being spent idle.
* `disk_io` will record the current amount of disk I/O Operations.
* `disk_available` will record how much disk space (in MB) is available.

### Script Descriptions
`psql_docker.sh` is the bash file that will set up the envrionment so that everything is prepared. `psql_docker.sh` will ensure that the docker container is started, volume created, as well as the PSQL container running so that the operations on the database can begin.
`ddl.sql` will initialize the PSQL database `host_data` in addition to creating the tables to store hardware specifications as well as usage data.
`scripts/host_info.sh` script will be ran once to provide specifications about the server to the PSQL DB.
`scripts/host_usage.sh` is the automated script by crontab to be ran every minute to provide CPU usage data to the PSQL DB.
`sql/queries.sql` will provide the following SQL results: 
    1) Group hosts by CPU number and sort by their memory size in descending order(within each cpu_number group)
    2) Average used memory in percentage over 5 mins interval for each host

## Usage

## Improvement
