package at.alex.Lobby.commands;

import at.alex.Lobby.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class groupChangeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            if (sender.hasPermission("lobby.permission.change")) {
                if (args.length == 1) {
                    if (Bukkit.getOfflinePlayer(args[0]).isOnline()) {
                        Player target = Bukkit.getPlayer(args[0]);
                        Player p = (Player)sender;


                        Main.rank.put(p.getName(), target.getName());

                        Inventory inv = p.getServer().createInventory(null, 5*9, "§6Rank-Changer");
                        ItemStack[] itemStacks = enchantCurrent(target, Main.getInventorys().get("ranks")).getContents();
                        inv.setContents(itemStacks);
                        p.openInventory(inv);
                        return true;
                    }
                    sender.sendMessage(Main.getPrefix() + "§cDieser Spieler ist nicht online!");
                    return true;
                }
                sender.sendMessage(Main.getPrefix() + "§cVerwende §6/rank <player>");
                return true;
            }
            sender.sendMessage(Main.getNoperm());
            return true;
        }
        sender.sendMessage("§cDazu musst du ein Spieler sein!");
        return true;
    }


    private Inventory enchantCurrent(Player target, ItemStack[] inv) {

        PermissionUser user = PermissionsEx.getUser(target);
        String userRank = user.getGroups()[0].getName();

        Inventory ranks = Bukkit.getServer().createInventory(null, 5*9, target.getName());
        ranks.setContents(inv);

        if (userRank.equalsIgnoreCase("owner")) {
            ItemStack owner = new ItemStack(Material.PAPER);
            ItemMeta ownermeta = owner.getItemMeta();
            ownermeta.setDisplayName("§l§4Owner");
            ownermeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
            ownermeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            owner.setItemMeta(ownermeta);
            ranks.setItem(43, owner);
            return ranks;
        }

        if (userRank.equalsIgnoreCase("superadmin")) {
            ItemStack sadmin = new ItemStack(Material.PAPER);
            ItemMeta sadminmeta = sadmin.getItemMeta();
            sadminmeta.setDisplayName("§l§4Super-Admin");
            sadminmeta.addEnchant(Enchantment.KNOCKBACK, 100, true);
            sadminmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            sadmin.setItemMeta(sadminmeta);
            ranks.setItem(41, sadmin);
            return ranks;
        }

        if (userRank.equalsIgnoreCase("admin")) {
            ItemStack radmin = new ItemStack(Material.PAPER);
            ItemMeta radminmeta = radmin.getItemMeta();
            radminmeta.setDisplayName("§l§cAdmin");
            radminmeta.addEnchant(Enchantment.KNOCKBACK, 100, true);
            radminmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            radmin.setItemMeta(radminmeta);
            ranks.setItem(39, radmin);
            return ranks;
        }

        if (userRank.equalsIgnoreCase("developer")) {
            ItemStack developer = new ItemStack(Material.PAPER);
            ItemMeta developermeta = developer.getItemMeta();
            developermeta.setDisplayName("§l§eDeveloper");
            developermeta.addEnchant(Enchantment.KNOCKBACK, 100, true);
            developermeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            developer.setItemMeta(developermeta);
            ranks.setItem(37, developer);
            return ranks;
        }

        if (userRank.equalsIgnoreCase("supermoderator")) {
            ItemStack smod = new ItemStack(Material.PAPER);
            ItemMeta smodmeta = smod.getItemMeta();
            smodmeta.setDisplayName("§l§cSuper-Moderator");
            smodmeta.addEnchant(Enchantment.KNOCKBACK, 100, true);
            smodmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            smod.setItemMeta(smodmeta);
            ranks.setItem(33, smod);
            return ranks;
        }

        if (userRank.equalsIgnoreCase("superarchitekt")) {
            ItemStack sarchi = new ItemStack(Material.PAPER);
            ItemMeta sarchimeta = sarchi.getItemMeta();
            sarchimeta.setDisplayName("§l§2Super-Architekt");
            sarchimeta.addEnchant(Enchantment.KNOCKBACK, 100, true);
            sarchimeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            sarchi.setItemMeta(sarchimeta);
            ranks.setItem(29, sarchi);
            return ranks;
        }

        if (userRank.equalsIgnoreCase("moderator")) {
            ItemStack mod = new ItemStack(Material.PAPER);
            ItemMeta modmeta = mod.getItemMeta();
            modmeta.setDisplayName("§l§9Moderator");
            modmeta.addEnchant(Enchantment.KNOCKBACK, 100, true);
            modmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            mod.setItemMeta(modmeta);
            ranks.setItem(31, mod);
            return ranks;
        }

        if (userRank.equalsIgnoreCase("architekt")) {
            ItemStack archi = new ItemStack(Material.PAPER);
            ItemMeta archimeta = archi.getItemMeta();
            archimeta.setDisplayName("§l§aArchitekt");
            archimeta.addEnchant(Enchantment.KNOCKBACK, 100, true);
            archimeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            archi.setItemMeta(archimeta);
            ranks.setItem(25, archi);
            return ranks;
        }

        if (userRank.equalsIgnoreCase("team")) {
            ItemStack team = new ItemStack(Material.PAPER);
            ItemMeta teammeta = team.getItemMeta();
            teammeta.setDisplayName("§l§3Team");
            teammeta.addEnchant(Enchantment.KNOCKBACK, 100, true);
            teammeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            team.setItemMeta(teammeta);
            ranks.setItem(23, team);
            return ranks;
        }

        if (userRank.equalsIgnoreCase("supporter")) {
            ItemStack sup = new ItemStack(Material.PAPER);
            ItemMeta supmeta = sup.getItemMeta();
            supmeta.setDisplayName("§l§bSupporter");
            supmeta.addEnchant(Enchantment.KNOCKBACK, 100, true);
            supmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            sup.setItemMeta(supmeta);
            ranks.setItem(21, sup);
            return ranks;
        }

        if (userRank.equalsIgnoreCase("testdeveloper")) {
            ItemStack tdev = new ItemStack(Material.PAPER);
            ItemMeta tdevmeta = tdev.getItemMeta();
            tdevmeta.addEnchant(Enchantment.KNOCKBACK, 100, true);
            tdevmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            tdevmeta.setDisplayName("§l§eTest-Developer");
            tdev.setItemMeta(tdevmeta);
            ranks.setItem(15, tdev);
            return ranks;
        }

        if (userRank.equalsIgnoreCase("testsupporter")) {
            ItemStack tsup = new ItemStack(Material.PAPER);
            ItemMeta tsupmeta = tsup.getItemMeta();
            tsupmeta.setDisplayName("§l§bTest-Supporter");
            tsupmeta.addEnchant(Enchantment.KNOCKBACK, 100, true);
            tsupmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            tsup.setItemMeta(tsupmeta);
            ranks.setItem(19, tsup);
            return ranks;
        }

        if (userRank.equalsIgnoreCase("streamer")) {
            ItemStack streamer = new ItemStack(Material.PAPER);
            ItemMeta streamermeta = streamer.getItemMeta();
            streamermeta.setDisplayName("§l§dStreamer");
            streamermeta.addEnchant(Enchantment.KNOCKBACK, 100, true);
            streamermeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            streamer.setItemMeta(streamermeta);
            ranks.setItem(13, streamer);
            return ranks;
        }

        if (userRank.equalsIgnoreCase("youtuber")) {
            ItemStack yt = new ItemStack(Material.PAPER);
            ItemMeta ytmeta = yt.getItemMeta();
            ytmeta.setDisplayName("§l§5Youtuber");
            ytmeta.addEnchant(Enchantment.KNOCKBACK, 100, true);
            ytmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            yt.setItemMeta(ytmeta);
            ranks.setItem(11, yt);
            return ranks;
        }

        if (userRank.equalsIgnoreCase("diamond")) {
            ItemStack diamond = new ItemStack(Material.PAPER);
            ItemMeta diamondmeta = diamond.getItemMeta();
            diamondmeta.setDisplayName("§l§3Diamond");
            diamondmeta.addEnchant(Enchantment.KNOCKBACK, 100, true);
            diamondmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            diamond.setItemMeta(diamondmeta);
            ranks.setItem(7, diamond);
            return ranks;
        }

        if (userRank.equalsIgnoreCase("platin")) {
            ItemStack platin = new ItemStack(Material.PAPER);
            ItemMeta platinmeta = platin.getItemMeta();
            platinmeta.setDisplayName("§l§fPlatin");
            platinmeta.addEnchant(Enchantment.KNOCKBACK, 100, true);
            platinmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            platin.setItemMeta(platinmeta);
            ranks.setItem(5, platin);
            return ranks;
        }

        if (userRank.equalsIgnoreCase("vip")) {
            ItemStack vip = new ItemStack(Material.PAPER);
            ItemMeta vipmeta = vip.getItemMeta();
            vipmeta.setDisplayName("§l§6VIP");
            vipmeta.addEnchant(Enchantment.KNOCKBACK, 100, true);
            vipmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            vip.setItemMeta(vipmeta);
            ranks.setItem(3, vip);
            return ranks;
        }

        if (userRank.equalsIgnoreCase("default")) {
            ItemStack player = new ItemStack(Material.PAPER);
            ItemMeta playermeta = player.getItemMeta();
            playermeta.setDisplayName("§l§7Player");
            playermeta.addEnchant(Enchantment.KNOCKBACK, 100, true);
            playermeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            player.setItemMeta(playermeta);
            ranks.setItem(1, player);
            return ranks;
        }
        return ranks;
    }




}
