package com.sunqian.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;

/**
 * @author sunqian
 * @date 2019/6/21
 */
public class PX2RWDCompletion extends CompletionContributor {
    public PX2RWDCompletion() {
        extend(
                CompletionType.BASIC,
                header(Constants.EXPORT_PACKAGE),
                new HeaderParametersProvider(Constants.VERSION_ATTRIBUTE, Constants.USES_DIRECTIVE + ':'));

        extend(
                CompletionType.BASIC,
                header(Constants.IMPORT_PACKAGE),
                new HeaderParametersProvider(Constants.VERSION_ATTRIBUTE, Constants.RESOLUTION_DIRECTIVE + ':'));

        extend(
                CompletionType.BASIC,
                directive(Constants.RESOLUTION_DIRECTIVE),
                new SimpleProvider(Constants.RESOLUTION_MANDATORY, Constants.RESOLUTION_OPTIONAL));
    }

    private static ElementPattern<PsiElement> header(String name) {
        return psiElement(ManifestTokenType.HEADER_VALUE_PART)
                .afterLeaf(";")
                .withSuperParent(3, psiElement(Header.class).withName(name));
    }

    private static ElementPattern<PsiElement> directive(String name) {
        return psiElement(ManifestTokenType.HEADER_VALUE_PART)
                .withSuperParent(2, psiElement(Directive.class).withName(name));
    }
}
