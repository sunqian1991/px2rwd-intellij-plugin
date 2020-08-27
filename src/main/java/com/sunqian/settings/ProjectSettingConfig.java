package com.sunqian.settings;

import com.intellij.openapi.options.SearchableConfigurable;
import com.sunqian.constvalue.ConstValue;
import com.sunqian.constvalue.ShortCutType;
import com.sunqian.utils.LogicUtils;
import com.sunqian.utils.NumberUtils;
import com.sunqian.utils.StringUtils;
import lombok.Data;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

import static com.sunqian.constvalue.MagicValue.PLUGIN_NAME;

@Data
public class ProjectSettingConfig implements SearchableConfigurable {

    ProjectSettingsPage mainGui;

    private ConstValue constValue;

    public ProjectSettingConfig() {
        constValue = ConstValue.getInstance();
    }

    @NotNull
    @Override
    public String getId() {
        return getDisplayName();
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return PLUGIN_NAME;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        mainGui = new ProjectSettingsPage(constValue);
        return mainGui.getRootPanel();
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Override
    public boolean isModified() {
        return ifEdited();
    }

    @Nullable
    @Override
    public Runnable enableSearch(String option) {
        return null;
    }

    @Override
    public void disposeUIResources() {
        mainGui = null;
    }

    @Override
    public void reset() {
        mainGui.getShowCalculationProcessInCheckBox().setSelected(constValue.getShowCalculationProcess());
        mainGui.getRemRadioButton().setSelected(constValue.getShortCutType() == ShortCutType.REM);
        mainGui.getVwRadioButton().setSelected(constValue.getShortCutType() == ShortCutType.VW);
        mainGui.getVhRadioButton().setSelected(constValue.getShortCutType() == ShortCutType.VH);
        mainGui.getRemBaseValue().setText(constValue.getRemBaseValue() + "");
        mainGui.getVwValue().setText(constValue.getWidthValue().toString());
        mainGui.getVhValue().setText(constValue.getHeightValue().toString());
        mainGui.getRemIntention().setSelected(constValue.getRemIntention());
        mainGui.getVwIntention().setSelected(constValue.getVwIntention());
        mainGui.getVhIntention().setSelected(constValue.getVhIntention());
        mainGui.getRemCompletion().setSelected(constValue.getRemCompletion());
        mainGui.getVwCompletion().setSelected(constValue.getVwCompletion());
        mainGui.getVhCompletion().setSelected(constValue.getVhCompletion());
        mainGui.getOnlyCssFiles().setSelected(constValue.getOnlyCssFiles());
    }

    @Override
    public void apply() {
        LogicUtils.getLogic().conOrEnd(mainGui.getRemBaseValue().getText(), text -> Objects.nonNull(text) && NumberUtils.isCreatable(text) && NumberUtils.toDouble(text) > 0, text -> constValue.setRemBaseValue(mainGui.getRemBaseValue().getText()));
        constValue.setShowCalculationProcess(mainGui.getShowCalculationProcessInCheckBox().isSelected());
        LogicUtils.getLogic().conOrEnd(mainGui.getVwValue().getText(), text -> Objects.nonNull(text) && NumberUtils.isCreatable(text) && NumberUtils.toDouble(text) > 0, text -> constValue.setWidthValue(mainGui.getVwValue().getText()));
        LogicUtils.getLogic().conOrEnd(mainGui.getVhValue().getText(), text -> Objects.nonNull(text) && NumberUtils.isCreatable(text) && NumberUtils.toDouble(text) > 0, text -> constValue.setHeightValue(mainGui.getVhValue().getText()));
        constValue.setShortCutType(getShortCutType());
        constValue.setRemIntention(mainGui.getRemIntention().isSelected());
        constValue.setVwIntention(mainGui.getVwIntention().isSelected());
        constValue.setVhIntention(mainGui.getVhIntention().isSelected());
        constValue.setRemCompletion(mainGui.getRemCompletion().isSelected());
        constValue.setVwCompletion(mainGui.getVwCompletion().isSelected());
        constValue.setVhCompletion(mainGui.getVhCompletion().isSelected());
        constValue.setOnlyCssFiles(mainGui.getOnlyCssFiles().isSelected());
    }

    private ShortCutType getShortCutType() {
        return Optional.ofNullable(LogicUtils.getLogic().generateObject(new HashMap<String, ShortCutType>(), map ->
                map.put("rem", ShortCutType.REM), map ->
                map.put("vw", ShortCutType.VW), map ->
                map.put("vh", ShortCutType.VH)).get(StringUtils.join(
                mainGui.getRemRadioButton().isSelected() ? "rem" : "",
                mainGui.getVwRadioButton().isSelected() ? "vw" : "",
                mainGui.getVhRadioButton().isSelected() ? "vh" : ""
        ))).orElse(ShortCutType.REM);
    }

    private boolean ifEdited() {
        return LogicUtils.getLogic().or(flag -> !Objects.equals(flag[0], flag[1]), new Double[]{
                constValue.getRemBaseValue(), NumberUtils.toDouble(mainGui.getRemBaseValue().getText())
        }, new Double[]{
                constValue.getWidthValue(), NumberUtils.toDouble(mainGui.getVwValue().getText())
        }, new Double[]{
                constValue.getHeightValue(), NumberUtils.toDouble(mainGui.getVhValue().getText())
        }, new ShortCutType[]{
                constValue.getShortCutType(), getShortCutType()
        }, new Boolean[]{
                constValue.getShowCalculationProcess(), mainGui.getShowCalculationProcessInCheckBox().isSelected()
        }, new Boolean[]{
                constValue.getRemIntention(), mainGui.getRemIntention().isSelected()
        }, new Boolean[]{
                constValue.getVwIntention(), mainGui.getVwIntention().isSelected()
        }, new Boolean[]{
                constValue.getVhIntention(), mainGui.getVhIntention().isSelected()
        }, new Boolean[]{
                constValue.getRemCompletion(), mainGui.getRemCompletion().isSelected()
        }, new Boolean[]{
                constValue.getVwCompletion(), mainGui.getVwCompletion().isSelected()
        }, new Boolean[]{
                constValue.getVhCompletion(), mainGui.getVhCompletion().isSelected()
        }, new Boolean[]{
                constValue.getOnlyCssFiles(), mainGui.getOnlyCssFiles().isSelected()
        });
    }
}
