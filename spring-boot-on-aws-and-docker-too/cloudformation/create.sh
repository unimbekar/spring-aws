#!/usr/bin/env bash
export AWS_PAGER=""

BUCKET_NAME=$1

aws cloudformation create-stack \
  --stack-name stratospheric-demo-infrastructure \
  --template-body file://application-infrastructure.yml \
  --profile default \
  --parameters \
    ParameterKey=BucketName,ParameterValue=$BUCKET_NAME

aws cloudformation wait stack-create-complete \
   --profile default \
   --stack-name stratospheric-demo-infrastructure
