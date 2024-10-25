@echo off
setlocal

echo -------------------------------
echo This cmd program automatically registers the path where the this file is located in the path user environment variable.
echo If you do not agree to run this program, type no and press Enter.
echo -------------------------------
set /p code=Press Enter to launch the program:
if "%code%"=="no" goto :false

:true
setx path "%path%;%~dp0;"
pause

:false
endlocal