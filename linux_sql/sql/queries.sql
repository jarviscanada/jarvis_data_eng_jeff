SELECT
	cpu_number,
	id as host_id,
	total_memory
FROM host_info
GROUP BY cpu_number
ORDER BY total_memory DESC;


SELECT 
  host_usage.host_id, 
  host_info.host_name, 
  TIMESTAMP WITHOUT TIME ZONE 'epoch' + INTERVAL '1 second' * round(
    extract(
      'epoch' 
      from 
        timestamp
    ) / 300
  ) * 300 as timestamp, 
  (
    ROUND(
      (
        host_info.total_mem :: FLOAT - (
          avg(host_usage.memory_free):: FLOAT * 1000
        ) / host_info.total_mem :: FLOAT
      ), 
      2
    ) * 100
  ) AS avg_used_mem_percentage 
FROM 
  host_usage 
  INNER JOIN host_info 
WHERE 
  host_usage.host_id = host_info.id 
ORDER BY 
  host_usage.host_id 
GROUP BY 
  round(
    extract(
      'epoch' 
      from 
        timestamp
    ) / 300
  );
