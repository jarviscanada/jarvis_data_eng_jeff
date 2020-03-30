
CREATE DATABASE IF NOT EXISTS host_agent;

/connect host_agent;

#create table for host info
CREATE TABLE IF NOT EXISTS PUBLIC.host_info 
  ( 
     id               SERIAL PRIMARY KEY NOT NULL, 
     hostname         VARCHAR NOT NULL UNIQUE,
     cpu_number		  SMALLINT NOT NULL,
     cpu_architecture VARCHAR NOT NULL,
     cpu_model		  VARCHAR NOT NULL,
     cpu_mhz          FLOAT NOT NULL,
     L2_cache		  INT NOT NULL,
     total_mem		  INT NOT NULL,
     timestamp        TIMESTAMP NOT NULL,
  );

  CREATE TABLE IF NOT EXISTS PUBLIC.host_usage 
    ( 
       "timestamp"    TIMESTAMP NOT NULL, 
       host_id        SERIAL refrences PUBLIC.host_info(id) NOT NULL,
       memory_free    REAL NOT NULL,
       cpu_idle		  FLOAT NOT NULL,
       cpu_kernel     FLOAT NOT NULL,
       disk_io        INT NOT NULL,
       disk_available FLOAT NOT NULL,
    );
