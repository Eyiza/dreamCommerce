### Dream Commerce
A demo E-Commerce App.

### MYSQL DB Setup
To avoid DB mismatch and issues, you can use docker to pull a
mysql image and run an instance.

```
docker --version
docker pull mysql
docker images
docker run --name mysql -p 3308:3306 -e MYSQL_ROOT_PASSWORD=password -d mysql::tag
docker ps
docker ps -a
```

