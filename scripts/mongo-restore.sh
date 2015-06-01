rm -rf $TMP_DIR && mkdir $TMP_DIR
if [[ $1 =~ \.tar$ ]];
then
	#FILENAME=$(echo $1 | sed 's/.*\///')
	FILENAME=$2"/"
	mkdir $TMP_DIR
	echo "Data will be extracted into :"$TMP_DIR
	tar -C $TMP_DIR -xvf $1
else
	FILENAME=$(echo $1 | sed 's/.*\///')
	cp $1 $TMP_DIR$FILENAME
fi

docker run -it --rm --link mongo:mongo -v $TMP_DIR:/tmp mongo bash -c 'mongorestore --drop -v --host $MONGO_PORT_27017_TCP_ADDR:$MONGO_PORT_27017_TCP_PORT --db '$2' /tmp/'$FILENAME
rm -rf $TMP_DIR
