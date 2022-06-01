kubectl delete -f ./kubernetes/download-microservice-deployment.yml
kubectl delete -f ./kubernetes/postgres-deployment.yml
kubectl delete -f ./kubernetes/configmap.yml
kubectl delete -f ./kubernetes/secret.yml

./gradlew build
docker build -f src/main/docker/Dockerfile.jvm -t team-f-download-microservice-jvm .

kubectl apply -f ./kubernetes/configmap.yml
kubectl apply -f ./kubernetes/secret.yml
kubectl apply -f ./kubernetes/postgres-deployment.yml
kubectl apply -f ./kubernetes/download-microservice-deployment.yml