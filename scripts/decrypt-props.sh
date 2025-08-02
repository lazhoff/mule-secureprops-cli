#!/bin/bash

# Check if securepropsHome is set
if [ -z "$securepropsHome" ]; then
  echo "[ERROR] securepropsHome is not set."
  echo
  echo "To fix:"
  echo "  export securepropsHome=/opt/secureprops"
  echo
  exit 1
fi

# Check if encryption keys are set
missing=0

if [ -z "$keyForLocal" ]; then
  echo "[ERROR] keyForLocal is not set."
  missing=1
fi

if [ -z "$keyForDev" ]; then
  echo "[ERROR] keyForDev is not set."
  missing=1
fi

if [ -z "$keyForProd" ]; then
  echo "[ERROR] keyForProd is not set."
  missing=1
fi

if [ "$missing" = "1" ]; then
  echo
  echo "To fix:"
  echo "  export keyForLocal=dummyPwd123LOCAL"
  echo "  export keyForDev=dummyPwd123DEV"
  echo "  export keyForProd=dummyPwd123PROD"
  echo
  exit 1
fi


# Run the CLI UI
muleSecurePropsCli="$securepropsHome/mule-secureprops-cli.jar"

envKeyMapping="(secure-config-local.yaml):(${keyForLocal}),(secure-config-(dev|uat).yaml):(${keyForDev}),(secure-config-prod.yaml):(${keyForProd})"



# Use current directory (.) if no argument is passed
if [ -z "$1" ]; then
    directory="."
else
    directory="$1"
fi

java -jar $muleSecurePropsCli decrypt file $directory AES CBC true --envKeyMapping=$envKeyMapping --tmp=.




