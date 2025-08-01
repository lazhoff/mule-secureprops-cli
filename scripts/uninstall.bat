@echo off
setlocal EnableDelayedExpansion

:: ========================================
::         SecureProps Uninstaller
:: ========================================

set "defaultDir=C:\Tools\secureprops"

echo.
echo ========================================
echo      SecureProps Tool Uninstaller
echo ========================================
echo.

:: Ask install location
set /p installDir=Enter installation folder to remove (default: %defaultDir%):
if "%installDir%"=="" set "installDir=%defaultDir%"

echo.
echo Selected folder: "%installDir%"
echo WARNING: This will delete all files in the folder.

choice /M "Are you sure you want to uninstall?"
if errorlevel 2 (
    echo.
    echo Uninstall canceled.
    goto end
)

:: Attempt folder deletion
if exist "%installDir%" (
    echo.
    echo Attempting to remove folder: %installDir%
    rmdir /S /Q "%installDir%" 2>nul

    if exist "%installDir%" (
        echo.
        echo [!] Could not delete the folder. It might be in use.
        echo     Try closing open files or Command Prompt in this directory.
    ) else (
        echo.
        echo Folder successfully removed.
    )
) else (
    echo.
    echo [!] Folder not found: %installDir%
)

:: Remove environment variable
echo.
echo Removing environment variable: securepropsHome
reg delete "HKCU\Environment" /F /V securepropsHome >nul 2>&1
if errorlevel 1 (
    echo [!] securepropsHome was not set.
) else (
    echo [+] securepropsHome removed.
)

:: Info about PATH
echo.
echo NOTE:
echo If you added this folder to your PATH manually,
echo you should open Environment Variables and remove it.
echo.
echo Opening Environment Variables UI...
start rundll32 sysdm.cpl,EditEnvironmentVariables

:end
echo.
echo ========================
echo     Uninstall complete
echo ========================
echo.
pause
