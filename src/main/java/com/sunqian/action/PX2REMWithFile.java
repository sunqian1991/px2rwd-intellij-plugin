package com.sunqian.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.sunqian.constvalue.ConstValue;
import com.sunqian.utils.FormatTools;

import java.util.stream.IntStream;

/**
 * Author:sunqian
 * Date:2018/12/8 15:31
 * Description:
 */
public class PX2REMWithFile extends AnAction {

    private ConstValue constValue;
    private Project project;

    private FormatTools formatTools;

    @Override
    public void actionPerformed(AnActionEvent e) {
        project = e.getRequiredData(CommonDataKeys.PROJECT);
        constValue = ConstValue.getInstance(project);
        formatTools = new FormatTools(constValue);

        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        final Document document = editor.getDocument();
        final SelectionModel selectionModel = editor.getSelectionModel();
        IntStream.range(0,document.getLineCount()).forEach(lineNum -> {
            int lineStartOffset = document.getLineStartOffset(lineNum);
            int lineEndOffset = document.getLineEndOffset(lineNum);
            String lineContent = document.getText(new TextRange(lineStartOffset, lineEndOffset));

            if(lineContent.toLowerCase().indexOf("px") > -1){
                WriteCommandAction.runWriteCommandAction(project, () ->
                        document.replaceString(lineStartOffset, lineEndOffset, formatTools.getFormatLine(lineContent))
                );
            }
        });
    }
}
