package com.sunqian.utils;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.sunqian.constvalue.ConstValue;
import com.sunqian.constvalue.ShortCutType;
import com.sunqian.model.ActionPerformer;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.sunqian.constvalue.MagicValue.*;

/**
 * 转换工具类
 *
 * @author sunqian
 * date 2018/12/8 15:39
 */
@NoArgsConstructor
public class FormatTools {

    @Setter
    private volatile static FormatTools formatTools;

    private ConstValue constValue;

    private static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_PATTERN_FORMULA);

    private FormatTools(ConstValue constValue) {
        this.constValue = constValue;
    }

    public static FormatTools getFormatTools(ConstValue constValue) {
        return Optional.ofNullable(formatTools).orElseGet(() -> {
            synchronized (FormatTools.class) {
                return Optional.ofNullable(formatTools).orElseGet(() -> new FormatTools(constValue));
            }
        });
    }

    private String getFormatText(String ele, ShortCutType shortCutType) {
        return Optional.ofNullable(ele).filter(text -> text.contains(PX_STYLE_TAG) && !Objects.equals(NULL_STRING, text)).map(text ->
                valueFormat(NumberUtils.toDouble(text.substring(0, text.indexOf(PX_STYLE_TAG)).trim()), shortCutType)
        ).orElse(ele);
    }

    private String valueFormat(Double px, ShortCutType shortCutType) {
        Double baseValue = this.constValue.baseValueType().get(shortCutType);
        if (Objects.isNull(baseValue)) {
            return px.toString();
        }
        Double rem = px / baseValue;
        if (check(Double.toString(px), Double.toString(baseValue))) {
            return rem.toString().replaceAll("0*$", "").replaceAll("\\.$", "") + STYLE_TAG_TYPE.get(shortCutType) + showComment(px, baseValue);
        }
        return String.format(getAccuracy(baseValue + ""), rem).replaceAll("0*$", "").replaceAll("\\.$", "").trim() + STYLE_TAG_TYPE.get(shortCutType) + showComment(px, baseValue);
    }

    /**
     * 计算回退
     *
     * @param result       计算结果
     * @param shortCutType 类型
     * @return 返回回退的结果
     */
    private String valueRollback(String result, ShortCutType shortCutType) {
        if (!StringUtils.containsAny(result, REM_STYLE_TAG, VW_STYLE_TAG, VH_STYLE_TAG)) {
            return result;
        }
        if (Objects.equals(NULL_STRING, result)) {
            return result;
        }
        Double baseValue = this.constValue.baseValueType().get(shortCutType);
        if (Objects.isNull(baseValue)) {
            return result;
        }
        return Math.round(NumberUtils.toDouble(result.substring(0, result.indexOf(STYLE_TAG_TYPE.get(shortCutType))).trim()) * baseValue) + PX_STYLE_TAG;
    }

    private String getFormatLine(String content, ShortCutType shortCutType, String styleTag, FormatStyleValue<String> formatStyleValue) {
        int index;
        while ((index = StringUtils.indexOf(content.toLowerCase(), styleTag)) > -1) {
            int startIndex = index;
            while (isNumeric(content.substring(startIndex - 1, index)) && startIndex > 0) {
                startIndex--;
            }
            if (startIndex != index) {
                String value = content.substring(startIndex, index) + styleTag;
                content = content.substring(0, startIndex) + formatStyleValue.apply(value, shortCutType) + content.substring(index + styleTag.length());
            } else {
                break;
            }
        }

        return content;
    }

    @FunctionalInterface
    private interface FormatStyleValue<R> {

        R apply(String value, ShortCutType shortCutType);

    }

    /**
     * 回退转换过程
     *
     * @param actionPerformer 动作参数
     * @param lineNum         行号
     */
    public void rollbackStyle(ActionPerformer actionPerformer, int lineNum) {
        int lineStartOffset = actionPerformer.getDocument().getLineStartOffset(lineNum);
        int lineEndOffset = actionPerformer.getDocument().getLineEndOffset(lineNum);
        String lineContent = actionPerformer.getDocument().getText(new TextRange(lineStartOffset, lineEndOffset));
        WriteCommandAction.runWriteCommandAction(actionPerformer.getProject(), () ->
                actionPerformer.getDocument().replaceString(
                        lineStartOffset,
                        lineEndOffset,
                        getFormatLine(
                                getFormatLine(
                                        getFormatLine(
                                                lineContent,
                                                ShortCutType.REM,
                                                REM_STYLE_TAG,
                                                this::valueRollback),
                                        ShortCutType.VW,
                                        VW_STYLE_TAG,
                                        this::valueRollback),
                                ShortCutType.VH,
                                VH_STYLE_TAG,
                                this::valueRollback
                        )
                )
        );
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str 被判断的字符串
     * @return 返回是否是数字
     */
    private static boolean isNumeric(String str) {
        return NUMBER_PATTERN.matcher(str).matches();
    }

    /**
     * 检查是否可以被除尽
     *
     * @param amount 被除数
     * @param count  除数
     * @return 是否可以被除尽
     */
    @SuppressWarnings("BigDecimalMethodWithoutRoundingCalled")
    private boolean check(String amount, String count) {
        BigDecimal dividend = new BigDecimal(amount.replaceAll("\\.", ""));
        BigDecimal divisor = new BigDecimal(count.replaceAll("\\.", ""));
        BigDecimal zero = new BigDecimal("0");
        BigDecimal two = new BigDecimal("2");
        BigDecimal five = new BigDecimal("5");
        BigDecimal[] results = dividend.divideAndRemainder(divisor);
        if (Objects.equals(zero, results[1])) {
            return true;
        }
        results = dividend.divideAndRemainder(
                LogicUtils.getLogic().funWithWhile(
                        LogicUtils.getLogic().funWithWhile(divisor, m ->
                                Objects.equals(
                                        m.divideAndRemainder(two)[1],
                                        zero
                                ), m -> m.divide(two)
                        ), n -> Objects.equals(n.divideAndRemainder(five)[1], zero), n -> n.divide(five)));
        return Objects.equals(results[1], zero);
    }

    /**
     * 通过注释的方式生成计算过程
     *
     * @param obj1 被除数
     * @param obj2 除数
     * @return 返回计算公式
     */
    private String showComment(Object obj1, Object obj2) {
        return constValue.getShowCalculationProcess() ? "  /* " + obj1.toString().replaceAll("0*$", "").replaceAll("\\.$", "") + "/" + obj2.toString().replaceAll("0*$", "").replaceAll("\\.$", "") + " */" : "";
    }

    private String getAccuracy(String remValue) {
        return MessageFormat.format("%.{0}f", remValue.substring(0, !StringUtils.contains(remValue, ".") ? remValue.length() : StringUtils.indexOf(remValue, ".")).length() + 1);
    }

    /**
     * 转换一行的代码
     * <p>
     * 这里行号取的是当前光标所在的行
     *
     * @param actionPerformer 获取的动作参数
     */
    public void formatLineCode(ActionPerformer actionPerformer, ShortCutType shortCutType) {
        actionPerformer.getCaretModel().runForEachCaret(caret -> formatLineCode(actionPerformer, actionPerformer.getDocument().getLineNumber(caret.getOffset()), shortCutType));
    }

    /**
     * 转换一行代码中的样式单位
     *
     * @param actionPerformer 获取的动作参数
     * @param lineNum         行号
     */
    public void formatLineCode(ActionPerformer actionPerformer, int lineNum, ShortCutType shortCutType) {
        // 行起始offset
        Optional.of(actionPerformer.getDocument().getLineStartOffset(lineNum)).ifPresent(lineStartOffset -> {
            // 行结束offset
            Optional.of(actionPerformer.getDocument().getLineEndOffset(lineNum)).ifPresent(lineEndOffset ->
                    Optional.of(actionPerformer.getDocument().getText(new TextRange(lineStartOffset, lineEndOffset)))
                            .filter(lineContent -> lineContent.toLowerCase().contains(PX_STYLE_TAG))
                            .map(lineContent -> {
                                WriteCommandAction.runWriteCommandAction(actionPerformer.getProject(), () ->
                                        actionPerformer.getDocument().replaceString(
                                                lineStartOffset,
                                                lineEndOffset,
                                                getFormatLine(lineContent, shortCutType, PX_STYLE_TAG, (value, type) -> getFormatText(value, shortCutType)))
                                );
                                return lineContent;
                            })
            );
        });
    }

    /**
     * 格式化光标处往前最近的一个可格化的长度
     *
     * @param actionPerformer 获取的动作参数
     */
    public void formatNearCode(ActionPerformer actionPerformer, ShortCutType shortCutType, ShortCutType unit) {
        Document document = actionPerformer.getDocument();
        CaretModel caretModel = actionPerformer.getCaretModel();
        int lineNum = document.getLineNumber(caretModel.getOffset());
        int lineStartOffset = document.getLineStartOffset(lineNum);
        String lineContent = document.getText(new TextRange(lineStartOffset, caretModel.getOffset()));
        String content = lineContent.substring(getNearCode(lineContent) + 1, lineContent.length() - STYLE_TAG_TYPE.get(unit).length()).trim();
        formatText(
                content,
                new int[]{
                        caretModel.getOffset() - content.length() - STYLE_TAG_TYPE.get(unit).length(),
                        caretModel.getOffset(),
                },
                actionPerformer,
                shortCutType);
    }

    public void formatNearCode(ActionPerformer actionPerformer, ShortCutType shortCutType) {
        formatNearCode(actionPerformer, shortCutType, ShortCutType.PX);
    }

    /**
     * 取一个长度单位的起始index
     *
     * @param content 待处理文本
     * @return 返回文本中包含的一个长度单位的起始index
     */
    private int getNearCode(String content) {
        return NumberUtils.max(StringUtils.lastIndexOf(content, COLON_STRING), StringUtils.lastIndexOf(content, BLANK_STRING));
    }

    /**
     * 转换选择的代码
     *
     * @param actionPerformer 获取的动作参数
     */
    public void formatSelectCode(ActionPerformer actionPerformer, ShortCutType shortCutType) {
        // 光标选择的文字
        String selectTexts = actionPerformer.getSelectionModel().getSelectedText(true);
        if (Objects.isNull(selectTexts)) {
            return;
        }
        String[] testArray = selectTexts.split("\n");
        int[] starts = actionPerformer.getSelectionModel().getBlockSelectionStarts();
        int[] ends = actionPerformer.getSelectionModel().getBlockSelectionEnds();
        if (testArray.length != starts.length || testArray.length != ends.length) {
            return;
        }
        for (int i = 0; i < testArray.length; i++) {
            String selectText = testArray[i];
            if (selectText.contains(PX_STYLE_TAG)) {
                formatText(
                        selectText.substring(0, selectText.indexOf(PX_STYLE_TAG)),
                        new int[]{
                                starts[i],
                                ends[i]
                        },
                        actionPerformer,
                        shortCutType);
                starts = actionPerformer.getSelectionModel().getBlockSelectionStarts();
                ends = actionPerformer.getSelectionModel().getBlockSelectionEnds();
            }
        }
    }

    /**
     * 格式化指定的style文本
     *
     * @param style           style文本
     * @param position        起止index
     * @param actionPerformer 动作参数
     */
    private void formatText(String style, int[] position, ActionPerformer actionPerformer, ShortCutType shortCutType) {
        Optional.of(style).filter(FormatTools::isNumeric).ifPresent(text ->
                WriteCommandAction.runWriteCommandAction(actionPerformer.getProject(), () ->
                        actionPerformer.getDocument().replaceString(
                                position[0],
                                position[1],
                                getFormatText(style + PX_STYLE_TAG, shortCutType))
                )
        );
    }

}
