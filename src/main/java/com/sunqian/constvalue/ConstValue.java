package com.sunqian.constvalue;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import lombok.Setter;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * 配置文件参数管理类
 *
 * @author sunqian
 * @date 2018/8/8 12:19
 */
@State(name = "px2remForWebStorm", storages = {@Storage("px2remforwebstorm.xml")})
public class ConstValue implements PersistentStateComponent<ConstValue> {

    @Setter
    private String remBaseValue;

    @Setter
    private Boolean showCalculationProcess;

    public Boolean getShowCalculationProcess() {
        return Optional.ofNullable(showCalculationProcess).orElse(false);
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
        return NumberUtils.toDouble(Optional.ofNullable(this.remBaseValue).orElse("100.0"));
    }

    @Nullable
    public static ConstValue getInstance(Project project) {
        return ServiceManager.getService(project, ConstValue.class);
    }
}
