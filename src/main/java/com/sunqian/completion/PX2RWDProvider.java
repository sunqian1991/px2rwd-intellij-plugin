package com.sunqian.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.util.ProcessingContext;
import com.sunqian.model.ActionPerformer;
import com.sunqian.utils.FormatTools;
import com.sunqian.utils.LogicUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;

/**
 * 代码自动完成处理类
 */
public class PX2RWDProvider extends CompletionProvider<CompletionParameters> {
    private final String[] myItems;

    PX2RWDProvider(String... items) {
        myItems = items;
    }

    @Override
    public void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
        Arrays.stream(myItems).forEach((LogicUtils.ExceptionConsumer<String>) item ->
                result.addElement(LookupElementBuilder.create(item)
                        .withCaseSensitivity(false).withTailText(" to rem/vw")
                        .withInsertHandler(DIRECTIVE_HANDLER))
        );
    }

    private static final InsertHandler<LookupElement> DIRECTIVE_HANDLER = (context, item) ->
            Optional.of(ActionPerformer.getActionPerformer(context.getProject(), context.getEditor())).ifPresent(actionPerformer ->
                    FormatTools.getFormatTools(actionPerformer.getConstValue()).formatNearCode(actionPerformer)
            );
}
