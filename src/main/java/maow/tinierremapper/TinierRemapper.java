/*
 * Copyright (c) 2020 Maow, MIT License.
 */

package maow.tinierremapper;

import maow.tinierremapper.gui.RemapperWindow;
import maow.tinierremapper.util.Args;
import maow.tinierremapper.util.FileIO;
import maow.tinierremapper.util.RemapperException;
import net.fabricmc.tinyremapper.IMappingProvider;
import net.fabricmc.tinyremapper.OutputConsumerPath;
import net.fabricmc.tinyremapper.TinyRemapper;
import net.fabricmc.tinyremapper.TinyUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;

public class TinierRemapper {
    public static final String VERSION = "1.0.0";

    private static final String ERROR_UNREADABLE = "%s located at \"%s\" was unable to be read\nFile is missing, corrupted, or has the wrong file extension (requires '%s')";
    private static final String ERROR_EQUAL_MAPPINGS = "From mappings (%s) should not equal To mappings (%s)";

    private static RemapperWindow remapperWindow;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        remapperWindow = new RemapperWindow(340, 200);
    }

    public static void remap(@NotNull Args args, Path input, Path output, Path mappings, String fromMappings, String toMappings) {
        // Error handling

        if (!FileIO.readable(input, "jar")) {
            // Checks if the input jar is readable and ends with '.jar'
            throw new RemapperException(String.format(ERROR_UNREADABLE, "Input jar", input.toAbsolutePath(), ".jar"));
        }
        if (!FileIO.readable(mappings, "tiny")) {
            // Checks if the mappings file is readable and ends with '.tiny'
            throw new RemapperException(String.format(ERROR_UNREADABLE, "Mappings file", mappings.toAbsolutePath(), ".tiny"));
        }
        if (fromMappings.equals(toMappings)) {
            // Checks if the to and from mappings are equal or not.
            throw new RemapperException(String.format(ERROR_EQUAL_MAPPINGS, fromMappings, toMappings));
        }

        // Mapping

        final IMappingProvider provider = TinyUtils.createTinyMappingProvider(mappings, fromMappings, toMappings);
        TinyRemapper remapper = TinyRemapper.newRemapper()
                .withMappings(provider)
                .ignoreFieldDesc(args.ignoreFieldDesc())
                .propagatePrivate(args.propagatePrivate())
                .removeFrames(args.removeFrames())
                .withForcedPropagation(args.getForcedPropagation())
                .ignoreConflicts(args.ignoreConflicts())
                .checkPackageAccess(args.checkPackageAccess())
                .fixPackageAccess(args.fixPackageAccess())
                .resolveMissing(args.resolveMissing())
                .rebuildSourceFilenames(args.rebuildSourceFilenames())
                .skipLocalVariableMapping(args.skipLocalVariableNaming())
                .renameInvalidLocals(args.renameInvalidLocals())
                .build();
        try (OutputConsumerPath consumer = new OutputConsumerPath.Builder(output).build()) {
            consumer.addNonClassFiles(input, args.getNonClassCopyMode(), remapper);

            remapper.readInputs(input);

            remapper.apply(consumer);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            remapper.finish();
        }
    }

    public static void remap(Path input, Path output, Path mappings, String fromMappings, String toMappings) {
        remap(new Args(), input, output, mappings, fromMappings, toMappings);
    }

    public static RemapperWindow getWindow() {
        return remapperWindow;
    }
}
