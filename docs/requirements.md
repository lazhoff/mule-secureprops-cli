# Requirements

## Features
- Encrypt or decrypt `.properties` files and Postman `.json` collections
- Works via CLI or cross-platform UI
- Accepts single files or entire folders
- Selects encryption key through environment variables (`keyForLocal`, `keyForDev`, `keyForProd`)
- Requires Java 17+

## Input / Output
- Only `.properties` and `.json` files are processed
- Results are written in the same folder with `-encrypted`/`-decrypted` suffix
- Each run produces a timestamped log
- Optional report file for automation

## CLI
- Run with `java -jar mule-secureprops-cli.jar`
- Options: `--encrypt` or `--decrypt`, `--env`, `--path`
- Uses `securepropsHome` to locate defaults

## UI
- Launch `secure-properties-ui.bat` (Windows) or `.sh` (Linux/macOS)
- `securepropsHome` must point to the tool's folder

## Packaging
- Distributed as a ZIP containing the jar, scripts, `vendor/`, `VERSION`, and README
- Install scripts help set `securepropsHome` and keys

## Limitations
- No key rotation
- Assumes trusted local runtime

## Security
- Bundled MuleSoft secure-properties-tool handles encryption
- Users are responsible for key storage
