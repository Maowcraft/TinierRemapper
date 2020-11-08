/*
 * Copyright (c) 2020 Maow, MIT License.
 */

package maow.tinierremapper.util;

import net.fabricmc.tinyremapper.NonClassCopyMode;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Args {
    private boolean ignoreFieldDesc = false;
    private boolean propagatePrivate = false;
    private boolean removeFrames = false;
    @NotNull private Set<String> forcedPropagation = Collections.emptySet();
    private boolean ignoreConflicts = false;
    private boolean checkPackageAccess = false;
    private boolean fixPackageAccess = false;
    private boolean resolveMissing = false;
    private boolean rebuildSourceFilenames = false;
    private boolean skipLocalVariableNaming = false;
    private boolean renameInvalidLocals = false;
    @NotNull private NonClassCopyMode nonClassCopyMode = NonClassCopyMode.FIX_META_INF;

    public boolean ignoreFieldDesc() { return ignoreFieldDesc; }
    public void setIgnoreFieldDesc(boolean ignoreFieldDesc) { this.ignoreFieldDesc = ignoreFieldDesc; }

    public boolean propagatePrivate() { return propagatePrivate; }
    public void setPropagatePrivate(boolean propagatePrivate) { this.propagatePrivate = propagatePrivate; }

    public boolean removeFrames() { return removeFrames; }
    public void setRemoveFrames(boolean removeFrames) { this.removeFrames = removeFrames; }

    public @NotNull Set<String> getForcedPropagation() { return forcedPropagation; }
    public void setForcedPropagation(Path forcedPropagation) { this.forcedPropagation = splitPropagationFile(forcedPropagation); }

    public boolean ignoreConflicts() { return ignoreConflicts; }
    public void setIgnoreConflicts(boolean ignoreConflicts) { this.ignoreConflicts = ignoreConflicts; }

    public boolean checkPackageAccess() { return checkPackageAccess; }
    public void setCheckPackageAccess(boolean checkPackageAccess) { this.checkPackageAccess = checkPackageAccess; }

    public boolean fixPackageAccess() { return fixPackageAccess; }
    public void setFixPackageAccess(boolean fixPackageAccess) { this.fixPackageAccess = fixPackageAccess; }

    public boolean resolveMissing() { return resolveMissing; }
    public void setResolveMissing(boolean resolveMissing) { this.resolveMissing = resolveMissing; }

    public boolean rebuildSourceFilenames() { return rebuildSourceFilenames; }
    public void setRebuildSourceFilenames(boolean rebuildSourceFilenames) { this.rebuildSourceFilenames = rebuildSourceFilenames; }

    public boolean skipLocalVariableNaming() { return skipLocalVariableNaming; }
    public void setSkipLocalVariableNaming(boolean skipLocalVariableNaming) { this.skipLocalVariableNaming = skipLocalVariableNaming; }

    public boolean renameInvalidLocals() { return renameInvalidLocals; }
    public void setRenameInvalidLocals(boolean renameInvalidLocals) { this.renameInvalidLocals = renameInvalidLocals; }

    public @NotNull NonClassCopyMode getNonClassCopyMode() { return nonClassCopyMode; }
    public void setNonClassCopyMode(@NotNull NonClassCopyMode nonClassCopyMode) { this.nonClassCopyMode = nonClassCopyMode; }

    private Set<String> splitPropagationFile(Path file) {
        if (FileIO.readable(file)) {
            Set<String> temp = new HashSet<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(file.toFile()))) {
                while (reader.ready()) {
                    String line = reader.readLine().trim();
                    if (line.isEmpty() || line.charAt(0) == '#') continue;
                    temp.add(line);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return temp;
        }
        throw new RemapperException("Can't read forced propagation file at \"" + file.toAbsolutePath() + "\"");
    }
}
