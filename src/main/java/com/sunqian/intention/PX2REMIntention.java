package com.sunqian.intention;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import com.sunqian.constvalue.ShortCutType;
import com.sunqian.model.ActionPerformer;
import com.sunqian.utils.FormatTools;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * intention action类
 *
 * @author sunqian
 * @date 2019/6/21
 */
public class PX2REMIntention extends PsiElementBaseIntentionAction implements IntentionAction {
    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) throws IncorrectOperationException {
        Optional.ofNullable(ActionPerformer.getActionPerformer(project, editor)).ifPresent(ap ->
                Optional.of(FormatTools.getFormatTools(ap.getConstValue())).ifPresent(formatTools ->
                        formatTools.formatLineCode(ap, ShortCutType.REM)
                )
        );
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
        return ActionPerformer.getActionPerformer(project, editor).getConstValue().getRemIntention() && IntentionUtils.isAvailable(editor, element);
    }

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @NotNull
    @Override
    public String getFamilyName() {
        return "Px to rem converter";
    }

    @NotNull
    @Override
    public String getText() {
        return "Px to rem";
    }

    @Override
    public boolean startInWriteAction() {
        return false;
    }
}
