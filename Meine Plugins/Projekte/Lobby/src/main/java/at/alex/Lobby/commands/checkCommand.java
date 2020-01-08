package at.alex.Lobby.commands;

import at.alex.Lobby.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class checkCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (args.length == 0) {
                if (p.hasPermission("lobby.check.self")) {
                    String uuid = p.getUniqueId().toString();
                    if (Main.isCheck(uuid) == false) {
                        Main.setCheck(uuid);
                        p.sendMessage(Main.getPrefix()+"§7Check wurde §aaktiviert§7!");
                    } else {
                        Main.remCheck(uuid);
                        p.sendMessage(Main.getPrefix()+"§7Check wurde §cdeaktiviert§7!");
                    }
                } else {
                    p.sendMessage(Main.getPrefix()+"§cDazu hast du keine Rechte!");
                }
            } else if (args.length == 1) {
                if (p.hasPermission("lobby.check.other")) {
                    try {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target.isOnline()) {
                            String uuid = target.getUniqueId().toString();
                            if (Main.isCheck(uuid) == false) {
                                Main.setCheck(uuid);
                                p.sendMessage(Main.getPrefix() + "§7Du hast Check für §e" + target.getDisplayName() + " §aaktiviert§7!");
                                target.sendMessage(Main.getPrefix() + "§7Dir wurde Check von §e" + p.getDisplayName() + " §aaktiviert§7!");
                            } else {
                                Main.remCheck(uuid);
                                p.sendMessage(Main.getPrefix() + "§7Du hast Check für §e" + target.getDisplayName() + " §cdeaktiviert§7!");
                                target.sendMessage(Main.getPrefix() + "§7Dir wurde Check von §e" + p.getDisplayName() + " §cdeaktiviert§7!");
                            }
                        }
                    }catch (Exception e){
                        p.sendMessage(Main.getPrefix()+"§cDieser Spieler ist nicht online!");
                    }
                } else {
                    p.sendMessage(Main.getPrefix()+"§cDazu hast du keine Rechte!");
                }
            } else {
                p.sendMessage(Main.getPrefix()+"§cVerwende §6/check §coder §6/check <player>");
            }
        } else {
            sender.sendMessage(Main.getPrefix() + "§cDazu musst du ein Spieler sein!");
        }

        return true;
    }

}
