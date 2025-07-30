



REM Sets the path to the SecureProps tool home folder example c:\Mule\secureprops
set securepropsHome = { set home folder for secureprops tool }
 
set securepropsHome=target
set mule-secureprops-cli=%securepropsHome%\mule-secureprops-cli-1.0-SNAPSHOT.jar

  
java -jar %mule-secureprops-cli% --ui

pause


 







