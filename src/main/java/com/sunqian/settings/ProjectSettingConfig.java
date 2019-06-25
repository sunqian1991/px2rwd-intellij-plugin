package com.sunqian.settings;

import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.sunqian.constvalue.ConstValue;
import com.sunqian.constvalue.ShortCutType;
import com.sunqian.utils.LogicUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

import static com.sunqian.constvalue.MagicValue.PLUGIN_NAME;

@SuppressWarnings("RedundantOperationOnEmptyContainer")
@Data
public class ProjectSettingConfig implements SearchableConfigurable {

    ProjectSettingsPage mainGui;

    private Project project;
    private ConstValue constValue;

    public ProjectSettingConfig(@NotNull Project project) {
        this.project = project;
        constValue = ConstValue.getInstance(project);
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
    }

    @Override
    public void apply() {
        LogicUtils.getLogic().conOrEnd(mainGui.getRemBaseValue().getText(), text -> Objects.nonNull(text) && NumberUtils.isCreatable(mainGui.getRemBaseValue().getText()), text -> constValue.setRemBaseValue(mainGui.getRemBaseValue().getText()));
        constValue.setShowCalculationProcess(mainGui.getShowCalculationProcessInCheckBox().isSelected());
        LogicUtils.getLogic().conOrEnd(mainGui.getVwValue().getText(), text -> Objects.nonNull(text) && NumberUtils.isCreatable(text), text -> constValue.setWidthValue(mainGui.getVwValue().getText()));
        LogicUtils.getLogic().conOrEnd(mainGui.getVhValue().getText(), text -> Objects.nonNull(text) && NumberUtils.isCreatable(text), text -> constValue.setHeightValue(mainGui.getVhValue().getText()));
        constValue.setShortCutType(getShortCutType());
        constValue.setRemIntention(mainGui.getRemIntention().isSelected());
        constValue.setVwIntention(mainGui.getVwIntention().isSelected());
        constValue.setVhIntention(mainGui.getVhIntention().isSelected());
        constValue.setRemCompletion(mainGui.getRemCompletion().isSelected());
        constValue.setVwCompletion(mainGui.getVwCompletion().isSelected());
        constValue.setVhCompletion(mainGui.getVhCompletion().isSelected());
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
        });
    }
}
