version=$1

echo "=======server-demo1======="

mvn clean install -Dmaven.test.skip=true

docker build -f docker/dev.Dockerfile -t 192.168.10.124:8889/public/server-demo1:"${version}" .
docker push 192.168.10.124:8889/public/server-demo1:"${version}"
docker rmi -f 192.168.10.124:8889/public/server-demo1:"${version}"

#docker run -d -p 9551:8080 --name server-demo1 --hostname server-demo1 --network cloud-demo 192.168.10.124:8889/public/server-demo1:latest