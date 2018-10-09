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

/**
 * Author:sunqian
 * Date:2018/8/8 12:11
 * Description:
 */
public class WebStormPX2REMTools extends AnAction {

    private ConstValue constValue;
    private Project project;

    @Override
    public void actionPerformed(AnActionEvent e) {
        project = e.getRequiredData(CommonDataKeys.PROJECT);
        constValue = ConstValue.getInstance(project);

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
            //如果行内的样式不是以 ; 结束，则在末尾加上;
            if(!lineContent.endsWith(";"))
                lineContent = lineContent + ";";

            int formatStart = lineContent.indexOf(":")+1;
            //如果行内的内容中不包含 : 或 px，则返回
            if(formatStart==0||lineContent.indexOf("px")==-1)
                return;
            try{
                int semiCount = StringUtils.countMatches(lineContent,";");
                if(semiCount > 1){
                    //去除最后的  ;
                    String formatText = lineContent;

                    if(lineContent.endsWith(";"))
                        formatText = lineContent + ";";

                    //这里是判断  :  的个数与样式的个数是否一样
                    if(formatText.split(";").length != StringUtils.countMatches(formatText,":"))
                        return;
                    String cssLine = StringUtils.join(Arrays.stream(formatText.split(";")).map((ele)->{
                        int colonIndex= ele.indexOf(":");
                        if(colonIndex > -1 && ele.indexOf("px") > -1){
                            String results = StringUtils.join(Arrays.stream(ele.substring(colonIndex+1).split(" ")).map((el)->(getFormatText(el))).toArray(), " ");
                            return ele.substring(0, colonIndex+1) + results;
                        }
                        return ele;
                    }).toArray(),";");
                    WriteCommandAction.runWriteCommandAction(project, () ->
                            document.replaceString(lineStartOffset, lineEndOffset, cssLine + (cssLine.endsWith(";") ? "" : ";"))
                    );
                }
                else if(semiCount == 1){
                    String formatText = lineContent.substring(formatStart);
                    String results = StringUtils.join(Arrays.stream(formatText.split(" ")).map((ele)->(getFormatText(ele))).toArray(), " ");

                    WriteCommandAction.runWriteCommandAction(project, () ->
                            document.replaceString(lineStartOffset+formatStart, lineEndOffset, results + (results.endsWith(";") ? "" : ";"))
                    );
                }
            } catch (Exception ex){
                return;
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
            WriteCommandAction.runWriteCommandAction(project, () ->
                    document.replaceString(start, end, String.format("%.2f", rem) + "rem" + (s.endsWith(";")?";":""))
            );
            return;
        }
    }

    private String getFormatText(String ele){
        if(ele==null||ele.indexOf("px")==-1||ele.equals(""))
            return ele.trim();
        double rem;
        double px;
        px = Double.valueOf(ele.substring(0, ele.indexOf("px")).trim());
        rem = px / constValue.getRemBaseValue();
        return String.format("%.2f", rem).trim() + "rem";
    }
}
