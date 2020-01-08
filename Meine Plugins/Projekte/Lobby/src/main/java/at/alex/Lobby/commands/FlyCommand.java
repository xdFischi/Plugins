package at.alex.Lobby.commands;

import at.alex.Lobby.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                if (p.hasPermission("lobby.fly.self")) {
                    if (p.getAllowFlight() == false) {
                        p.setAllowFlight(true);
                        p.sendMessage(Main.getPrefix()+"§7Fly wurde §aaktiviert§7!");
                    } else {
                        p.setAllowFlight(false);
                        p.sendMessage(Main.getPrefix()+"§7Fly wurde §cdeaktiviert§7!");
                    }
                } else {
                    p.sendMessage(Main.getPrefix()+"§cDazu hast du keine Rechte!");
                }
            } else if (args.length == 1) {
                if (p.hasPermission("lobby.fly.other")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target.isOnline()) {
                        if (target.getAllowFlight() == false) {
                            target.setAllowFlight(true);
                            p.sendMessage(Main.getPrefix() + "§7Du hast Fly für§e " + target.getDisplayName() + " §aaktiviert§7!");
                            target.sendMessage(Main.getPrefix() + "§7Fly wurde dir von§e " + p.getDisplayName() + " §aaktiviert§7!");
                        } else {
                            target.setAllowFlight(false);
                            p.sendMessage(Main.getPrefix() + "§7Du hast Fly für§e " + target.getDisplayName() + " §cdeaktiviert§7!");
                            target.sendMessage(Main.getPrefix() + "§7Fly wurde dir von§e " + p.getDisplayName() + " §cdeaktiviert§7!");
                        }
                    }
                } else {
                    p.sendMessage(Main.getPrefix()+"§cDazu hast du keine Rechte!");
                }
            }else {
                p.sendMessage(Main.getPrefix()+"§cVerwende §6/fly §coder §6/fly <player>§c.");
            }
        }else {
            sender.sendMessage(Main.getPrefix()+"§cDazu musst du ein Spieler sein!");
        }
        return true;
    }

}
