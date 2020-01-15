package com.sunqian.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.sunqian.model.ActionPerformer;
import com.sunqian.utils.FormatTools;
import com.sunqian.utils.LogicUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Optional;

import static com.sunqian.constvalue.MagicValue.*;
import static com.sunqian.constvalue.MagicValue.STYLUS_LANGUAGE_ID;

/**
 * 转换回退action
 *
 * @author sunqian
 * @date 2019/6/25
 */
public class ConvertRollback extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        LogicUtils.getLogic().conOrEnd(anActionEvent.getData(LangDataKeys.PSI_FILE), file -> Objects.nonNull(file) && StringUtils.containsAny(file.getLanguage().getID(), STYLE_SHEET_LANGUAGE_ID, LESS_LANGUAGE_ID, SASS_LANGUAGE_ID, HTML_LANGUAGE_ID, SCSS_LANGUAGE_ID, STYLUS_LANGUAGE_ID, TWIG_LANGUAGE_ID), file ->
                Optional.ofNullable(ActionPerformer.getActionPerformer(anActionEvent.getRequiredData(CommonDataKeys.PROJECT), anActionEvent.getRequiredData(CommonDataKeys.EDITOR))).ifPresent(ap ->
                        Optional.of(FormatTools.getFormatTools(ap.getConstValue())).ifPresent(formatTools ->
                                formatTools.rollbackStyle(ap, ap.getDocument().getLineNumber(ap.getCaretModel().getOffset()))
                        )
                )
        );
    }

}
