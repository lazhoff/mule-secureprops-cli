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

# Run the CLI UI
muleSecurePropsCli="$securepropsHome/mule-secureprops-cli.jar"

java -jar $muleSecurePropsCli --ui
