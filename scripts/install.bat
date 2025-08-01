@echo off
setlocal EnableDelayedExpansion

echo.
echo SecureProps Tool Installer (Windows)
echo ------------------------------------

set /p installDir=Enter installation folder (e.g. C:\Mule\secureprops):

if not exist "%installDir%" (
    echo Creating folder...
    mkdir "%installDir%"
)

echo Copying files to "%installDir%"...
xcopy * "%installDir%" /E /Y >nul

echo.
echo Installation complete.
echo.
echo Now add the following environment variable:
echo   Name:  securepropsHome
echo   Value: %installDir%
echo.
echo You can do this via:
echo   - Control Panel -> System -> Advanced -> Environment Variables
echo   - Or via command:
echo       setx securepropsHome "%installDir%"
echo.
echo Also set your encryption keys (each must be exactly 16 characters):
echo       setx keyForLocal dummyKeyLOCAL12
echo       setx keyForDev   devSecretKey123
echo       setx keyForProd  prodSecretKey12
echo.
echo To launch the Graphical User Interface:
echo   %installDir%\secure-properties-ui.bat
echo.
pause
