package com.sunqian.action;

import com.intellij.openapi.project.Project;
import com.sunqian.constvalue.ConstValue;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.swing.*;
import java.awt.event.*;

public class SetPX2REMTools extends JDialog {
    private static final long serialVersionUID = -9001684391377248720L;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField base_value;
    private JCheckBox showCalculationProcessInCheckBox;
    private ConstValue constValue;

    SetPX2REMTools(Project project) {
        constValue = ConstValue.getInstance(project);
        setTitle("Plug-in Settings");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        base_value.setText(constValue.getRemBaseValue() + "");
        showCalculationProcessInCheckBox.setSelected(constValue.getShowCalculationProcess() != null ? constValue.getShowCalculationProcess() : false);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        String value = base_value.getText();
        try {
            constValue.setShowCalculationProcess(showCalculationProcessInCheckBox.isSelected());
            double rem = Double.parseDouble(value);
            if (rem > 0) {
                constValue.setRemBaseValue(rem + "");
                dispose();
            }
        } catch (Exception e) {
            throw new RuntimeException(ExceptionUtils.getRootCause(e));
        }
    }

    private void onCancel() {
        dispose();
    }

}
