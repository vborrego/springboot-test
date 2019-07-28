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

# Kubernetes - run a container
https://www.katacoda.com/courses/kubernetes/playground
```bash
# k8s cluster info
kubectl cluster-info

# create deployment from docker image
kubectl create deployment springboot-test --image=vbodocker/springboot-test:latest

# exposes port 8000 poiting to port 8080
kubectl expose deployment springboot-test --port=8000 --target-port=8080 --type=NodePort

# invoke endpoint
kubectl get services
#NAME              TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE
#kubernetes        ClusterIP   10.96.0.1       <none>        443/TCP          19m
#springboot-test   NodePort    10.102.123.27   <none>        8000:32696/TCP   8s

IP_SPRINGBOOT=$(kubectl get services | grep springboot | awk '//{print $3}')
curl http://$IP_SPRINGBOOT:8000/dummy
# [{"fieldA":"AAA","fieldB":"CCC"},{"fieldA":"AAA2","fieldB":"CCC2"}]

# delete service
kubectl delete services springboot-test

# get services
kubectl get services -o wide
# NAME              TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE       SELECTOR
# kubernetes        ClusterIP   10.96.0.1       <none>        443/TCP          25m       <none>
# springboot-test   NodePort    10.102.123.27   <none>        8000:32696/TCP   6m        app=springboot-test

# get nodes
kubectl get  nodes -o wide
# NAME      STATUS    ROLES     AGE       VERSION   INTERNAL-IP   EXTERNAL-IP   OS-IMAGE             KERNEL-VERSION     CONTAINER-RUNTIME
# master    Ready     master    25m       v1.11.3   172.17.0.11   <none>        Ubuntu 16.04.2 LTS   4.4.0-62-generic   docker://1.13.1
# node01    Ready     <none>    25m       v1.11.3   172.17.0.30   <none>        Ubuntu 16.04.2 LTS   4.4.0-62-generic   docker://1.13.1

# get pods
kubectl get pods -o wide
# NAME                              READY     STATUS    RESTARTS   AGE       IP          NODE      NOMINATED NODE
# springboot-test-5b5fb44bc-gdxgk   1/1       Running   0          13m       10.40.0.1   node01 

# invoke endpoint directly on the pod (10.40.0.1)
curl http://10.40.0.1:8080/dummy
#[{"fieldA":"AAA","fieldB":"CCC"},{"fieldA":"AAA2","fieldB":"CCC2"}]

# in a k8s node find docker container
docker ps | grep vbo

# scale pods, change spec.replicas to 3
kubectl edit deployment springboot-test

# show new pods
kubectl get pods -o wide
# NAME                              READY     STATUS    RESTARTS   AGE       IP          NODENOMINATED NODE
# springboot-test-5b5fb44bc-gdxgk   1/1       Running   0          21m       10.40.0.1   node01 <none>
# springboot-test-5b5fb44bc-jd4lw   1/1       Running   0          27s       10.40.0.2   node01 <none>
# springboot-test-5b5fb44bc-tk8b2   1/1       Running   0          27s       10.40.0.3   node01 <none>

# get data directly from new pod
curl http://10.40.0.3:8080/dummy

# get data directly from service
curl http://10.102.123.27:8000/dummy

```




