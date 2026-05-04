## Dream Commerce
A demo E-Commerce App.

### MYSQL DB Setup
To avoid DB mismatch and issues, you can use docker to pull a
mysql image and run an instance.

```
docker --version
docker pull mysql
docker images
docker run --name mysql -p 3308:3306 -e MYSQL_ROOT_PASSWORD=password -d mysql:latest
docker ps
docker ps -a
docker exec -it mysql mysql -u root -p # To access the db in your terminal
docker exec -it broker bash # To access the kafka instance
```

To test using curl
```
curl --json '{"username":"user","password":"password"}' http://localhost:8080/api/v1/login
```

### Kafka Setup
To run [Kafka](https://kafka.apache.org/quickstart/) locally, `docker-compose up` which already has the configuration of kafka to run.
<br>
Or, you can pull directly:
```
docker pull apache/kafka:4.2.0
docker run -p 9092:9092 apache/kafka:4.2.0
```
To access the kafka instance:
```
# To access the kafka instance
docker exec -it broker bash 

# To enter the kafka bin directory
cd /opt/kafka/bin 

# To list all the topics
./kafka-topics.sh --list --bootstrap-server localhost:9092

# To create a topic
./kafka-topics.sh --create --topic my-topic --bootstrap-server localhost:9092

# To describe a topic
./kafka-topics.sh --describe --topic my-topic --bootstrap-server localhost:9092

# To produce a message / Publish a message to a topic
./kafka-console-producer.sh --topic my-topic --bootstrap-server localhost:9092

# To consume a message / Subscribe to a topic
./kafka-console-consumer.sh --topic my-topic --bootstrap-server localhost:9092
./kafka-console-consumer.sh --topic my-topic --from-beginning --bootstrap-server localhost:9092

```
