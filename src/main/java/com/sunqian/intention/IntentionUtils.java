package com.sunqian.intention;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.sunqian.constvalue.MagicValue.STYLE_PATTERN_FORMAT;
import static com.sunqian.constvalue.MagicValue.STYLE_SHEET_LANGUAGE_ID;

public class IntentionUtils {

    static boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element){
        return Optional.of(element.getLanguage()).filter(language -> Objects.equals(language.getID(), STYLE_SHEET_LANGUAGE_ID)).map(language -> Optional.of(editor.getDocument().getLineNumber(editor.getCaretModel().getOffset())).map(lineNum ->
                Optional.of(editor.getDocument().getText(
                        new TextRange(editor.getDocument().getLineStartOffset(lineNum), editor.getDocument().getLineEndOffset(lineNum))
                )).map(text -> Pattern.compile(STYLE_PATTERN_FORMAT).matcher(text.toLowerCase()).matches()).get()
        ).orElse(false)).orElse(false);
    }

}
