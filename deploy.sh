#!/bin/bash

# requires AWS CLI to be set up
# and keys to be set accordingly

lein build
aws s3 sync resources/public/ s3://envoys-frontend

