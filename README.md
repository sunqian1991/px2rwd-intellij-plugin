## WebStorm-PX2REM----px转rem插件

[![codebeat badge](https://codebeat.co/badges/290c72c6-f4e8-4f45-81e0-b98c612805b6)](https://codebeat.co/projects/github-com-sunqian1991-webstorm-px2rem-master)
[![](https://img.shields.io/badge/license-MIT-000000.svg)](https://github.com/sunqian1991/WebStorm-PX2REM/blob/master/LICENSE)
[![](https://img.shields.io/jetbrains/plugin/v/11187.svg)](https://plugins.jetbrains.com/plugin/11187-px2rem)
[![](https://img.shields.io/jetbrains/plugin/d/11187.svg)](https://plugins.jetbrains.com/plugin/11187-px2rem)

#### 项目介绍

webstorm插件，主要是把css中的px转换为rem，其中可以在webstorm中设置转化的比例值(File-Other Settings-PX2REM)

本项目参考了 https://github.com/yclnycl/ideapx2rem 中的代码，在他的基础上加入的比值的设置，并修改了部分错误。其中在最新的2.0.0版本加入了一键转换整个文件的功能

目前是自动转换光标所在行内的正确书写的样式，或者转换光标选择的px样式，如果有更好的使用方式建议，欢迎提出，共同学习。


#### 当前版本

V2.0.2

#### 更新日志

v2.0.2:在注释中添加了计算的过程；修改了保留小数位数的设置，在可以被整除的情况下，不改变精度

v2.0.1:添加了转换时精度与设置的比例相对应

v2.0.0:添加了转换整个文件的快捷键，修改了转换行内单位的逻辑；因转换文件的快捷键和默认的有冲突，现在将快捷键换成'shift d'和'ctrl shift d'

v1.1.5:添加了中文说明

v1.1.4:添加了英文说明

v1.1.3:处理了部分错误

v1.1.2:添加了用于转换比例值的持久化 

v1.1.1:修复了当前行内包含多条样式时无法转换的问题，将设置窗口移动到file-other settings下

v1.1.0:在原有的基础上添加了自动转换当前光标所在行中可以转换的值，将设置比值的窗口更改到tools菜单下，其中，需要注意行内不能包含多条样式；

v1.0.0:初始编写，webstorm插件，主要是把css中的px转换为rem，其中可以在webstorm中设置转化的比值(window-SetPX2REM),选中后通过快捷键shift+f来转化，如比值为100，选中"100px"后，点击shift+d，自动转化为1.00rem

#### 联系我

如有任何问题，请联系我

QQ:991637393

email:sunqian1991@gmail.com

#### 安装教程

Project SDK选择IntelliJ IDEA IU-*****,调试没有错误后，即可生成jar文件，在webstorm中使用

#### 使用说明

1. File - Settings - plugins - Browse repositories... - 搜索 'px2rem' - Install (也可以通过 File-Settings-plugins-install plugin from disk-选择生成的jar文件-restart webstorm 在本地安装).
2. File-Other Settings-PX2REM设置计算的比例值-选中代码段或光标移动到需要转换的行，点击shift+d.
3. 设置界面和快捷键设置可以在插件中自定义.
4. v2.0.0版本中加入了一键转换整个文件的功能，通过使用快捷键'ctrl shift d'来转换.
5. v2.0.2版本中加入了选择在注释中生成转换样式时的计算过程，在File-Other Settings-PX2REM选择勾选框可开启

#### 注意事项
在使用插件转换时，**请避免在样式名称中使用值和`px`的组合**，如：
```css
.test200px{
    ...
}
```
此时`test200px`也会被自动转换，可能会造成极大风险

当启用在注释中显示计算过程的功能时，插件会在每次转换的样式的后面添加注释，在一些情况下会极大影响样式的可读性，请谨慎开启！！如：
```css
.test{
    box-shadow: 0.1rem    /* 10 / 100 */ 0.1rem    /* 10 / 100 */ 0.1rem    /* 10 / 100 */ #000;
    width:calc(100% - 0.2rem    /* 20 / 100 */);
}
```

![image][opt_gif]

#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request

本项目遵循MIT开源协议，代码当然可以被任意使用，同时也希望可以多多提交使用BUG和优化建议，如果能够pull request，那就太棒了！



[opt_gif]:https://github.com/sunqian1991/WebStorm-PX2REM/raw/dev/resources/option.gif
