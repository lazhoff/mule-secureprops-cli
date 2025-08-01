#!/bin/bash

echo ""
echo "SecureProps Tool Installer (Linux/macOS)"
echo "----------------------------------------"

read -p "Enter installation folder (e.g. /opt/secureprops): " install_dir

mkdir -p "$install_dir"
cp -r . "$install_dir"

echo ""
echo "Installation complete."
echo ""
echo "Now add this to your shell config (e.g. ~/.bashrc or ~/.zshrc):"
echo "  export securepropsHome=\"$install_dir\""
echo ""
echo "Also set your encryption keys (each must be exactly 16 characters):"
echo "  export keyForLocal=dummyKeyLOCAL12"
echo "  export keyForDev=devSecretKey123"
echo "  export keyForProd=prodSecretKey12"
echo ""
echo "To launch the Graphical User Interface:"
echo "  $install_dir/secure-properties-ui.sh"
