lib=true
arg=true
poly=false
cf_version="3.42.0"
ucrt_version="0.3-SNAPSHOT"
rm -rvf target > /dev/null
CF_VERSION=$cf_version UCRT_VERSION=$ucrt_version ANNOTATOR_POLY=$poly ANNOTATOR_LIBRARY=$lib ANNOTATOR_TYPE_ARG=$arg mvn compile