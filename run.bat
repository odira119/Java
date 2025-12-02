@echo off
echo ================================================
echo   Uzima Borehole System - Quick Start
echo ================================================
echo.

REM Check if Maven is installed
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Maven is not installed or not in PATH
    echo Please install Maven from https://maven.apache.org/download.cgi
    echo.
    pause
    exit /b 1
)

echo [INFO] Maven found
echo.

REM Check if MySQL is running
echo [INFO] Checking MySQL service...
sc query MySQL80 | find "RUNNING" >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [WARNING] MySQL service not detected as running
    echo Please ensure MySQL is installed and running
    echo.
)

echo [INFO] Building project with Maven...
call mvn clean compile
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Build failed
    pause
    exit /b 1
)

echo.
echo [INFO] Starting Uzima Borehole System...
echo.
echo ================================================
echo   Database: uzima_db (auto-created)
echo   Default Admin: admin / admin123
echo ================================================
echo.

call mvn javafx:run

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] Application failed to start
    echo.
    echo Common issues:
    echo - MySQL not running
    echo - Wrong MySQL credentials in DBConnection.java
    echo - JavaFX not properly configured
    echo.
    pause
    exit /b 1
)

pause
