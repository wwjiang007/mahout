#!/usr/bin/env bash
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements. See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License. You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

sudo: required

dist: trusty

cache:
  directories:
  - $HOME/.m2

install: true

language: java

branches:
  only:
   - master

env:
  global:
    - JAVA_OPTS=-Xmx3g
    - TEST_MODULES="core,engine"
    - STANDARD_BUILD_OPTS="-Dmaven.javadoc.skip=true -B -V"
    - PROFILES="" # "-Ptravis"
    - SPARK_1_6=http://d3kbcqa49mib13.cloudfront.net/spark-1.6.3-bin-hadoop2.6.tgz
    - SPARK_2_0=http://d3kbcqa49mib13.cloudfront.net/spark-2.0.2-bin-hadoop2.7.tgz
    - SPARK_2_1=http://d3kbcqa49mib13.cloudfront.net/spark-2.1.0-bin-hadoop2.7.tgz
    - SPARK_2_3=https://archive.apache.org/dist/spark/spark-2.3.0/spark-2.3.0-bin-hadoop2.7.tgz

# The file assumes a certain build order for the maven / nightly build deployments.
matrix:
  include:
    # Default Build: Spark 2.3.0, Scala 2.11
    - jdk: "openjdk8"
      env: PROFILES="${PROFILES}" SPARK_BIN=$SPARK_2_3 SCALA_VERSION="2.11"

    # Build Spark 1.6.3 , Scala 2.10
    #- jdk: "openjdk7"
    #  env: PROFILES="${PROFILES} -Pscala-2.10 -Pspark-1.6" SPARK_BIN=$SPARK_1_6 SCALA_VERSION="2.10"

    # Build Spark 2.0.2 , Scala 2.11 - replace -D... with profiles when available
    #- jdk: "openjdk7"
    #  env: PROFILES="${PROFILES} -Pspark-2.0 -Pscala-2.11" SPARK_BIN=$SPARK_2_0 SCALA_VERSION="2.11"

    # Build Spark 2.1.0 , Scala 2.11 - replace -D... with profiles when available
    #- jdk: "openjdk7"
    #  env: PROFILES="${PROFILES} -Pspark-2.1 -Pscala-2.11" SPARK_BIN=$SPARK_2_1 SCALA_VERSION="2.11"

    # Build Spark 1.6.3 , Scala 2.10, ViennaCL
    #- jdk: "openjdk7"
    #  env: PROFILES="${PROFILES} -Pscala-2.10 -Pviennacl" SPARK_BIN=$SPARK_1_6 SCALA_VERSION="2.10"

    # Build Spark 2.0.2 , Scala 2.11, ViennaCL - replace -D... with profiles when available
    #- jdk: "openjdk7"
    #  env: PROFILES="${PROFILES} -Pspark-2.0 -Pscala-2.11 -Pviennacl" SPARK_BIN=$SPARK_2_0 SCALA_VERSION="2.11"

    # Build Spark 2.1.0 , Scala 2.11, ViennaCL - replace -D... with profiles when available
    #- jdk: "openjdk7"
    #  env: PROFILES="${PROFILES} -Pspark-2.1 -Pscala-2.11 -Pviennacl" SPARK_BIN=$SPARK_2_1 SCALA_VERSION="2.11"

    # Build Spark 1.6.3 , Scala 2.10, ViennaCL-OMP
    #- jdk: "openjdk7"
    #  env: PROFILES="${PROFILES} -Pscala-2.10 -Pviennacl-omp" TEST_MODULES="${TEST_MODULES},viennacl-omp" SPARK_BIN=$SPARK_1_6 SCALA_VERSION="2.10"

    # Build Spark 2.0.2 , Scala 2.11, ViennaCL-OMP - replace -D... with profiles when available
    #- jdk: "openjdk7"
    #  env: PROFILES="${PROFILES} -Pspark-2.0 -Pscala-2.11 -Pviennacl-omp" TEST_MODULES="${TEST_MODULES},viennacl-omp" SPARK_BIN=$SPARK_2_0 SCALA_VERSION="2.11"

    # Build Spark 2.1.0 , Scala 2.11, ViennaCL-OMP - replace -D... with profiles when available
    #- jdk: "openjdk7"
    #  env: PROFILES="${PROFILES} -Pspark-2.1 -Pscala-2.11 -Pviennacl-omp" TEST_MODULES="${TEST_MODULES},viennacl-omp" SPARK_BIN=$SPARK_2_1 SCALA_VERSION="2.11"

git:
  depth: 10

#notifications:
#  slack: mahout:7vlbihiCBKuhEZK2610jkeeT

before_install:
# Install Maven 3.3.x+
  - wget https://archive.apache.org/dist/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.zip
  - unzip -qq apache-maven-3.3.9-bin.zip
  - export M2_HOME=$PWD/apache-maven-3.3.9
  - export PATH=$M2_HOME/bin:$PATH
  - export MAHOUT_HOME=$PWD
  - sudo apt-get -qq update
  # Install OpenCL Driver
  - sudo apt-get install ocl-icd-libopencl1
  # Install ViennaCL Source
  - wget https://github.com/viennacl/viennacl-dev/archive/release-1.7.1.zip
  - unzip -qq release-1.7.1.zip
  - sudo cp -r viennacl-dev-release-1.7.1/viennacl /usr/include/viennacl
  - sudo cp -r viennacl-dev-release-1.7.1/CL /usr/include/CL
  # Install SSH Host Client so Spark Pseudo-cluster can start w/out password
  - sudo apt-get install openssh-client
  - sudo apt-get install openssh-server
  - ssh-keygen -t rsa -P "" -f ~/.ssh/id_rsa
  - cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys


script:
  # - buildtools/change-scala-version.sh $SCALA_VERSION
  # Build Mahout
  # - mvn clean package $PROFILES $STANDARD_BUILD_OPTS -DskipTests -DskipCli
  - mvn clean package $STANDARD_BUILD_OPTS -DskipTests -DskipCli
  # Start Spark
  - echo $SPARK_BIN
  - wget $SPARK_BIN
  - tar -xzf *tgz
  - spark*/sbin/start-all.sh
  # Run Tests with Master at spark://localhost:7077
  - mvn test -pl $TEST_MODULES $PROFILES -Dtest.spark.master=spark://localhost:7077

