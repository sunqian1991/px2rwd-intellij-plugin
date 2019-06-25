package com.sunqian.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.sunqian.model.ActionPerformer;
import com.sunqian.utils.FormatTools;
import com.sunqian.utils.LogicUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import static com.sunqian.constvalue.MagicValue.*;

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
        LogicUtils.getLogic().conOrEnd(anActionEvent.getData(LangDataKeys.PSI_FILE), file -> Objects.nonNull(file) && StringUtils.containsAny(file.getLanguage().getID(), STYLE_SHEET_LANGUAGE_ID, LESS_LANGUAGE_ID, SASS_LANGUAGE_ID, HTML_LANGUAGE_ID, SCSS_LANGUAGE_ID, STYLUS_LANGUAGE_ID), file ->
                Optional.ofNullable(ActionPerformer.getActionPerformer(anActionEvent.getRequiredData(CommonDataKeys.PROJECT), anActionEvent.getRequiredData(CommonDataKeys.EDITOR))).ifPresent(ap ->
                        Optional.of(FormatTools.getFormatTools(ap.getConstValue())).ifPresent(formatTools ->
                                LogicUtils.getLogic().generateObject(new HashMap<String, Consumer<ActionPerformer>>(), map -> {
                                    map.put("0", actionPerformer -> formatTools.formatLineCode(actionPerformer, actionPerformer.getConstValue().getShortCutType()));
                                    map.put("1", actionPerformer -> formatTools.formatSelectCode(actionPerformer, actionPerformer.getConstValue().getShortCutType()));
                                }).get(Optional.ofNullable(ap.getSelectionModel().getSelectedText()).filter(text -> !Objects.equals(text, "")).map(text -> "1").orElse("0")).accept(ap)
                        )
                )
        );
    }

}
