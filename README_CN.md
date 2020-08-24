## PX2RWD-Intellij-Plugin

[![](https://img.shields.io/badge/license-MIT-000000.svg)](https://github.com/sunqian1991/px2rwd-intellij-plugin/blob/master/LICENSE)
[![](https://img.shields.io/jetbrains/plugin/v/11187.svg)](https://plugins.jetbrains.com/plugin/11187-px2rem)
[![](https://img.shields.io/jetbrains/plugin/d/11187.svg)](https://plugins.jetbrains.com/plugin/11187-px2rem)
[![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu)

#### 项目介绍

这是一个转换css文件中的px单位为rem/vw/vh等自适应设计页面（Responsive Web Design）单位的插件，主要适用于idea和webstorm.

#### 安装

<iframe frameborder="none" width="245px" height="48px" src="https://plugins.jetbrains.com/embeddable/install/11187"></iframe>

此插件已经提交到插件仓库中，可以直接在插件市场 `file - settings - plugins - markerplace` 中搜索 `px2rem`.

你也可以直接从 [release](https://github.com/sunqian1991/px2rwd-intellij-plugin/releases) 库中下载最新的版本，然后从本地安装。

#### 联系我

有任何问题或者建议都可以联系我。

QQ: 991637393

Email: sunqian1991@gmail.com


#### 使用方法

目前提供了三种方式来转换：快捷键、代码意图提示(<kbd>Alt</kbd> + <kbd>Enter</kbd>)、代码自动完成提示

1. 快捷键

默认的快捷键是<kbd>Alt</kbd> + <kbd>d</kbd>，在插件配置页面中可以看到快捷键部分有3种转换类型的选项，分别是rem,vw,vh，选择其中的一个来使用快捷键转换，需要注意的是，需要在选项下方的值配置中设置对应的基值。
只能选择一种转换类型来使用快捷键转换单位。同时也可以重新设置快捷键。需要注意的是，跟之前的版本相比，默认的快捷键修改了，修改的原因主要是原来的<kbd>Shift</kbd> + <kbd>d</kbd>会与大小写冲突。

2. code intention

选择使用代码意图提示的类型后即可使用已选择的类型来在css文件中通过<kbd>Alt</kbd> + <kbd>Shift</kbd>来调用code intention来快捷转换单位。未勾选的选项不会出现在文件的code intention列表中。

3. code completion

选择使用代码自动提示的类型后即可使用已选择的类型来在css文件中快捷转换单位，当输入'px'后，code completion列表中会出现在配置页面中选择的类型名称，选择其中一个类型后即可实现自动完成转换功能。

如果觉得这个插件还不错，希望给我加个星，非常感激。

使用说明：
1. File-Settings-Px to Rem进行必要的参数配置
2. 选择一个需要转换的样式或者将光标移动到一个包含需要转换样式的行中，使用快捷键<kbd>Alt</kbd> + <kbd>d</kbd>来转换，也可以使用<kbd>Ctrl</kbd> + <kbd>Alt</kbd> + <kbd>d</kbd>来转换整个文件中的可转换样式
3. 通过code intention<kbd>Alt</kbd> + <kbd>Enter</kbd>来转换某一行中涉及到的样式
4. 通过在输入'px'字符后显示的自动完成提示列表中选择相应的选项来转换相应的样式
5. 通过快捷键<kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>Alt</kbd> + <kbd>d</kbd>来回退一行内的转换

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


[opt_gif]:https://github.com/sunqian1991/px2rwd-intellij-plugin/blob/master/src/main/resources/images/px2rwd.gif