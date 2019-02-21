package com.sunq.utils;

import clojure.lang.Obj;
import com.sunq.constvalue.ConstValue;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sunqian
 * @date 2018/12/8 15:39
 * description 转换工具类
 */
public class FormatTools {

    private ConstValue constValue;
    private FormatAccuracy formatAccuracy;

    private static Pattern NUMBER_PATTERN = Pattern.compile("-?[0-9]+(\\.[0-9]+)?");

    public FormatTools(ConstValue constValue){
        this.constValue = constValue;
        formatAccuracy = new FormatAccuracy();
    }

    private String getFormatText(String ele){
        String styleTag = "px";
        if(ele==null || !ele.contains(styleTag) || "".equals(ele)) {
            return ele;
        }
        double rem;
        double px;
        px = Double.valueOf(ele.substring(0, ele.indexOf("px")).trim());
        boolean ifDivide = check(px, this.constValue.getRemBaseValue());
        rem = px / this.constValue.getRemBaseValue();
//        rem = px / 100;
        return ifDivide ? (rem+"").replaceAll("0*$","").replaceAll("\\.$","") + "rem" + showComment(px, this.constValue.getRemBaseValue()) : String.format(formatAccuracy.getAccuracy(constValue.getRemBaseValue()+""), rem).replaceAll("0*$","").replaceAll("\\.$","").trim() + "rem" + showComment(px, this.constValue.getRemBaseValue());
    }

    public String getFormatLine(String content){
        int index = -1;
        String styleTag = "px";
        while((index = StringUtils.indexOf(content.toLowerCase(), styleTag)) > -1){
            int startIndex = index;
            while(isNumeric(content.substring(startIndex-1,index)) && startIndex > 0){
                startIndex--;
            }
            if(startIndex != index){
                String value = content.substring(startIndex,index) + "px";
                content = content.substring(0, startIndex) + getFormatText(value) + content.substring(index +2);
            } else{
                break;
            }
        }

        return content;
    }

    /**
     * 匹配是否为数字
     */
    private static boolean isNumeric(String str) {
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;
        }

        Matcher isNum = NUMBER_PATTERN.matcher(bigStr);
        return isNum.matches();
    }

    /**
     * 检查是否可以被除尽
     * @param amount 被除数
     * @param count 除数
     * @return 是否可以被除尽
     */
    public boolean check(double amount,double count){
        if(amount % count == 0) {
            return true;
        }
        else {
            double m = count;
            while(m % 2 == 0){
                m = m / 2;
            }
            while(m % 5 == 0){
                m = m / 5;
            }
            if(amount % m != 0){
                return false;
            }
        }

        return true;
    }

    public String showComment(Object obj1, Object obj2){
        return constValue.getShowCalculationProcess() ? "  /* " + obj1.toString().replaceAll("0*$","").replaceAll("\\.$","") + "/" + obj2.toString().replaceAll("0*$","").replaceAll("\\.$","") + " */" : "";
    }

}
