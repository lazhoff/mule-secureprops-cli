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

envKeyMapping="(local.postman_environment.json):($keyForLocal),((dev|uat).postman_environment.json):($keyForDev),(prod.postman_environment.json):($keyForProd),(.*.postman_collection.json):($keyForDev)"



# Use current directory (.) if no argument is passed
if [ -z "$1" ]; then
    directory="."
else
    directory="$1"
fi


java -jar $muleSecurePropsCli encrypt file-level $directory AES CBC false  --envKeyMapping=$envKeyMapping  --tmp=.
