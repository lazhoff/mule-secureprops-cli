# Functional Requirements

## Core Features

- Encrypt/decrypt `.properties` files and Postman `.json` collections
- Support both CLI and UI usage modes
- Accept single files or entire folders as input
- Environment-based key selection (e.g., local/dev/prod) via environment variables
- Support for Java 17+ runtime environment

## Input/Output

- Input files must be either `.properties` or `.json` (Postman format)
- Output is generated in the same folder with `-encrypted` / `-decrypted` suffix
- Log files are stored per operation with timestamp
- Result reports in structured text format (optional, for automation/debugging)

## UI Mode

- Cross-platform support: Windows (.bat) and Linux/macOS (.sh)
- Launchable via `secure-properties-ui.[bat|sh]`
- Requires `securepropsHome` environment variable pointing to installation directory

## CLI Mode

- Main entry point: `java -jar mule-secureprops-cli.jar`
- Supports arguments for:
    - `--encrypt` / `--decrypt`
    - `--env` (e.g., `local`, `dev`, `prod`)
    - `--path` (file or folder)

## Configuration

- Uses `securepropsHome` to resolve default paths and script execution
- Keys for environments set via:
    - `keyForLocal`
    - `keyForDev`
    - `keyForProd`
- Keys must be 16 characters long (AES-128 requirement)

## Packaging and Installation

- Delivered as ZIP archive containing:
    - `mule-secureprops-cli.jar`
    - Platform-specific scripts (`.bat`, `.sh`)
    - `VERSION` file
    - `README.txt`
    - `thirdparty/` folder (was `lib/`)
- Install script provided (`install.bat` / `install.sh`) to help users:
    - Set `securepropsHome`
    - Provide encryption keys
    - Update system environment variables

## Limitations

- No support for key rotation
- Assumes trusted local runtime (no remote execution context)

## Security

- No telemetry or network connections
- Encrypted values use secure-properties-tool from MuleSoft (included)
- Users are responsible for key management and secure storage
