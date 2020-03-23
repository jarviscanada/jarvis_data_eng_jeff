#! /bin/bash
instruction= $1
password=$2;
#make sure the correct number of arguments are given
if [ $# -lt 1 ] || [ $# -gt 2 ];
then
	echo "Wrong number of arugments"
	exit 1
fi

# Check that the command is to start or stop docker
if [ $instruction != "start" ] || [ $instruction != "stop" ]
then
	echo "Incorrect command, use ./psql_docker.sh start | stop [password]"
	exit 1
fi

# Handle starting docker logic
if [ $instruction == "start" ];
then
	# If the password is not provided set the password to the default
	if [ $# -eq 1 ];
	then
		export PGPASSWORD='password'
	else
		export PGPASSWORD=$password
	fi
	# start docker or give the status if docker is already started
	systemctl status docker  || systemctl start docker

	# start the container for the first time if needed
	linecount=`docker ps -af name=jrvs-psql | wc -l`
	if [ "$linecount" -lt 2 ];
	then
		docker run --name jrvs-psql -e POSTGRES_PASSWORD=$PGPASSWORD -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
	fi

	docker container start jrvs-psql

	#check if the volume exists
	docker volume inspect pgdata
	if [ "$?" -ne  0 ];
	then
		docker volume create pgdata
	fi
	exit 0
fi

if [ $instruction == "stop" ];
then
	# stop and exit from docker
	docker container stop jrvs-psql
	systemctl stop docker
	exit 0
fi

exit 0
