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

Specifically, `host_info` will record the following information
* `id` a unique self-incrementing id that will help keep track of the servers as well as serve as the primary key for the table
* `hostname` the fully qualified hostname of the server, assumed to be unique amonst the different servers.
* `cpu_number` the amount of cpu cores that the machine has.
* `cpu_architecture` the CPU's architecture.
* `cpu_model` the model of the CPU.
* `cpu_mhz` measures the CPU speed
* `L2_cache` how much space (in kB) the L2 cache has.
* `total_memory` the total amount of memory (in kB) the CPU has.
* `timestamp` when was the information recorded, measured in UTC time.
## Usage
 
## Improvement
