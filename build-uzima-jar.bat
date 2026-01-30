@echo off
REM Build a runnable JAR for UzimaBoreholeSystem
cd /d %~dp0
if not exist target mkdir target
javac -d target -cp lib/* src/UzimaBoreholeSystem/*.java src/UzimaBoreholeSystem/database/*.java src/UzimaBoreholeSystem/models/*.java src/UzimaBoreholeSystem/services/*.java src/UzimaBoreholeSystem/ui/admin/*.java src/UzimaBoreholeSystem/ui/client/*.java src/UzimaBoreholeSystem/ui/login/*.java src/UzimaBoreholeSystem/utils/*.java
cd target
jar cfe UzimaBoreholeSystem.jar UzimaBoreholeSystem.Main UzimaBoreholeSystem/*.class UzimaBoreholeSystem/database/*.class UzimaBoreholeSystem/models/*.class UzimaBoreholeSystem/services/*.class UzimaBoreholeSystem/ui/admin/*.class UzimaBoreholeSystem/ui/client/*.class UzimaBoreholeSystem/ui/login/*.class UzimaBoreholeSystem/utils/*.class
cd ..
echo JAR created at target\UzimaBoreholeSystem.jar
