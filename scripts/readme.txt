SecureProps Tool - CLI & UI for MuleSoft Secure Properties
==========================================================

ABOUT
-----
This tool provides a CLI and GUI to encrypt/decrypt:
- Java `.properties` files
- Postman collections (`.postman_collection.json`)
- Environment-specific configurations

It is designed to work with MuleSoft Secure Properties using pre-configured encryption keys.

REQUIREMENTS
------------
- Java 17 or higher
- Predefined environment variables:
    securepropsHome     = the path to this tool's folder
    keyForLocal         = 16-char encryption key for local
    keyForDev           = 16-char encryption key for dev
    keyForProd          = 16-char encryption key for prod

Example keys:
    export securepropsHome=/opt/secureprops
    export keyForLocal=dummyKeyLOCAL12
    export keyForDev=devSecretKey123
    export keyForProd=prodSecretKey12

SETUP
-----
Unzip the archive to your preferred location:

    Windows:   C:\Mule\secureprops
    Linux/Mac: /opt/secureprops

Set environment variables:

    Windows:
        setx securepropsHome "C:\Mule\secureprops"
        setx keyForLocal dummyKeyLOCAL12
        setx keyForDev   devSecretKey123
        setx keyForProd  prodSecretKey12

    Linux/macOS:
        export securepropsHome=/opt/secureprops
        export keyForLocal=dummyKeyLOCAL12
        export keyForDev=devSecretKey123
        export keyForProd=prodSecretKey12

USAGE
-----
Run UI mode:

    Windows:
        secure-properties-ui.bat

    Linux/macOS:
        ./secure-properties-ui.sh

Run CLI mode:

    java -jar mule-secureprops-cli.jar [options]

    Examples:
        Encrypt folder:
            java -jar mule-secureprops-cli.jar encrypt-folder path/to/configs
        Decrypt file:
            java -jar mule-secureprops-cli.jar decrypt path/to/file.properties

SCRIPTS INCLUDED
----------------
- secure-properties-ui.bat / .sh       - Launches the UI
- encrypt-props.bat / .sh              - Encrypts properties
- decrypt-props.bat / .sh              - Decrypts properties
- encrypt-postman.bat / .sh            - Encrypts Postman collections
- decrypt-postman.bat / .sh            - Decrypts Postman collections
- install.bat / .sh                    - Optional interactive setup script

CONTACT & SOURCE
----------------
For updates, source code, and issue tracking:
https://github.com/your-org/mule-secureprops

