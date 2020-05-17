# Avro Demo Repository
This is a demo repo for Avro files in Java

[![Build Status](https://travis-ci.org/rishuatgithub/avrodemorepo.svg?branch=master)](https://travis-ci.org/rishuatgithub/avrodemorepo)

- Author: Rishu Shrivastava
- Email : rishu.shrivastava@gmail.com


### Pre-requisite

- Need a Google Cloud Pub/Sub Topic 
- Export your google application credentials to the service accounts.

  `export GOOGLE_APPLICATION_CREDENTIALS = <path to your serviceaccount.json>`


### Build

`mvn clean install`


### Application

`java -jar avrodemorepo-*.jar demo.pkg.avro.build.AvroApplication`

AvroApplication is the master class.

