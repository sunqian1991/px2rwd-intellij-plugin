package com.sunqian.constvalue;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

/**
 * Author:sunqian
 * Date:2018/8/8 12:19
 * Description:
 */
@State(name = "px2remForWebStorm",storages = {@Storage("px2remforwebstorm.xml")})
public class ConstValue implements PersistentStateComponent<ConstValue> {

    public String remBaseValue;

    public Boolean showCalculationProcess;

    public Boolean getShowCalculationProcess() {
        return showCalculationProcess == null ? false : showCalculationProcess;
    }

    public void setShowCalculationProcess(Boolean showCalculationProcess) {
        this.showCalculationProcess = showCalculationProcess;
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
        return Double.parseDouble(remBaseValue == null ? "100.0" : remBaseValue);
    }

    public void setRemBaseValue(String remBaseValue) {
        this.remBaseValue = remBaseValue;
    }

    @Nullable
    public static ConstValue getInstance(Project project) {
        return ServiceManager.getService(project, ConstValue.class);
    }
}
