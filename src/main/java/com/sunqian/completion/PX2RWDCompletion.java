package com.sunqian.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.css.impl.CssElementTypes;
import com.sunqian.utils.LogicUtils;

import java.util.Arrays;

import static com.sunqian.constvalue.MagicValue.AUTO_COMPLETION_TAG;
import static com.sunqian.constvalue.MagicValue.TO_RWD_TIPS;

/**
 * 代码自动完成
 *
 * @author sunqian
 * @date 2019/6/21
 */
public class PX2RWDCompletion extends CompletionContributor {
    public PX2RWDCompletion() {
        extend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement(CssElementTypes.CSS_IDENT).withParent(PlatformPatterns.psiElement(CssElementTypes.CSS_NUMBER_TERM)),
                new PX2RWDProvider(LogicUtils.getLogic().generateObject(new String[TO_RWD_TIPS.length], tips -> Arrays.fill(tips, AUTO_COMPLETION_TAG)))
        );
    }
}
