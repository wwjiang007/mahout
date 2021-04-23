#!/usr/bin/env bash

#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# A BIG Shoutout to the Bros and Bro-ettes at Apache Spark for this

## TODO post 14.1 fix this script ---- Idempotency is lost somewhere, and 
## this script will mess an entire IntelliJ project structure up !!######

set -ex

VALID_VERSIONS=( 2.10 2.11 2.12 )

usage() {
  echo "Usage: $(basename $0) [-h|--help] <version>
where :
  -h| --help Display this help text
  valid version values : ${VALID_VERSIONS[*]}
" 1>&2
  exit 1
}

if [[ ($# -ne 1) || ( $1 == "--help") ||  $1 == "-h" ]]; then
  usage
fi

TO_VERSION=$1

check_scala_version() {
  for i in ${VALID_VERSIONS[*]}; do [ $i = "$1" ] && return 0; done
  echo "Invalid Scala version: $1. Valid versions: ${VALID_VERSIONS[*]}" 1>&2
  exit 1
}

check_scala_version "$TO_VERSION"

if [[ $TO_VERSION != "2.10" && $TO_VERSION != "2.11" ]]; then
  FROM_VERSION="2.12"
elif [[ $TO_VERSION != "2.10" && $TO_VERSION !="2.12" ]]; then
  FROM_VERSION="2.11"
else [[ $TO_VERSION != "2.11" && $TO_VERSION != "2.12" ]];
  FROM_VERSION="2.10"
fi

sed_i() {
  sed -e "$1" "$2" > "$2.tmp" && mv "$2.tmp" "$2"
}

export -f sed_i

BASEDIR=$(dirname $0)/..
find "$BASEDIR" -name 'pom.xml' -not -path '*target*' -print \
  -exec bash -c "sed_i 's/\(artifactId.*\)_'$FROM_VERSION'/\1_'$TO_VERSION'/g' {}" \;

# Also update <scala.binary.version> in parent POM
# Match any scala binary version to ensure idempotency
sed_i '1,/<scala\.compat\.version>[0-9]*\.[0-9]*</s/<scala\.compat\.version>[0-9]*\.[0-9]*</<scala.compat.version>'$TO_VERSION'</' \
  "$BASEDIR/pom.xml"

#
# Mahout doesn't need this until we get our website acting right.
#
## Update source of scaladocs
#echo "$BASEDIR/docs/_plugins/copy_api_dirs.rb"
#sed_i 's/scala\-'$FROM_VERSION'/scala\-'$TO_VERSION'/' "$BASEDIR/docs/_plugins/copy_api_dirs.rb"
