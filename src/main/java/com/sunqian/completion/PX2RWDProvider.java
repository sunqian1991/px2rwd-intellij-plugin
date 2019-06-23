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
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.sunqian.constvalue.MagicValue.*;

/**
 * 代码自动完成处理类
 */
@SuppressWarnings("RedundantOperationOnEmptyContainer")
public class PX2RWDProvider extends CompletionProvider<CompletionParameters> {
    private final String[] myItems;

    PX2RWDProvider(String... items) {
        myItems = items;
    }

    @Override
    public void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
        result.addAllElements(IntStream.range(0, myItems.length).mapToObj(i ->
            LookupElementBuilder.create(myItems[i])
                    .withCaseSensitivity(false).withTailText(TO_RWD_TIPS[i])
                    .withInsertHandler(LogicUtils.getLogic().generateObject(new HashMap<String, InsertHandler<LookupElement>>(), map ->
                            map.put(TO_REM_TIP, REM_HANDLER), map ->
                            map.put(TO_VW_TIP, VW_HANDLER), map ->
                            map.put(TO_VH_TIP, VH_HANDLER)).get(TO_RWD_TIPS[i]))).collect(Collectors.toList()));
    }

    private static final InsertHandler<LookupElement> DIRECTIVE_HANDLER = (context, item) ->
            Optional.of(ActionPerformer.getActionPerformer(context.getProject(), context.getEditor())).ifPresent(actionPerformer ->
                    FormatTools.getFormatTools(actionPerformer.getConstValue()).formatNearCode(actionPerformer)
            );

    private static final InsertHandler<LookupElement> REM_HANDLER = (context, item) ->
            Optional.of(ActionPerformer.getActionPerformer(context.getProject(), context.getEditor())).ifPresent(actionPerformer ->
                    FormatTools.getFormatTools(actionPerformer.getConstValue()).formatNearCode(actionPerformer)
            );

    private static final InsertHandler<LookupElement> VW_HANDLER = (context, item) ->
            Optional.of(ActionPerformer.getActionPerformer(context.getProject(), context.getEditor())).ifPresent(actionPerformer ->
                    FormatTools.getFormatTools(actionPerformer.getConstValue()).formatNearCode(actionPerformer)
            );

    private static final InsertHandler<LookupElement> VH_HANDLER = (context, item) ->
            Optional.of(ActionPerformer.getActionPerformer(context.getProject(), context.getEditor())).ifPresent(actionPerformer ->
                    FormatTools.getFormatTools(actionPerformer.getConstValue()).formatNearCode(actionPerformer)
            );
}
