package at.alex.Lobby.commands;

import at.alex.Lobby.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class specCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                if (p.hasPermission("lobby.spec.self")) {
                    if (Main.isSpec(p) == false) {

                        if (Main.isBuild(p.getUniqueId().toString())){
                            Main.leaveBuild(p);
                        }
                        Main.setSpec(p);
                        //Hide Player for all
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.hidePlayer(p);
                        }
                        for (Player players : Main.getSpec()) {
                            players.showPlayer(p);
                            p.showPlayer(players);
                        }
                        p.setAllowFlight(true);
                        setInventory(p);
                        p.sendMessage(Main.getPrefix() + "§7Spec wurde §aaktiviert§7!");
                    } else {
                        Main.remSpec(p);
                        //Show Player for all
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.showPlayer(p);
                        }
                        for (Player vanished : Main.getSpec()) {
                            p.hidePlayer(vanished);
                        }

                        p.setAllowFlight(false);
                        Main.setInv(p);
                        for (Player player : Main.getHideAll()) {
                            player.hidePlayer(p);
                        }
                        p.sendMessage(Main.getPrefix() + "§7Spec wurde §cdeaktiviert§7!");
                    }
                } else {
                    p.sendMessage(Main.getPrefix() + "§cDazu hast du keine Rechte!");
                }
            } else if (args.length == 1) {
                if (p.hasPermission("lobby.spec.other")) {
                    OfflinePlayer check = Bukkit.getOfflinePlayer(args[0]);
                    if (check.isOnline()) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (Main.isSpec(target) == false) {

                            if (Main.isBuild(target.getUniqueId().toString())){
                                Main.leaveBuild(target);
                            }
                            Main.setSpec(target);
                            //Hide Player for all
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.hidePlayer(target);
                            }
                            for (Player players : Main.getSpec()) {
                                players.showPlayer(target);
                                target.showPlayer(players);
                            }

                            p.setAllowFlight(true);
                            setInventory(target);
                            p.sendMessage(Main.getPrefix() + "§7Du hast Spec für §e" + target.getDisplayName() + " §aaktiviert§7!");
                            target.sendMessage(Main.getPrefix() + "§7Dir wurde Spec von §e"+p.getDisplayName()+" §aaktiviert§7!");
                        } else {
                            Main.remSpec(target);
                            //Show Player for all
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.showPlayer(target);
                            }
                            for (Player vanished : Main.getSpec()) {
                                target.hidePlayer(vanished);
                            }

                            p.setAllowFlight(false);
                            Main.setInv(target);
                            for (Player player : Main.getHideAll()) {
                                player.hidePlayer(p);
                            }
                            p.sendMessage(Main.getPrefix() + "§7Du hast Spec für §e" + target.getDisplayName() + " §cdeaktiviert§7!");
                            target.sendMessage(Main.getPrefix() + "§7Dir wurde Spec von §e"+p.getDisplayName()+" §cdeaktiviert§7!");
                        }
                    } else {
                        p.sendMessage(Main.getPrefix() + "§cDieser Spieler ist nicht online!");
                    }
                } else {
                    p.sendMessage(Main.getPrefix() + "§cDazu hast du keine Rechte!");
                }
            } else {
                p.sendMessage(Main.getPrefix() + "§cVerwende §6/spec §coder §6/spec <player>");
            }
        }

        return true;
    }

    private void setInventory(Player target) {
        if (target.hasPermission("lobby.adminitems.bansword")) {
            target.getInventory().setContents(Main.getInventorys().get("specBan"));
        }else if (target.hasPermission("lobby.check.self")) {
            target.getInventory().setContents(Main.getInventorys().get("specCheck"));
        }else if(target.hasPermission("lobby.adminitems.muteaxe")) {
            target.getInventory().setContents(Main.getInventorys().get("specMute"));
        }else{
            target.getInventory().setContents(Main.getInventorys().get("specNormal"));
        }
    }

}
