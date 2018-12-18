package com.sunq.utils;

import com.sunq.constvalue.ConstValue;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author:sunqian
 * Date:2018/12/8 15:39
 * Description:
 */
public class FormatTools {

    private ConstValue constValue;
    private FormatAccuracy formatAccuracy;

    public FormatTools(ConstValue constValue){
        this.constValue = constValue;
        formatAccuracy = new FormatAccuracy();
    }

    public String getFormatText(String ele){
        if(ele==null || ele.indexOf("px")==-1 || ele.equals(""))
            return ele.trim();
        double rem;
        double px;
        px = Double.valueOf(ele.substring(0, ele.indexOf("px")).trim());
        rem = px / this.constValue.getRemBaseValue();
//        rem = px / 100;
        return String.format(formatAccuracy.getAccuracy(constValue.getRemBaseValue()+""), rem).replaceAll("0*$","").replaceAll("\\.$","").trim() + "rem";
    }

    public String getFormatLine(String content){
        int index = -1;
        while((index = StringUtils.indexOf(content.toLowerCase(), "px")) > -1){
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
        Pattern pattern = Pattern.compile("-?[0-9]+(\\.[0-9]+)?");
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;
        }

        Matcher isNum = pattern.matcher(bigStr);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

}
