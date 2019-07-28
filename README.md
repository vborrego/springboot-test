# springboot-test
Test web app using springboot. Requires Java 8 and maven.

## Build
```
mvn clean install
```

## Execute
```
java -Dfilelog=/tmp/out.log -jar target/test-spring-boot-0.1.0.jar
```

# Run docker image built from hub.docker.com registry 
```bash 
docker pull vbodocker/springboot-test # pull image
docker run -p 8080:8080 --name springboot-test -d vbodocker/springboot-test # run container from image
curl http://localhost:8080/dummy # invoke endpoint in container
curl http://localhost:8080/greeting?name=dfg # invoke endpoint in container
docker exec -it d4b5fe209467 sh # use shell inside docker container
```