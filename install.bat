@echo off
echo 初始化中，请稍后
%cd%\..\..\eclipse\jre\bin\java -Dfile.encoding=utf-8 -jar %cd%\..\..\eclipse\jre\lib\install.jar updateFileCrTime %cd%
echo 环境初始化完毕！
pause