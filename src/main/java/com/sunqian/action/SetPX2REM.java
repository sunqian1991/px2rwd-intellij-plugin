package com.sunqian.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.sunqian.utils.LogicUtils;

import java.awt.*;
import java.util.Optional;

import static com.sunqian.constvalue.MagicValue.CONFIG_DIALOG_HEIGHT;
import static com.sunqian.constvalue.MagicValue.CONFIG_DIALOG_WIDTH;

/**
 * 设置页面弹窗
 *
 * @author sunqian
 * @date 2018/8/8
 */
public class SetPX2REM extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Optional.of(e.getRequiredData(CommonDataKeys.PROJECT)).ifPresent(project ->
                LogicUtils.getLogic().generateObject(new SetPX2REMTools(project), dialog -> {
                    dialog.pack();
                    dialog.setSize(CONFIG_DIALOG_WIDTH, CONFIG_DIALOG_HEIGHT);
                    Optional.ofNullable(Toolkit.getDefaultToolkit()).ifPresent(kit -> {
                        dialog.setLocation(kit.getScreenSize().width / 2 - dialog.getWidth() / 2, kit.getScreenSize().height / 2 - dialog.getHeight() / 2);
                        dialog.setVisible(true);
                    });
                })
        );
    }
}
