/*
 * Copyright (c) 2020 Maow, MIT License.
 */

package maow.tinierremapper.util;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileIO {
    public static boolean readable(Path path, String requiredExtension) {
        if (!path.toFile().getName().endsWith("." + requiredExtension) || requiredExtension.equals("")) {
            return false;
        }
        return Files.isReadable(path) && !Files.isDirectory(path);
    }

    public static boolean readable(Path path) {
        return readable(path, "");
    }
}
