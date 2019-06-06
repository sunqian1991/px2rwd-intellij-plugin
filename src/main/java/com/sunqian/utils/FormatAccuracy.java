package com.sunqian.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

/**
 * Author:sunqian
 * Date:2018/12/18 15:24
 * Description:
 */
public class FormatAccuracy {

    public String getAccuracy(String remValue) {
        return MessageFormat.format("%.{0}f", remValue.substring(0, !StringUtils.contains(remValue, ".") ? remValue.length() : StringUtils.indexOf(remValue, ".")).length() + 1);
    }

}
