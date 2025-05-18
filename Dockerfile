FROM java:21

RUN mkdir /app

COPY ./build/distributions/ug-task-0.1.0-SNAPSHOT.tar /tmp

RUN tar -C /tmp -xzvf /tmp/ug-task-0.1.0-SNAPSHOT.tar
RUN mv -v /tmp/ug-task-0.1.0-SNAPSHOT/.[!.]* /app
RUN rm /tmp/ug-task-0.1.0-SNAPSHOT.tar
RUN rm -rf /tmp/ug-task-0.1.0-SNAPSHOT

WORKDIR /app
VOLUME . /app

EXPOSE 8080

ENTRYPOINT [ "/app/bin/ug-task" ]
