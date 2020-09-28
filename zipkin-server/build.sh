version=$1

echo "=======zipkin-server======="

mvn clean install -Dmaven.test.skip=true

docker build -f docker/dev.Dockerfile -t 192.168.10.124:8889/public/zipkin-server:"${version}" .
docker push 192.168.10.124:8889/public/zipkin-server:"${version}"
docker rmi -f 192.168.10.124:8889/public/zipkin-server:"${version}"

#docker run -d -p 9441:8080 --name zipkin-server --hostname zipkin-server --network cloud-demo 192.168.10.124:8889/public/zipkin-server:latest