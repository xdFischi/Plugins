package at.alex.Lobby.commands;

import at.alex.Lobby.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            Player p = (Player)sender;
            if (args.length == 0) {
                if (p.hasPermission("lobby.build.self")) {
                    String uuid = p.getUniqueId().toString();

                    if (Main.isBuild(uuid) == false) {
                        if (Main.isSpec(p)){
                            Main.leaveSpec(p);
                        }
                        Main.setBuild(uuid);
                        p.setGameMode(GameMode.CREATIVE);
                        p.sendMessage(Main.getPrefix()+"§7Build ist nun §aaktiviert§7!");
                        p.getInventory().clear();
                    } else {
                        Main.remBuild(uuid);
                        p.setGameMode(GameMode.ADVENTURE);
                        p.sendMessage(Main.getPrefix()+"§7Build ist nun §cdeaktiviert§7!");
                        Main.setInv(p);
                    }
                } else {
                    p.sendMessage(Main.getPrefix() + "§cDazu hast du keine Rechte!");
                }
            } else if (args.length == 1) {
                if (p.hasPermission("lobby.build.other")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target.isOnline()) {
                        String uuid = target.getUniqueId().toString();
                        if (Main.isBuild(uuid) == false) {
                            if (Main.isSpec(target)){
                                Main.leaveSpec(target);
                            }
                            Main.setBuild(uuid);
                            target.setGameMode(GameMode.CREATIVE);
                            p.sendMessage(Main.getPrefix() + "§7Du hast Build für §e" + target.getDisplayName() + " §aaktiviert§7!");
                            target.sendMessage(Main.getPrefix() + "§7Build wurde dir von §e" + p.getDisplayName() + " §aaktiviert§7!");
                            target.getInventory().clear();
                        } else {
                            Main.remBuild(uuid);
                            target.setGameMode(GameMode.ADVENTURE);
                            p.sendMessage(Main.getPrefix() + "§7Du hast Build für §e" + target.getDisplayName() + " §cdeaktiviert§7!");
                            target.sendMessage(Main.getPrefix() + "§7Build wurde dir von §e" + p.getDisplayName() + " §cdeaktiviert§7!");
                            Main.setInv(target);
                        }
                    }
                }
            } else {
                p.sendMessage(Main.getPrefix()+"§cVerwende §6/build §coder §6/build <player>§7.");
            }
        } else {
            sender.sendMessage(Main.getPrefix() + "§cDazu must du ein Spieler sein!");
        }
        return true;
    }
}
