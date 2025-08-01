@echo off
setlocal EnableDelayedExpansion

:: --------------------------------------------
:: SecureProps Tool Installer (Windows - ASCII)
:: --------------------------------------------

:: Define default installation directory
set "defaultDir=C:\Tools\secureprops"

:: Store the path of the script directory
set "scriptDir=%~dp0"

echo.
echo SecureProps Tool Installer
echo --------------------------
echo.

:: Ask for installation path
set /p installDir=Enter installation folder (default: %defaultDir%):
if "%installDir%"=="" set "installDir=%defaultDir%"

:: Remove trailing backslash if exists
if "%installDir:~-1%"=="\" set "installDir=%installDir:~0,-1%"

:: Create the folder if it doesn't exist
if not exist "%installDir%" (
    echo Creating folder "%installDir%"...
    mkdir "%installDir%"
)

:: Copy files from script directory to install directory
echo Copying SecureProps files...
xcopy "%scriptDir%*" "%installDir%\" /E /I /Y >nul

:: Set securepropsHome variable permanently
echo Setting environment variable securepropsHome...
setx securepropsHome "%installDir%"

REM :: Check if installDir is already in PATH
REM echo Checking if install folder is in PATH...
REM echo %PATH% | find /I "%installDir%" >nul
REM if errorlevel 1 (
REM     echo Adding "%installDir%" to PATH...
REM     setx PATH "%PATH%;%installDir%"
REM ) else (
REM     echo Folder is already in PATH.
REM )


echo.
echo Adding installDir to PATH...

powershell -NoProfile -Command " $installDir = \"$env:installDir\"; $oldPath = [Environment]::GetEnvironmentVariable(\"PATH\", \"User\"); if (-not ($oldPath.Split(';') -contains $installDir)) { Write-Host 'Appending to PATH...'; [Environment]::SetEnvironmentVariable(\"PATH\", $oldPath + ';' + $installDir, \"User\") } else { Write-Host 'Install folder already in PATH.' } "


:: Final instructions
echo.
echo Installation complete.
echo.
echo You may need to restart Command Prompt for changes to take effect.
echo.
echo To verify:
echo     echo %%securepropsHome%%
echo.
echo "securepropsHome: %securepropsHome%"
echo "Path: %Path%"
echo.
echo Set encryption keys (each must be exactly 16 characters):
echo    setx keyForLocal dummyKeyLOCAL12
echo    setx keyForDev   devSecretKey123
echo    setx keyForProd  prodSecretKey12
echo.
echo If you prefer, you can set them manually via Environment Variables UI...
pause
start rundll32 sysdm.cpl,EditEnvironmentVariables
echo.
echo To launch the GUI:
echo     secure-properties-ui.bat
echo.
pause
