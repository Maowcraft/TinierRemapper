/*
 * Copyright (c) 2020 Maow, MIT License.
 */

import maow.tinierremapper.TinierRemapper;

import java.nio.file.Paths;

public class Test {
    public static void main(String[] args) {
        TinierRemapper.remap(
                Paths.get("minecraft.jar"),
                Paths.get("minecraft-remapped.jar"),
                Paths.get("intermediary.tiny"),
                "official",
                "intermediary"
        );

        TinierRemapper.remap(
                Paths.get("minecraft-remapped.jar"),
                Paths.get("minecraft-named.jar"),
                Paths.get("yarn.tiny"),
                "intermediary",
                "named"
        );
    }
}
