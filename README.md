## PX2RWD-Intellij-Plugin

[![](https://img.shields.io/badge/license-MIT-000000.svg)](https://github.com/sunqian1991/px2rwd-intellij-plugin/blob/master/LICENSE)
[![](https://img.shields.io/jetbrains/plugin/v/11187.svg)](https://plugins.jetbrains.com/plugin/11187-px2rem)
[![](https://img.shields.io/jetbrains/plugin/d/11187.svg)](https://plugins.jetbrains.com/plugin/11187-px2rem)
[![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu)

[中文](./README_CN.md)

#### Description

This is a tool of converting px to rem/vw/vh in a css/less file by a few settings used in intellij idea or webstorm.

#### Install

This plugin has been put in the market and you can find it in `file - settings - plugins - marketplace` by searching a text of `px2rem`.

You can also download the plugin file which has been put in [release](https://github.com/sunqian1991/px2rwd-intellij-plugin/releases), and install the plugin from disk.

#### Contact me

If you have any questions or suggestions, please contact me.

QQ: 991637393

email: sunqian1991@gmail.com


#### Usage

there are three ways to convert: short-cut key, code intention, code completion. you can find the settings related to these converting ways in settings page with the path 'File - Settings - Px to Rem'

1. short-cut key

select a converting type from three options of rem, vw, vh in setting page and give a necessary value set below the radio group, then use the default short-cut key of <kbd>Alt</kbd> + <kbd>d</kbd> to convert a line or a selected text. you can also use the short-cut key of <kbd>Ctrl</kbd> + <kbd>Alt</kbd> + <kbd>d</kbd> to convert in a whole file.
you can only choose one converting type of short-cut key from rem,vw,vg to convert.
btw, you can change the short-cut key in your ide by setting the keymap option.

2. code intention

you can find three code intention types in settings page and you can select if a code intention type works by check the checkbox.
by check a code intention in a line of a css file, which will display by typing Alt + Shift, you can convert the line where the caret displays.
the unselected code intention types in settings page will not show in the list of code intention in a css file

3. code completion

you can also find three code completion types in the settings page and you can select if a code completion type works by check the checkbox.
by typing 'px' in a css file, you can find some code completion types you set in settings page. choose a type then it will give a result of converting.
the unselected code completion types in settings page will not show in the list of code completion in a css file

Instructions:
1. find menu at 'File - Settings - Px to Rem', and give some settings.
2. select a text or move cursor at a line which contains a 'px' value.
3. use default shortcut key <kbd>Alt</kbd> + <kbd>d</kbd> to convert px to rem/vw/vh with a line; btw, you can change the shortcut at 'File - Settings - keymap - Plug-ins - px2rem'.
4. use default shortcut key <kbd>Ctrl</kbd> + <kbd>Alt</kbd> + <kbd>d</kbd> to convert px to rem/vw/vh in a whole file.
5. use a code intention to convert px to rem/vw/vh in a css file
6. use a code completion to convert px to rem/vw/vh in a css file
7. use the short-cut key <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>Alt</kbd> + <kbd>d</kbd> to rollback the converting within one line


![image][opt_gif]


[opt_gif]:https://github.com/sunqian1991/px2rwd-intellij-plugin/raw/master/src/main/resources/images/px2rwd.gif
