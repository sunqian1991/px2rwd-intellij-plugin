package com.sunq.action;

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
import com.sunq.constvalue.ConstValue;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Author:sunqian
 * Date:2018/8/8 12:11
 * Description:
 */
public class WebStormPX2REMTools extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        final Document document = editor.getDocument();
        final SelectionModel selectionModel = editor.getSelectionModel();

        final int start = selectionModel.getSelectionStart();
        final int end = selectionModel.getSelectionEnd();
        //获取idea选中区域数字
        String s = selectionModel.getSelectedText();

        if(s==null||s.equals("")){
            CaretModel caretModel = editor.getCaretModel();
            int caretOffset = caretModel.getOffset();
            int lineNum = document.getLineNumber(caretOffset);
            int lineStartOffset = document.getLineStartOffset(lineNum);
            int lineEndOffset = document.getLineEndOffset(lineNum);
            String lineContent = document.getText(new TextRange(lineStartOffset, lineEndOffset));
            int formatStart = lineContent.indexOf(":")+1;
            if(formatStart==0||lineContent.indexOf("px")==-1)
                return;
            String formatText = lineContent.substring(formatStart);
            int formatEnd = formatText.indexOf("px");
            if(formatEnd==-1)
                return;
            formatText = formatText.replace(";","");
            String results = StringUtils.join(Arrays.asList(formatText.split(" ")).stream().map((ele)->{
                if(ele==null||ele.indexOf("px")==-1||ele.equals(""))
                    return ele.trim();
                double rem;
                double px;
                px = Double.valueOf(ele.substring(0, ele.indexOf("px")).trim());
                rem = px / ConstValue.remBaseValue;
                return String.format("%.2f", rem).trim() + "rem";
            }).collect(Collectors.toList()).toArray(), " ") + ";";

            WriteCommandAction.runWriteCommandAction(project, () ->
                    document.replaceString(lineStartOffset+formatStart, lineEndOffset, results)
            );
            return;
        }

        int index = s.indexOf("px");
        double rem;
        double px;
        if(index == -1){
            return;
        }else{
            px = Double.valueOf(s.substring(0, index));
            rem = px / ConstValue.remBaseValue;
            WriteCommandAction.runWriteCommandAction(project, () ->
                    document.replaceString(start, end, String.format("%.2f", rem) + "rem")
            );
            return;
        }
    }
}
