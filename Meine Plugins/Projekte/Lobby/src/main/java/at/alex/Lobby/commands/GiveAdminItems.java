package at.alex.Lobby.commands;

import at.alex.Lobby.main.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GiveAdminItems implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        String adminItems = "§7-----§4Admin§8-§4Items§7-----\n" +
                "§ebansword\n" +
                "§emuteaxe\n" +
                "§7---------------------";

        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("bansword")) {
                    if (p.hasPermission("lobby.adminitems.bansword")) {
                        ItemStack ban = new ItemStack(Material.DIAMOND_SWORD);
                        ItemMeta banmeta = ban.getItemMeta();
                        banmeta.setDisplayName("§4Ban-Sword");
                        banmeta.spigot().setUnbreakable(true);
                        ban.setItemMeta(banmeta);
                        p.getInventory().setItem(2, ban);
                        return true;
                    }
                    p.sendMessage(Main.getNoperm());
                    return true;
                }

                if (args[0].equalsIgnoreCase("muteaxe")) {
                    if (p.hasPermission("lobby.adminitems.muteaxe")) {
                        ItemStack mute = new ItemStack(Material.DIAMOND_AXE);
                        ItemMeta mutemeta = mute.getItemMeta();
                        mutemeta.setDisplayName("§4Mute-Axe");
                        mutemeta.spigot().setUnbreakable(true);
                        mute.setItemMeta(mutemeta);
                        p.getInventory().setItem(2, mute);
                        return true;
                    }
                    p.sendMessage(Main.getNoperm());
                    return true;
                }

                if (args[0].equalsIgnoreCase("clear")) {
                    if (p.hasPermission("lobby.adminitems.clear")) {
                        p.getInventory().setItem(2, null);
                        return true;
                    }
                    p.sendMessage(Main.getNoperm());
                    return true;
                }

                if (args[0].equalsIgnoreCase("list")) {
                    if (p.hasPermission("lobby.adminitems.list")) {
                        p.sendMessage(adminItems);
                        return true;
                    }
                    p.sendMessage(Main.getNoperm());
                    return true;
                }
            }
            p.sendMessage(Main.getPrefix() + "§cVerwende §6/adminitems <item>§7|§6clear§7|§6list");
            return true;
        }
        sender.sendMessage("§cDazu musst du ein Spieler sein!");
        return true;
    }

}
