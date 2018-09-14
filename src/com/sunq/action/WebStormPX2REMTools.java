package com.sunq.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.sunq.constvalue.ConstValue;

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

        if(s==null||s.equals(""))
            return;

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
