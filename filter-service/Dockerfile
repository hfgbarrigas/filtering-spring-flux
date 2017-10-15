# get minimal OS with oracle jdk8 installed
FROM frolvlad/alpine-oraclejdk8:slim

# copy file to the working directory of container
ADD deploy/scripts/run.sh run.sh

# Create app directory on container
RUN mkdir -p /usr/opt/service

# Bundle app source
COPY target/filter-service*.jar /usr/opt/service/filtering.jar

# make port 8080 available
EXPOSE 8080

#execute run script
RUN chmod 700 run.sh
CMD ["./run.sh"]
