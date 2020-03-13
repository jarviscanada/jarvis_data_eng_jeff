#! /bin/bash
if [ $# != 2 ];
then
	echo $#
	echo "Wrong number of arugments"
	exit 1
fi
if [ $1 == "start" ]; then 
	systemctl status docker  || systemctl start docker
	linecount=`docker ps -af name=jrvs-psql | wc -l`
	if [ "$linecount" -gt 2 ];
	then
		docker run --name jrvs-psql -e POSTGRES_PASSWORD=$PGPASSWORD -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
	fi
	docker container start jrvs-psql
	docker volume inspect pgdata
	if [ "$?" -ne  0 ];
	then
		docker volume create pgdata
	fi
	psql -h localhost -U postgres -W
	exit 0
fi
if [ $1 == "stop" ];
then	
	docker container stop jrvs-psql
	systemctl stop docker
	exit 0
fi
exit 1
