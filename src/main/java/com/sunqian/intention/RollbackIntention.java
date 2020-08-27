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
import com.sunqian.utils.StringUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

import static com.sunqian.constvalue.MagicValue.*;

/**
 * intention action类
 *
 * @author sunqian
 * date 2019/6/21
 */
public class RollbackIntention extends PsiElementBaseIntentionAction implements IntentionAction {
    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) throws IncorrectOperationException {
        Optional.ofNullable(ActionPerformer.getActionPerformer(project, editor)).ifPresent(ap ->
                Optional.of(FormatTools.getFormatTools(ap.getConstValue())).ifPresent(formatTools ->
                        ap.getCaretModel().runForEachCaret(caret -> formatTools.rollbackStyle(ap, ap.getDocument().getLineNumber(caret.getOffset())))
                )
        );
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
        return Optional.of(element.getLanguage()).filter(language -> Objects.equals(language.getID(), STYLE_SHEET_LANGUAGE_ID)).map(language -> Optional.of(editor.getDocument().getLineNumber(editor.getCaretModel().getOffset())).flatMap(lineNum -> Optional.of(editor.getDocument().getText(
                new TextRange(editor.getDocument().getLineStartOffset(lineNum), editor.getDocument().getLineEndOffset(lineNum))
        )).map(text -> StringUtils.containsAny(text, REM_STYLE_TAG, VW_STYLE_TAG, VH_STYLE_TAG))).orElse(false)).orElse(false);
    }

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @NotNull
    @Override
    public String getFamilyName() {
        return "Rem/vw/vh to px";
    }

    @NotNull
    @Override
    public String getText() {
        return "Rem/vw/vh to px";
    }

    @Override
    public boolean startInWriteAction() {
        return false;
    }
}
