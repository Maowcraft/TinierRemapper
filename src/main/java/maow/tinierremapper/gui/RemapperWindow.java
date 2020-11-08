/*
 * Copyright (c) 2020 Maow, MIT License.
 */

package maow.tinierremapper.gui;

import maow.tinierremapper.TinierRemapper;
import maow.tinierremapper.gui.component.HintedTextField;
import maow.tinierremapper.util.Args;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class RemapperWindow extends JFrame {
    private final int width;
    private final int height;

    private File inputFile;
    private File outputFile = new File("remapped.jar");
    private File mappingsFile;
    private final Args args = new Args();

    public RemapperWindow(int width, int height) {
        this.width = width;
        this.height = height;

        init();
    }

    private void initComponents() {
        // Constraint init
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Creation

        JButton selectInputButton = new JButton("Select Input Jar...");
        JButton selectMappingsButton = new JButton("Select Mappings File...");
        JButton selectOutputButton = new JButton("Select Output Path...");

        HintedTextField fromMappingsField = new HintedTextField("From mappings");
        HintedTextField toMappingsField = new HintedTextField("To mappings");

        JButton settingsButton = new JButton("Advanced Settings");
        JButton mapButton = new JButton("Start Mapping Process");

        // Functionality

        //// Input button

        selectInputButton.addActionListener((event) -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select input file...");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setFileFilter(new FileNameExtensionFilter("Jar file (.jar)", "jar"));
            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                inputFile = chooser.getSelectedFile();
            }
        });

        //// Mappings button

        selectMappingsButton.addActionListener((event) -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select mappings file...");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setFileFilter(new FileNameExtensionFilter("Tiny mappings file (.tiny)", "tiny"));
            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                mappingsFile = chooser.getSelectedFile();
            }
        });

        //// Output button

        selectOutputButton.addActionListener((event) -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select output path...");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setFileFilter(new FileNameExtensionFilter("Jar file (.jar)", "jar"));
            chooser.setSelectedFile(outputFile.getAbsoluteFile());
            int returnVal = chooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                outputFile = chooser.getSelectedFile();
            }
        });

        //// Settings button

        settingsButton.addActionListener((event) -> new SettingsWindow(500, 175));

        //// Map button

        mapButton.addActionListener((event) -> {
            String from = fromMappingsField.getText();
            String to = toMappingsField.getText();
            if (inputFile == null || mappingsFile == null) {
                JOptionPane.showMessageDialog(this, "One or more required files hasn't been selected.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                TinierRemapper.remap(args, inputFile.toPath(), outputFile.toPath(), mappingsFile.toPath(), from, to);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred while performing this action.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "Finished remapping process!", "Finished", JOptionPane.INFORMATION_MESSAGE);
        });

        // Adding

        //// Input button

        gbc.insets = new Insets(0, 0, 3, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(selectInputButton, gbc);

        //// Mappings button

        gbc.gridy = 1;
        this.add(selectMappingsButton, gbc);

        //// Output button

        gbc.gridy = 2;
        this.add(selectOutputButton, gbc);

        //// From mappings

        gbc.insets = new Insets(0, 5, 3, 0);
        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(fromMappingsField, gbc);

        //// To mappings

        gbc.gridy = 1;
        this.add(toMappingsField, gbc);

        //// Settings button

        gbc.insets = new Insets(0, 7, 3, 7);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        this.add(settingsButton, gbc);

        //// Map button

        gbc.insets = new Insets(0, 7, 0, 7);
        gbc.gridy = 5;
        this.add(mapButton, gbc);
    }

    public Args getArgs() {
        return args;
    }

    private void init() {
        this.setTitle("TinierRemapper - v" + TinierRemapper.VERSION);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());

        initComponents();

        this.setSize(width, height);
        this.setPreferredSize(getSize());
        this.setResizable(false);
        this.setVisible(true);
    }
}
