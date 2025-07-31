@echo off
setlocal

REM Check if securepropsHome is set
if "%securepropsHome%"=="" (
    echo [ERROR] securepropsHome is not set.
    echo.
    echo To fix:
    echo   Windows: Go to Environment Variables and add securepropsHome, e.g. C:\Mule\secureprops
    echo.
    pause
    exit /b 1
)

set "muleSecurePropsCli=%securepropsHome%\mule-secureprops-cli.jar"

java -jar "%muleSecurePropsCli%" --ui


echo
echo This window can be closed now

REM wait 15 seconds and then close the console window
timeout /t 15 >nul




 







