/*
 * Copyright (c) 2020 Maow, MIT License.
 */

package maow.tinierremapper.gui;

import maow.tinierremapper.TinierRemapper;
import net.fabricmc.tinyremapper.NonClassCopyMode;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SettingsWindow extends JFrame {
    private final int width;
    private final int height;

    public SettingsWindow(int width, int height) {
        this.width = width;
        this.height = height;

        init();
    }

    private void initComponents() {
        // Init

        RemapperWindow window = TinierRemapper.getWindow();

        // Creation

        JCheckBox ignoreFieldDescBox = new JCheckBox("Ignore Field Descriptions", false);
        JCheckBox propagatePrivateBox = new JCheckBox("Propagate Private", false);
        JCheckBox removeFramesBox = new JCheckBox("Remove Frames", false);
        JButton forcedPropagationButton = new JButton("Select Forced Propagation File...");
        JCheckBox ignoreConflictsBox = new JCheckBox("Ignore Conflicts", false);
        JCheckBox checkPackageAccessBox = new JCheckBox("Check Package Access", false);
        JCheckBox fixPackageAccessBox = new JCheckBox("Fix Package Access", false);
        JCheckBox resolveMissingBox = new JCheckBox("Resolve Missing", false);
        JCheckBox rebuildSourceFilenamesBox = new JCheckBox("Rebuild Source Filenames", false);
        JCheckBox skipLocalVariableNamingBox = new JCheckBox("Skip Local Variable Naming", false);
        JCheckBox renameInvalidLocalsBox = new JCheckBox("Rename Invalid Locals");
        JComboBox<NonClassCopyMode> nonClassCopyModeCombo = new JComboBox<>(NonClassCopyMode.values());

        ignoreFieldDescBox.setSelected(window.getArgs().ignoreFieldDesc());
        propagatePrivateBox.setSelected(window.getArgs().propagatePrivate());
        removeFramesBox.setSelected(window.getArgs().removeFrames());
        ignoreConflictsBox.setSelected(window.getArgs().ignoreConflicts());
        checkPackageAccessBox.setSelected(window.getArgs().checkPackageAccess());
        fixPackageAccessBox.setSelected(window.getArgs().fixPackageAccess());
        resolveMissingBox.setSelected(window.getArgs().resolveMissing());
        rebuildSourceFilenamesBox.setSelected(window.getArgs().rebuildSourceFilenames());
        skipLocalVariableNamingBox.setSelected(window.getArgs().skipLocalVariableNaming());
        renameInvalidLocalsBox.setSelected(window.getArgs().renameInvalidLocals());
        nonClassCopyModeCombo.setSelectedItem(window.getArgs().getNonClassCopyMode());

        // Functionality

        ignoreFieldDescBox.addActionListener((event) -> window.getArgs().setIgnoreFieldDesc(ignoreFieldDescBox.isSelected()));
        propagatePrivateBox.addActionListener((event) -> window.getArgs().setPropagatePrivate(propagatePrivateBox.isSelected()));
        removeFramesBox.addActionListener((event) -> window.getArgs().setRemoveFrames(removeFramesBox.isSelected()));

        forcedPropagationButton.addActionListener((event) -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select forced propagation file...");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                window.getArgs().setForcedPropagation(chooser.getSelectedFile().toPath());
            }
        });

        ignoreConflictsBox.addActionListener((event) -> window.getArgs().setIgnoreConflicts(ignoreConflictsBox.isSelected()));
        checkPackageAccessBox.addActionListener((event) -> window.getArgs().setCheckPackageAccess(checkPackageAccessBox.isSelected()));
        fixPackageAccessBox.addActionListener((event) -> window.getArgs().setFixPackageAccess(fixPackageAccessBox.isSelected()));
        resolveMissingBox.addActionListener((event) -> window.getArgs().setResolveMissing(resolveMissingBox.isSelected()));
        rebuildSourceFilenamesBox.addActionListener((event) -> window.getArgs().setRebuildSourceFilenames(rebuildSourceFilenamesBox.isSelected()));
        skipLocalVariableNamingBox.addActionListener((event) -> window.getArgs().setSkipLocalVariableNaming(skipLocalVariableNamingBox.isSelected()));
        renameInvalidLocalsBox.addActionListener((event) -> window.getArgs().setRenameInvalidLocals(renameInvalidLocalsBox.isSelected()));

        nonClassCopyModeCombo.addActionListener((event) -> window.getArgs().setNonClassCopyMode((NonClassCopyMode) Objects.requireNonNull(nonClassCopyModeCombo.getSelectedItem())));

        // Adding

        this.add(ignoreFieldDescBox);
        this.add(propagatePrivateBox);
        this.add(removeFramesBox);
        this.add(forcedPropagationButton);
        this.add(ignoreConflictsBox);
        this.add(checkPackageAccessBox);
        this.add(fixPackageAccessBox);
        this.add(resolveMissingBox);
        this.add(rebuildSourceFilenamesBox);
        this.add(skipLocalVariableNamingBox);
        this.add(renameInvalidLocalsBox);
        this.add(nonClassCopyModeCombo);
    }

    private void init() {
        this.setTitle("Advanced Settings");
        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        initComponents();

        this.setSize(width, height);
        this.setPreferredSize(getSize());
        this.setResizable(false);
        this.setVisible(true);
    }
}