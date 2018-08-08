package com.sunq.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import java.awt.*;

/**
 * Author:sunqian
 * Date:2018/8/8 12:39
 * Description:
 */
public class SetPX2REM extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        SetPX2REMTools dialog = new SetPX2REMTools();
        dialog.pack();
        dialog.setSize(300,150);
        int windowWidth = dialog.getWidth(); //获得窗口宽
        int windowHeight = dialog.getHeight(); //获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        int screenWidth = kit.getScreenSize().width; //获取屏幕的宽
        int screenHeight = kit.getScreenSize().height; //获取屏幕的高
        dialog.setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2);//设置窗口居中显示
        dialog.setVisible(true);
    }
}
