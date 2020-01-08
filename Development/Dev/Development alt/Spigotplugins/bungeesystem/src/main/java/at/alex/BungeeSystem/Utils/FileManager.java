package at.alex.BungeeSystem.Utils;

import java.io.File;

public class FileManager {

    public static File getConfigFile() {
        return new File("plugins/bungeesystem", "config.yml");
    }

}
