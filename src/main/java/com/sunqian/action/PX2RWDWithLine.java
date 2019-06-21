package com.sunqian.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.sunqian.model.ActionPerformer;
import com.sunqian.utils.FormatTools;
import com.sunqian.utils.LogicUtils;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * PX转响应式布局样式单位----按行处理
 * <p>
 * 0 处理整行
 * 1 处理选中的文字
 *
 * @author sunqian
 * @date 2019/6/10
 */
@SuppressWarnings("ALL")
public class PX2RWDWithLine extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Optional.ofNullable(ActionPerformer.getActionPerformer(anActionEvent.getRequiredData(CommonDataKeys.PROJECT), anActionEvent.getRequiredData(CommonDataKeys.EDITOR))).ifPresent(ap -> {
            Optional.of(FormatTools.getFormatTools(ap.getConstValue())).ifPresent(formatTools -> {
                LogicUtils.getLogic().generateObject(new HashMap<String, Consumer<ActionPerformer>>(), map -> {
                    map.put("0", actionPerformer -> formatTools.formatLineCode(actionPerformer));
                    map.put("1", actionPerformer -> formatTools.formatSelectCode(actionPerformer));
                }).get(Optional.ofNullable(ap.getSelectionModel().getSelectedText()).filter(text -> !Objects.equals(text, "")).map(text -> "1").orElse("0")).accept(ap);
            });
        });
    }

}
