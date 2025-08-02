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

REM Check if encryption keys are set
set "missing=0"

if "%keyForLocal%"=="" (
    echo [ERROR] keyForLocal is not set.
    set "missing=1"
)

if "%keyForDev%"=="" (
    echo [ERROR] keyForDev is not set.
    set "missing=1"
)

if "%keyForProd%"=="" (
    echo [ERROR] keyForProd is not set.
    set "missing=1"
)

if "%missing%"=="1" (
    echo.
    echo To fix:
    echo   Windows: Go to Environment Variables and add keys, e.g.
    echo     keyForLocal = dummyPwd123LOCAL
    echo     keyForDev   = dummyPwd123DEV
    echo     keyForProd  = dummyPwd123PROD
    echo.
    pause
    exit /b 1
)

set "muleSecurePropsCli=%securepropsHome%\mule-secureprops-cli.jar"

set envKeyMapping="(local.postman_environment.json):(%keyForLocal%),((dev|uat).postman_environment.json):(%keyForDev%),(prod.postman_environment.json):(%keyForProd%),(.*.postman_collection.json):(%keyForDev%)"





REM Use current directory (.) if no argument is passed
if "%~1"=="" (
    set "directory=."
) else (
    set "directory=%~1"
)

java -jar "%muleSecurePropsCli%" decrypt file-level %directory% AES CBC true --envKeyMapping=%envKeyMapping% --tmp=.




