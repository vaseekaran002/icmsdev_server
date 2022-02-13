#react build
mvn clean package

#login docker
docker login https://index.docker.io/v1/ --username eswarpalani --password 1cc7176d-13a8-4d3e-a144-85afb866959c

#pull latest image
docker pull eswarpalani/icms_service:latest

arhieve_tag="$(date +%Y%m%d_%H%M%S)"

#rename image
docker tag eswarpalani/icms_service:latest eswarpalani/icms_service:$arhieve_tag

#archive old image
docker push eswarpalani/icms_service:$arhieve_tag

#build new image
docker build -t eswarpalani/icms_service:latest .

#push new image
docker push eswarpalani/icms_service:latest

#docker logout
docker logout