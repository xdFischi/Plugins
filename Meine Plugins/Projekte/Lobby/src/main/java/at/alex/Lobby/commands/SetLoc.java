package at.alex.Lobby.commands;

import at.alex.Lobby.main.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetLoc implements CommandExecutor {

    private Main plugin;

    public SetLoc(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            //SetSpawn
            if (p.hasPermission("lobby.setlocations")) {
                if (cmd.getName().equalsIgnoreCase("setspawn")) {
                    if (args.length == 0) {
                        FileConfiguration cfg = Main.getPlugin().getConfig();
                        Location loc = p.getLocation();

                        cfg.set("spawn.world", loc.getWorld().getName());
                        cfg.set("spawn.x", loc.getX());
                        cfg.set("spawn.y", loc.getY());
                        cfg.set("spawn.z", loc.getZ());
                        cfg.set("spawn.yaw", loc.getYaw());
                        cfg.set("spawn.pitch", loc.getPitch());
                        Main.getPlugin().saveConfig();

                        p.sendMessage(Main.getPrefix() + "§aDu hast den §6Spawn §agesetzt!");
                    } else {
                        p.sendMessage(Main.getPrefix() + "§cVerwende §e/setspawn");
                    }
                } else if (cmd.getName().equalsIgnoreCase("setgame")) {
                    if (args.length == 1) {
                        if (Integer.parseInt(args[0]) < 3 && Integer.parseInt(args[0]) > 0) {
                            String gameName = "game" + args[0];

                            FileConfiguration cfg = Main.getPlugin().getConfig();
                            Location loc = p.getLocation();

                            cfg.set(gameName + ".world", loc.getWorld().getName());
                            cfg.set(gameName + ".x", loc.getX());
                            cfg.set(gameName + ".y", loc.getY());
                            cfg.set(gameName + ".z", loc.getZ());
                            cfg.set(gameName + ".yaw", loc.getYaw());
                            cfg.set(gameName + ".pitch", loc.getPitch());
                            Main.getPlugin().saveConfig();

                            p.sendMessage(Main.getPrefix() + "§aDu hast den Spawnpunkt für §6" + gameName + " §agesetzt!");
                        } else {
                            p.sendMessage(Main.getPrefix() + "§cVerwende §e/setgame <1-2>");
                        }
                    } else {
                        p.sendMessage(Main.getPrefix() + "§cVerwende §e/setgame <1-2>");
                    }
                }
            }else {
                p.sendMessage(Main.getNoperm());
            }
        } else {
            sender.sendMessage(Main.getPrefix() + "§cDazu musst du ein Spieler sein!");
        }

        return true;
    }
}
