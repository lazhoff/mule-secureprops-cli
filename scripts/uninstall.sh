#!/bin/bash

echo ""
echo "========================================"
echo "       SecureProps Tool Uninstaller     "
echo "========================================"
echo ""

# Default install directory
default_dir="/opt/secureprops"

# Prompt user for install dir
read -p "Enter installation folder to remove [default: $default_dir]: " install_dir
install_dir=${install_dir:-$default_dir}

echo ""
echo "Selected folder: $install_dir"
read -p "WARNING: This will delete all files in that folder. Proceed? (y/n): " confirm
if [[ "$confirm" != "y" && "$confirm" != "Y" ]]; then
    echo "Uninstall cancelled."
    exit 0
fi

# Try to delete folder
if [ -d "$install_dir" ]; then
    echo ""
    echo "Attempting to remove folder: $install_dir"
    rm -rf "$install_dir" 2>/dev/null

    if [ -d "$install_dir" ]; then
        echo "[!] Could not delete the folder. It may be in use or require elevated permissions."
        echo "    Try closing any open terminals or files from that folder."
    else
        echo "[+] Folder successfully removed."
    fi
else
    echo "[!] Folder not found: $install_dir"
fi

# Remove securepropsHome from shell config
echo ""
echo "Searching for 'securepropsHome' in ~/.bashrc and ~/.zshrc..."

for file in ~/.bashrc ~/.zshrc; do
    if [ -f "$file" ]; then
        if grep -q "securepropsHome" "$file"; then
            echo "  -> Removing from $file"
            sed -i.bak '/securepropsHome/d' "$file"
        fi
    fi
done

# Suggest PATH cleanup
echo ""
echo "NOTE:"
echo "If you manually added SecureProps to your PATH, consider removing it:"
echo "  - Check your ~/.bashrc or ~/.zshrc"
echo "  - Remove any 'export PATH=...' lines referencing secureprops"
echo ""
echo "Uninstallation complete."
echo ""
