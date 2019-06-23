package com.sunqian.intention;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import com.sunqian.model.ActionPerformer;
import com.sunqian.utils.FormatTools;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.sunqian.constvalue.MagicValue.STYLE_PATTERN_FORMAT;
import static com.sunqian.constvalue.MagicValue.STYLE_SHEET_LANGUAGE_ID;

/**
 * intention action类
 *
 * @author sunqian
 * @date 2019/6/21
 */
public class PX2VHIntention extends PsiElementBaseIntentionAction implements IntentionAction {
    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) throws IncorrectOperationException {
        Optional.ofNullable(ActionPerformer.getActionPerformer(project, editor)).ifPresent(ap ->
                Optional.of(FormatTools.getFormatTools(ap.getConstValue())).ifPresent(formatTools ->
                        formatTools.formatLineCode(ap)
                )
        );
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
        return IntentionUtils.isAvailable(project, editor, element);
    }

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @NotNull
    @Override
    public String getFamilyName() {
        return "Px to vh converter";
    }

    @NotNull
    @Override
    public String getText() {
        return "Px to vh";
    }

    @Override
    public boolean startInWriteAction() {
        return false;
    }
}
