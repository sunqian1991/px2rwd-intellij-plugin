package com.sunqian.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.sunqian.constvalue.ConstValue;
import com.sunqian.utils.FormatAccuracy;
import com.sunqian.utils.FormatTools;

/**
 * Author:sunqian
 * Date:2018/8/8 12:11
 * Description:
 */
public class WebStormPX2REMTools extends AnAction {

    private ConstValue constValue;
    private Project project;
    private FormatTools formatTools;
    private FormatAccuracy formatAccuracy;

    @Override
    public void actionPerformed(AnActionEvent e) {
        project = e.getRequiredData(CommonDataKeys.PROJECT);
        constValue = ConstValue.getInstance(project);
        formatTools = new FormatTools(constValue);
        formatAccuracy = new FormatAccuracy();

        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        final Document document = editor.getDocument();
        final SelectionModel selectionModel = editor.getSelectionModel();

        final int start = selectionModel.getSelectionStart();
        final int end = selectionModel.getSelectionEnd();
        String s = selectionModel.getSelectedText();

        try{
            if(s==null||s.equals("")){
                CaretModel caretModel = editor.getCaretModel();
                int caretOffset = caretModel.getOffset();
                int lineNum = document.getLineNumber(caretOffset);
                int lineStartOffset = document.getLineStartOffset(lineNum);
                int lineEndOffset = document.getLineEndOffset(lineNum);
                String lineContent = document.getText(new TextRange(lineStartOffset, lineEndOffset));

                if(lineContent.toLowerCase().indexOf("px") > -1) {
                    WriteCommandAction.runWriteCommandAction(project, () ->
                            document.replaceString(lineStartOffset, lineEndOffset, formatTools.getFormatLine(lineContent))
                    );
                }

                return;
            }

            int index = s.indexOf("px");
            double rem;
            double px;
            if(index == -1){
                return;
            }else{
                px = Double.valueOf(s.substring(0, index));
                rem = px / constValue.getRemBaseValue();

                boolean ifDivide = formatTools.check(px, this.constValue.getRemBaseValue());

                WriteCommandAction.runWriteCommandAction(project, () ->
                        document.replaceString(start, end, ifDivide ? (rem+"").replaceAll("0*$","").replaceAll("\\.$","")+"rem"+formatTools.showComment(px, this.constValue.getRemBaseValue()) : String.format(formatAccuracy.getAccuracy(constValue.getRemBaseValue()+""), rem).replaceAll("0*$","").replaceAll("\\.$","") + "rem" + formatTools.showComment(px, this.constValue.getRemBaseValue()))
                );
                return;
            }
        } catch (Exception ex){
            return;
        }
    }
}
