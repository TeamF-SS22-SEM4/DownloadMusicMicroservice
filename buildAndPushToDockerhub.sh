./gradlew build
docker build -f src/main/docker/Dockerfile.jvm -t smighty/download-service .
docker push smighty/download-service:latest