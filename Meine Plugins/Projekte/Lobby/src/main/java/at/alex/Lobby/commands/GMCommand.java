package at.alex.Lobby.commands;

import at.alex.Lobby.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GMCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (args.length == 1) {
                if (p.hasPermission("lobby.gamemode.self")) {
                    if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("SURVIVAL")) {
                        p.setGameMode(GameMode.SURVIVAL);
                        p.sendMessage(Main.getPrefix()+"§aDein Spielmodus wurde auf §eSURVIVAL §agesetzt!");
                    } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("CREATIVE")) {
                        p.setGameMode(GameMode.CREATIVE);
                        p.sendMessage(Main.getPrefix()+"§aDein Spielmodus wurde auf §eCREATIVE §agesetzt!");
                    } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("ADVENTURE")) {
                        p.setGameMode(GameMode.ADVENTURE);
                        p.sendMessage(Main.getPrefix()+"§aDein Spielmodus wurde auf §eADVENTURE §agesetzt!");
                    } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("SPECTATOR")) {
                        p.setGameMode(GameMode.SPECTATOR);
                        p.sendMessage(Main.getPrefix()+"§aDein Spielmodus wurde auf §eSPECTATOR §agesetzt!");
                    } else {
                        p.sendMessage(Main.getPrefix()+"§cVerwende §6/gm <0-3>");
                    }
                } else {
                    p.sendMessage(Main.getPrefix()+"§cDazu hast du keine Rechte!");
                }
            } else if (args.length == 2) {
                if (p.hasPermission("lobby.gamemode.other")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target.isOnline()) {
                        if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("SURVIVAL")) {
                            target.setGameMode(GameMode.SURVIVAL);
                            target.sendMessage(Main.getPrefix()+"§aDein Spielmodus wurde von §e"+ p.getDisplayName() +" §aauf §eSURVIVAL §agesetzt!");
                            p.sendMessage(Main.getPrefix()+"§aDu hast den Spielmodus von §e"+target.getDisplayName()+" §aauf §eSURVIVAL §agesetzt!");
                        } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("CREATIVE")) {
                            target.setGameMode(GameMode.CREATIVE);
                            target.sendMessage(Main.getPrefix()+"§aDein Spielmodus wurde von §e"+ p.getDisplayName() +" §aauf §eCREATIVE §agesetzt!");
                            p.sendMessage(Main.getPrefix()+"§aDu hast den Spielmodus von §e"+target.getDisplayName()+" §aauf §ECREATIVE §agesetzt!");
                        } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("ADVENTURE")) {
                            target.setGameMode(GameMode.ADVENTURE);
                            target.sendMessage(Main.getPrefix()+"§aDein Spielmodus wurde von §e"+ p.getDisplayName() +" §aauf §eADVENTURE §agesetzt!");
                            p.sendMessage(Main.getPrefix()+"§aDu hast den Spielmodus von §e"+target.getDisplayName()+" §aauf §eADVENTURE §agesetzt!");
                        } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("SPECTATOR")) {
                            target.setGameMode(GameMode.SPECTATOR);
                            target.sendMessage(Main.getPrefix()+"§aDein Spielmodus wurde von §e"+ p.getDisplayName() +" §aauf §eSPECTATOR §agesetzt!");
                            p.sendMessage(Main.getPrefix()+"§aDu hast den Spielmodus von §e"+target.getDisplayName()+" §aauf §eSPECTATOR §agesetzt!");
                        } else {
                            p.sendMessage(Main.getPrefix()+"§cVerwende §6/gm <player> <0-3>");
                        }
                    } else {
                        p.sendMessage(Main.getPrefix()+"§cDieser Spieler ist nicht online!");
                    }
                } else {
                    p.sendMessage(Main.getPrefix()+"§cDazu hast du keine Rechte!");
                }
            }  else {
                p.sendMessage(Main.getPrefix()+"§cVerwende §6/gm <0-3> §coder §7/gm <player> <0-3>");
            }
        }

        return true;
    }

}
