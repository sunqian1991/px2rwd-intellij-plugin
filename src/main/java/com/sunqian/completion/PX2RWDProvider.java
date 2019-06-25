package com.sunqian.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.util.ProcessingContext;
import com.sunqian.constvalue.ShortCutType;
import com.sunqian.model.ActionPerformer;
import com.sunqian.utils.FormatTools;
import com.sunqian.utils.LogicUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
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
        Optional.of(Arrays.stream(TO_RWD_TIPS).filter(item -> filterCompletionItem.test(item, ActionPerformer.getActionPerformer(parameters.getEditor().getProject(), parameters.getEditor()))).collect(Collectors.toList())).ifPresent(tips -> {
            result.addAllElements(IntStream.range(0, tips.size()).mapToObj(i ->
                    LookupElementBuilder.create(myItems[i])
                            .withCaseSensitivity(false).withTailText(tips.get(i))
                            .withInsertHandler(LogicUtils.getLogic().generateObject(new HashMap<String, InsertHandler<LookupElement>>(), map ->
                                    map.put(TO_REM_TIP, PX_REM_HANDLER), map ->
                                    map.put(TO_VW_TIP, PX_VW_HANDLER), map ->
                                    map.put(TO_VH_TIP, PX_VH_HANDLER)).get(tips.get(i)))).collect(Collectors.toList()));
            result.addAllElements(Arrays.stream(RWD_TIPS).map(tip ->
                    LookupElementBuilder.create(tip)
                            .withCaseSensitivity(false).withTailText(PREFIX_TAIL_TIPS + tip)
                            .withInsertHandler(LogicUtils.getLogic().generateObject(new HashMap<String, InsertHandler<LookupElement>>(), map ->
                                    map.put(REM_STYLE_TAG, REM_HANDLER), map ->
                                    map.put(VW_STYLE_TAG, VW_HANDLER), map ->
                                    map.put(VH_STYLE_TAG, VH_HANDLER)).get(tip))).collect(Collectors.toList()));
        });
    }

    private RwdTipsPredicate<String, ActionPerformer> filterCompletionItem = (item, actionPerformer) ->
            Objects.equals(item, TO_REM_TIP) && (actionPerformer.getConstValue().getRemCompletion()) ||
            Objects.equals(item, TO_VW_TIP) && (actionPerformer.getConstValue().getVwCompletion()) ||
            Objects.equals(item, TO_VH_TIP) && (actionPerformer.getConstValue().getVhCompletion());

    @FunctionalInterface
    private interface RwdTipsPredicate<T, E>  {
        boolean test(T t, E e);
    }

    private static final InsertHandler<LookupElement> REM_HANDLER = (context, item) ->
            Optional.of(ActionPerformer.getActionPerformer(context.getProject(), context.getEditor())).ifPresent(actionPerformer ->
                    FormatTools.getFormatTools(actionPerformer.getConstValue()).formatNearCode(actionPerformer, ShortCutType.REM, ShortCutType.REM)
            );

    private static final InsertHandler<LookupElement> VW_HANDLER = (context, item) ->
            Optional.of(ActionPerformer.getActionPerformer(context.getProject(), context.getEditor())).ifPresent(actionPerformer ->
                    FormatTools.getFormatTools(actionPerformer.getConstValue()).formatNearCode(actionPerformer, ShortCutType.VW, ShortCutType.VW)
            );

    private static final InsertHandler<LookupElement> VH_HANDLER = (context, item) ->
            Optional.of(ActionPerformer.getActionPerformer(context.getProject(), context.getEditor())).ifPresent(actionPerformer ->
                    FormatTools.getFormatTools(actionPerformer.getConstValue()).formatNearCode(actionPerformer, ShortCutType.VH, ShortCutType.VH)
            );

    private static final InsertHandler<LookupElement> PX_REM_HANDLER = (context, item) ->
            Optional.of(ActionPerformer.getActionPerformer(context.getProject(), context.getEditor())).ifPresent(actionPerformer ->
                    FormatTools.getFormatTools(actionPerformer.getConstValue()).formatNearCode(actionPerformer, ShortCutType.REM)
            );

    private static final InsertHandler<LookupElement> PX_VW_HANDLER = (context, item) ->
            Optional.of(ActionPerformer.getActionPerformer(context.getProject(), context.getEditor())).ifPresent(actionPerformer ->
                    FormatTools.getFormatTools(actionPerformer.getConstValue()).formatNearCode(actionPerformer, ShortCutType.VW)
            );

    private static final InsertHandler<LookupElement> PX_VH_HANDLER = (context, item) ->
            Optional.of(ActionPerformer.getActionPerformer(context.getProject(), context.getEditor())).ifPresent(actionPerformer ->
                    FormatTools.getFormatTools(actionPerformer.getConstValue()).formatNearCode(actionPerformer, ShortCutType.VH)
            );
}
