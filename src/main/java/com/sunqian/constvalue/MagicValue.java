package com.sunqian.constvalue;

import com.sunqian.utils.LogicUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 魔法值库
 *
 * @author sunqian
 * @date 2019/6/6
 */
@SuppressWarnings("ALL")
public class MagicValue {

    public static final String PX_STYLE_TAG = "px";

    public static final String REM_STYLE_TAG = "rem";

    public static final String VW_STYLE_TAG = "vw";

    public static final String VH_STYLE_TAG = "vh";

    public static final String NUMBER_PATTERN_FORMULA = "-?[0-9]+(\\.[0-9]+)?";

    public static final String NULL_STRING = "";

    public static final int CONFIG_DIALOG_WIDTH = 320;

    public static final int CONFIG_DIALOG_HEIGHT = 150;

    public static final String STYLE_PATTERN_FORMAT = "^[\\w\\W]+\\:[\\w\\W]*\\d+(\\.{1}(\\d+))?px[\\w\\W]*$";

    public static final String STYLE_SHEET_LANGUAGE_ID = "CSS";

    public static final String BLANK_STRING = " ";

    public static final String COLON_STRING = ":";

    public static final String AUTO_COMPLETION_TAG = "px";

    public static final String PLUGIN_NAME = "Px to Rwd";

    public static final String TO_REM_TIP = " to rem";

    public static final String TO_VW_TIP = " to vw";

    public static final String TO_VH_TIP = " to vh";

    public static final String[] TO_RWD_TIPS = new String[]{TO_VW_TIP, TO_VH_TIP, TO_REM_TIP};

    public static final Map STYLE_TAG_TYPE = LogicUtils.getLogic().generateObject(new HashMap<>(), map ->
            map.put(ShortCutType.REM, REM_STYLE_TAG), map ->
            map.put(ShortCutType.VW, VW_STYLE_TAG), map ->
            map.put(ShortCutType.VH, VH_STYLE_TAG)
    );

}
