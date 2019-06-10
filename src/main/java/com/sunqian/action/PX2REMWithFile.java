package com.sunqian.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.util.TextRange;
import com.sunqian.model.ActionPerformer;
import com.sunqian.utils.FormatTools;

import java.util.Optional;
import java.util.stream.IntStream;

import static com.sunqian.constvalue.MagicValue.PX_STYLE_TAG;

/**
 * PX转响应式布局样式单位----按文件处理
 *
 * @author sunqian
 * @date 2019/6/10
 */
@SuppressWarnings("duplicate")
public class PX2REMWithFile extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Optional.of(ActionPerformer.getActionPerformer(anActionEvent.getRequiredData(CommonDataKeys.PROJECT), anActionEvent.getRequiredData(CommonDataKeys.EDITOR))).ifPresent(actionPerformer ->
                Optional.of(FormatTools.getFormatTools(actionPerformer.getConstValue())).ifPresent(formatTools ->
                        IntStream.range(0, actionPerformer.getDocument().getLineCount()).forEach(lineNum ->
                                formatTools.formatLineCode(actionPerformer)
                        )
                )
        );
    }
}
