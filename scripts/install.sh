#!/bin/bash

# --------------------------------------------
# SecureProps Tool Installer (Linux/macOS)
# --------------------------------------------

default_dir="$HOME/secureprops"
script_dir="$(cd "$(dirname "$0")" && pwd)"

echo ""
echo "SecureProps Tool Installer"
echo "--------------------------"
echo ""

# Prompt for install directory
read -p "Enter installation folder (default: $default_dir): " install_dir
if [ -z "$install_dir" ]; then
  install_dir="$default_dir"
fi

# Remove trailing slash
install_dir="${install_dir%/}"

# Create install folder
echo "Creating folder: $install_dir"
mkdir -p "$install_dir"

# Copy files
echo "Copying SecureProps files..."
cp -r "$script_dir/"* "$install_dir/"
find "$install_dir" -type f -name '*.bat' -delete

# Set securepropsHome in shell config
echo ""
echo "To persist environment variable, add this to your ~/.bashrc or ~/.zshrc:"
echo "  export securepropsHome=\"$install_dir\""
echo ""

# Add to PATH
echo "To add SecureProps to PATH, add this line to ~/.bashrc or ~/.zshrc:"
echo "  export PATH=\"\$PATH:$install_dir\""
echo ""

# Encryption key instructions
echo "Set encryption keys (must be exactly 16 characters):"
echo "  export keyForLocal=dummyKeyLOCAL12"
echo "  export keyForDev=devSecretKey123"
echo "  export keyForProd=prodSecretKey12"
echo ""

# Launch instruction
echo "To launch the GUI:"
echo "  $install_dir/secure-properties-ui.sh"
echo ""

# Optional: Prompt to add to .bashrc now (commented out)
# read -p "Do you want to add export lines to ~/.bashrc now? (y/n): " add_now
# if [[ "$add_now" =~ ^[Yy]$ ]]; then
#   echo "export securepropsHome=\"$install_dir\"" >> ~/.bashrc
#   echo "export PATH=\"\$PATH:$install_dir\"" >> ~/.bashrc
#   echo "✔️ Added to ~/.bashrc"
# fi
