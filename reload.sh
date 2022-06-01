./gradlew build
docker build -f src/main/docker/Dockerfile.jvm -t team-f-download-microservice-jvm .
kubectl rollout restart deployment/download-microservice