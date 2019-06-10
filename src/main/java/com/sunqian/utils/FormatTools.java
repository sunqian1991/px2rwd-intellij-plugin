package com.sunqian.utils;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.util.TextRange;
import com.sunqian.constvalue.ConstValue;
import com.sunqian.model.ActionPerformer;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.sunqian.constvalue.MagicValue.*;

/**
 * 转换工具类
 *
 * @author sunqian
 * @date 2018/12/8 15:39
 */
@NoArgsConstructor
public class FormatTools {

    @Setter
    private volatile static FormatTools formatTools;

    private ConstValue constValue;

    private static Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_PATTERN_FORMULA);

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

    private String getFormatText(String ele) {
        return Optional.ofNullable(ele).filter(text -> text.contains(PX_STYLE_TAG) && !Objects.equals(NULL_STRING, text)).map(text ->
            Optional.of(NumberUtils.toDouble(text.substring(0, ele.indexOf(PX_STYLE_TAG)).trim())).map(px ->
                Optional.of(px / this.constValue.getRemBaseValue()).map(rem ->
                    Optional.of(check(px, this.constValue.getRemBaseValue())).filter(ifDivide -> ifDivide).map(ifDivide ->
                        (rem.toString()).replaceAll("0*$", "").replaceAll("\\.$", "") + "rem" + showComment(px, this.constValue.getRemBaseValue())
                    ).orElseGet(() ->
                        String.format(getAccuracy(constValue.getRemBaseValue() + ""), rem).replaceAll("0*$", "").replaceAll("\\.$", "").trim() + "rem" + showComment(px, this.constValue.getRemBaseValue())
                    )
                )
            ).get()
        ).get().orElse(ele);
    }

    private String getFormatLine(String content) {
        int index;
        while ((index = StringUtils.indexOf(content.toLowerCase(), PX_STYLE_TAG)) > -1) {
            int startIndex = index;
            while (isNumeric(content.substring(startIndex - 1, index)) && startIndex > 0) {
                startIndex--;
            }
            if (startIndex != index) {
                String value = content.substring(startIndex, index) + PX_STYLE_TAG;
                content = content.substring(0, startIndex) + getFormatText(value) + content.substring(index + 2);
            } else {
                break;
            }
        }

        return content;
    }

    /**
     * 匹配是否为数字
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
    private boolean check(double amount, double count) {
        return Optional.of(amount % count).filter(times -> times == 0).map(times -> true).orElseGet(() ->
            amount % LogicUtils.getLogic().funWithWhile(LogicUtils.getLogic().funWithWhile(count, m -> m % 2 == 0, m -> m / 2), n -> n % 5 == 0, n -> n / 5) == 0
        );
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
     *
     * @param actionPerformer 获取的动作参数
     */
    public void formatLineCode(ActionPerformer actionPerformer) {
        // 行号
        Optional.of(actionPerformer.getDocument().getLineNumber(actionPerformer.getCaretModel().getOffset())).ifPresent(lineNum -> {
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
                                                    getFormatLine(lineContent))
                                    );
                                    return lineContent;
                                })
                );
            });
        });
    }

    /**
     * 转换选择的代码
     *
     * @param actionPerformer 获取的动作参数
     */
    public void formatSelectCode(ActionPerformer actionPerformer){
        // 光标选择的文字
        Optional.ofNullable(actionPerformer.getSelectionModel().getSelectedText()).ifPresent(selectText ->
            Optional.of(selectText.indexOf(PX_STYLE_TAG)).filter(index -> index > -1).map(index -> {
                // 选择区域开始
                Optional.of(actionPerformer.getSelectionModel().getSelectionStart()).ifPresent(start -> {
                    // 选择区域结束
                    Optional.of(actionPerformer.getSelectionModel().getSelectionEnd()).ifPresent(end ->
                        Optional.of(NumberUtils.toDouble(selectText.substring(0, index))).ifPresent(px ->
                            Optional.of(px / actionPerformer.getConstValue().getRemBaseValue()).ifPresent(rem ->
                                WriteCommandAction.runWriteCommandAction(actionPerformer.getProject(), () ->
                                        actionPerformer.getDocument().replaceString(
                                                start,
                                                end,
                                                Optional.of(check(px, actionPerformer.getConstValue().getRemBaseValue())).filter(ifDivide -> ifDivide).map(ifDivide ->
                                                        (rem+"").replaceAll("0*$","").replaceAll("\\.$","")+"rem"+showComment(px, actionPerformer.getConstValue().getRemBaseValue())
                                                ).orElseGet(() ->
                                                        String.format(getAccuracy(actionPerformer.getConstValue().getRemBaseValue() + ""), rem)
                                                                .replaceAll("0*$","")
                                                                .replaceAll("\\.$","") + "rem" +
                                                                showComment(px, actionPerformer.getConstValue().getRemBaseValue())
                                                ))
                                )
                            )
                        )
                    );
                });
                return index;
            })
        );
    }

}
