package com.sunqian.constvalue;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.sunqian.utils.LogicUtils;
import lombok.Data;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 配置文件参数管理类
 *
 * @author sunqian
 * @date 2018/8/8 12:19
 */
@State(name = "px2remForWebStorm", storages = {@Storage("px2remforwebstorm.xml")})
@Data
public class ConstValue implements PersistentStateComponent<ConstValue> {

    private String remBaseValue;

    private Boolean showCalculationProcess;

    private ShortCutType shortCutType;

    private String widthValue;

    private String heightValue;

    private Boolean remIntention;

    private Boolean vwIntention;

    private Boolean vhIntention;

    private Boolean remCompletion;

    private Boolean vwCompletion;

    private Boolean vhCompletion;

    public Boolean getShowCalculationProcess() {
        return Optional.ofNullable(showCalculationProcess).orElse(false);
    }

    public ShortCutType getShortCutType() {
        return Optional.ofNullable(shortCutType).orElse(ShortCutType.REM);
    }

    public Double getWidthValue() {
        return LogicUtils.getLogic().funOrElse(NumberUtils.toDouble(Optional.ofNullable(widthValue).orElse("1920")), value -> value < 0, value -> 1920d, value -> value);
    }

    public Double getHeightValue() {
        return LogicUtils.getLogic().funOrElse(NumberUtils.toDouble(Optional.ofNullable(heightValue).orElse("1080")), value -> value < 0, value -> 1080d, value -> value);
    }

    public Boolean getRemIntention() {
        return Optional.ofNullable(remIntention).orElse(true);
    }

    public Boolean getVwIntention() {
        return Optional.ofNullable(vwIntention).orElse(false);
    }

    public Boolean getVhIntention() {
        return Optional.ofNullable(vhIntention).orElse(false);
    }

    public Boolean getRemCompletion() {
        return Optional.ofNullable(remCompletion).orElse(true);
    }

    public Boolean getVwCompletion() {
        return Optional.ofNullable(vwCompletion).orElse(false);
    }

    public Boolean getVhCompletion() {
        return Optional.ofNullable(vhCompletion).orElse(false);
    }

    public Map<ShortCutType, Double> baseValueType() {
        return LogicUtils.getLogic().generateObject(new HashMap<>(), map ->
                map.put(ShortCutType.REM, this.getRemBaseValue()), map ->
                map.put(ShortCutType.VW, this.getWidthValue() / 100), map ->
                map.put(ShortCutType.VH, this.getHeightValue() / 100));
    }

    @Override
    public ConstValue getState() {
        return this;
    }

    @Override
    public void loadState(ConstValue state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public double getRemBaseValue() {
        return LogicUtils.getLogic().funOrElse(NumberUtils.toDouble(Optional.ofNullable(this.remBaseValue).orElse("100.0")), value -> value < 0, value -> 100d, value -> value);
    }

    @Nullable
    public static ConstValue getInstance(Project project) {
        return ServiceManager.getService(project, ConstValue.class);
    }
}
