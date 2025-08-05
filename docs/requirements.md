# Requirements

## Features
- Encrypt or decrypt `.yaml` and `.properties` files in value mode, or process any file as a whole
- Works via CLI or cross-platform UI
- Processes folders recursively; single-file paths are not supported
- Selects encryption key through environment variables (`keyForLocal`, `keyForDev`, `keyForProd`)
- Requires Java 17+

## Input / Output
- `.yaml` and `.properties` files support value mode (`file`) or whole-file mode (`file-level`)
- Other file extensions are processed only in `file-level` mode
- Results are written in the same folder with `-encrypted`/`-decrypted` suffix
- Each run produces a timestamped log
- Optional report file for automation

## CLI
- Run with `java -jar mule-secureprops-cli.jar <encrypt|decrypt> <file|file-level> <folderPath> <algorithm> <mode> <useRandomIV>`
- Map environment patterns to keys via `--envKeyMapping="(pattern):(key)"`
- Optional flags: `--dryRun`, `--tmp=TempFolder`, `--noBackup`
-- Uses `securepropsHome` to locate defaults

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

