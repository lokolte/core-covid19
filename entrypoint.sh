#!/bin/bash
export JAVA_OPTS="-server \
          -Xms512m \
          -XX:MaxGCPauseMillis=500 \
          -XX:+UseStringDeduplication \
          -Xmx3072m \
          -XX:+UseG1GC \
          -XX:ConcGCThreads=4 \
          -XX:ParallelGCThreads=4 \
          -Dpidfile.path=/dev/null"
          
java $JAVA_OPTS -jar /core/app.jar