# WebStorm-PX2REM

#### 项目介绍

webstorm插件，主要是把css中的px转换为rem，其中可以在webstorm中设置转化的比值(window-SetPX2REM),选中后通过快捷键shift+f来转化，如比值为100，选中"100px"后，点击shift+f，自动转化为1.00rem。

本项目参考了 https://github.com/yclnycl/ideapx2rem 中的代码，在他的基础上加入的比值的设置，并修改了部分错误

#### 当前版本

V1.1.0

#### 更新日志

v1.1.0:在原有的基础上添加了自动转换当前光标所在行中可以转换的值，将设置比值的窗口更改到tools菜单下，其中，需要注意行内不能包含多条样式；

v1.0.0:初始编写，webstorm插件，主要是把css中的px转换为rem，其中可以在webstorm中设置转化的比值(window-SetPX2REM),选中后通过快捷键shift+f来转化，如比值为100，选中"100px"后，点击shift+f，自动转化为1.00rem

#### 联系我

如有任何问题，请联系我

QQ:991637393

email:sunqian1991@gmail.com

#### 安装教程

Project SDK选择IntelliJ IDEA IU-*****,调试没有错误后，即可生成jar文件，在webstorm中使用

#### 使用说明

1. file-settings-plugins-install plugin from disk-选择生成的jar文件-restart webstorm
2. window-SetPX2REM设置计算的比值-选中代码段，如'100px'-后，点击shift+f
3. 设置界面和快捷键设置可以在插件中自定义

#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request
