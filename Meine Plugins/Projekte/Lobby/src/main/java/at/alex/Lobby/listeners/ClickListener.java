package at.alex.Lobby.listeners;

import at.alex.Lobby.main.Main;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ClickListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (p.getItemInHand() == null || !p.getItemInHand().hasItemMeta() || !p.getItemInHand().getItemMeta().hasDisplayName()) {
                e.setCancelled(true);
                return;
            }
            if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aTeleporter")) {
                p.playSound(p.getLocation(), Sound.CLICK, 1, 1);

                ItemStack[] itemStack = Main.getInventorys().get("teleporter");
                Inventory inv = p.getServer().createInventory(null, 3 * 9, "§aTeleporter");
                inv.setContents(itemStack);
                p.openInventory(inv);
            }
        }
    }

    @EventHandler
    public void onTeleportClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta() || !e.getCurrentItem().getItemMeta().hasDisplayName()) {
            return;
        }
        if (e.getInventory().getName().equals("§aTeleporter")) {
            if (e.getCurrentItem().getItemMeta().getDisplayName() == "§6Spawn") {
                FileConfiguration cfg = Main.getPlugin().getConfig();
                World world = Bukkit.getWorld(cfg.getString("spawn.world"));

                double x = cfg.getDouble("spawn.x");
                double y = cfg.getDouble("spawn.y");
                double z = cfg.getDouble("spawn.z");
                float yaw = (float) cfg.getDouble("spawn.yaw");
                float pitch = (float) cfg.getDouble("spawn.pitch");

                Location loc = new Location(world, x, y, z, yaw, pitch);

                p.teleport(loc);
            } else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§bDev-Server") {
                FileConfiguration cfg = Main.getPlugin().getConfig();
                World world = Bukkit.getWorld(cfg.getString("game1.world"));

                double x = cfg.getDouble("game1.x");
                double y = cfg.getDouble("game1.y");
                double z = cfg.getDouble("game1.z");
                float yaw = (float) cfg.getDouble("game1.yaw");
                float pitch = (float) cfg.getDouble("game1.pitch");

                Location loc = new Location(world, x, y, z, yaw, pitch);

                p.teleport(loc);
            } else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§2Build-Server") {
                FileConfiguration cfg = Main.getPlugin().getConfig();
                World world = Bukkit.getWorld(cfg.getString("game2.world"));

                double x = cfg.getDouble("game2.x");
                double y = cfg.getDouble("game2.y");
                double z = cfg.getDouble("game2.z");
                float yaw = (float) cfg.getDouble("game2.yaw");
                float pitch = (float) cfg.getDouble("game2.pitch");

                Location loc = new Location(world, x, y, z, yaw, pitch);

                p.teleport(loc);
            }
        }
    }

    @EventHandler
    public void onFlyClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta() || !e.getCurrentItem().getItemMeta().hasDisplayName()) {
            return;
        }

        if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§9Fly")) {
            p.performCommand("fly");
        }
    }

    @EventHandler
    public void onHideInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (p.getItemInHand() == null || !p.getItemInHand().hasItemMeta() || !p.getItemInHand().getItemMeta().hasDisplayName()) {
                e.setCancelled(true);
                return;
            }

            if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Spieler verstecken")) {
                Inventory inv = p.getServer().createInventory(null, 1 * 9, "§6Spieler verstecken");
                inv.setContents(Main.getInventorys().get("spielerverstecken"));
                p.openInventory(inv);
            }
        }

    }

    @EventHandler
    public void onHideClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta() || !e.getCurrentItem().getItemMeta().hasDisplayName()) {
            return;
        }

        if (e.getInventory().getName().equalsIgnoreCase("§6Spieler verstecken")) {
            if (e.getCurrentItem().getItemMeta().getDisplayName() == "§aAlle Spieler anzeigen") {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (!Main.isSpec(all)) {
                        p.showPlayer(all);
                    }
                }
                if (Main.ishideall(p)) {
                    Main.remhideAll(p);
                }
                if (Main.isshowTeam(p)) {
                    Main.remmshowTeam(p);
                }
                p.sendMessage(Main.getPrefix() + "§aDir werden nun alle Spieler angezeigt.");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§eNur Teammitglieder anzeigen") {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    p.hidePlayer(all);
                }
                for (Player team : Main.getTeam()) {
                    if (!Main.isSpec(team)) {
                        p.showPlayer(team);
                    }
                }
                if (Main.ishideall(p)) {
                    Main.remhideAll(p);
                }
                Main.addshowTeam(p);
                p.sendMessage(Main.getPrefix() + "§aDir werden nun alle Teammitglieder angezeigt.");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§cKeine Spieler anzeigen") {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    p.hidePlayer(all);
                }
                if (Main.isshowTeam(p)) {
                    Main.remmshowTeam(p);
                }
                Main.addhideAll(p);
                p.sendMessage(Main.getPrefix() + "§aDir werden nun keine Spieler mehr angezeigt.");
            }
        }
    }

    @EventHandler
    public void onGMClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta() || !e.getCurrentItem().getItemMeta().hasDisplayName()) {
            return;
        }

        if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4GAMEMODES")) {
            Inventory inv = p.getServer().createInventory(null, 1 * 9, "§4GAMEMODES");
            inv.setContents(Main.getInventorys().get("gamemodes"));
            p.openInventory(inv);
        }
    }

    @EventHandler
    public void onGMInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta() || !e.getCurrentItem().getItemMeta().hasDisplayName()) {
            return;
        }

        if (e.getInventory().getName().equalsIgnoreCase("§4GAMEMODES")) {
            if (e.getCurrentItem().getItemMeta().getDisplayName() == "§aSURVIVAL") {
                p.performCommand("gm 0");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§aCREATIVE") {
                p.performCommand("gm 1");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§aADVENTURE") {
                p.performCommand("gm 2");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§aSPECTATOR") {
                p.performCommand("gm 3");
            }
        }
    }

    @EventHandler
    public void onSilentInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (p.getItemInHand() == null || !p.getItemInHand().hasItemMeta() || !p.getItemInHand().getItemMeta().hasDisplayName()) {
                e.setCancelled(true);
                return;
            }
            if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§5Silent-Lobby")) {
                p.playSound(p.getLocation(), Sound.EXPLODE, 1, 1);
                p.playEffect(p.getLocation(), Effect.EXPLOSION_LARGE, 1);


                if (!Main.isSilent(p)) {
                    p.sendMessage(Main.getPrefix() + "§7Silent-Lobby wurde §aaktiviert§7!");
                    Main.addSilent(p);
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        p.hidePlayer(all);
                        all.hidePlayer(p);
                    }
                } else {
                    p.sendMessage(Main.getPrefix() + "§7Silent-Lobby wurde §cdeaktiviert§7!");
                    Main.remSilent(p);
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        p.showPlayer(all);
                        all.showPlayer(p);
                    }
                }
            }
        }

    }

    @EventHandler
    public void onSpecInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (p.getItemInHand() == null || !p.getItemInHand().hasItemMeta() || !p.getItemInHand().getItemMeta().hasDisplayName()) {
                e.setCancelled(true);
                return;
            }
            //Teleporter
            if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Teleporter")) {
                p.playSound(p.getLocation(), Sound.CLICK, 1, 1);

                Inventory inv = p.getServer().createInventory(null, 4 * 9, "§6Teleporter");
                p.openInventory(inv);
                return;
            }
            //Leave
            if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cSpectator verlassen")) {
                Main.leaveSpec(p);
                return;
            }
            //Check
            if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Check")) {
                p.performCommand("check");
                return;
            }
        }

    }

    @EventHandler
    public void onSpecHit(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            if (p.getItemInHand() != null && p.getItemInHand().hasItemMeta()) {
                Player target = (Player) e.getEntity();
                if (p.getItemInHand().getItemMeta().getDisplayName().equals("§4Ban-Sword")) {
                    target.getWorld().strikeLightningEffect(target.getLocation());
                    p.performCommand("ban " + target.getName() + " 99");
                    return;
                }
                if (p.getItemInHand().getItemMeta().getDisplayName().equals("§4Mute-Axe")) {
                    p.performCommand("mute " + target.getName() + " 15");
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent e) {
        if (e.getInventory().getTitle() == "§6Rank-Changer") {
            Main.rank.remove(e.getPlayer().getName());
        }
    }


    @EventHandler
    public void onRankClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta() || !e.getCurrentItem().getItemMeta().hasDisplayName()) {
            return;
        }

        if (e.getInventory().getTitle() == "§6Rank-Changer") {
            String target = getPlayer(p).getName();
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Player") && p.hasPermission("lobby.rank.set.player")) {
                setGroup(getPlayer(p), "default");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§6VIP") && p.hasPermission("lobby.rank.set.vip")) {
                setGroup(getPlayer(p), "vip");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§fPlatin") && p.hasPermission("lobby.rank.set.platin")) {
                setGroup(getPlayer(p), "platin");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§3Diamond") && p.hasPermission("lobby.rank.set.diamond")) {
                setGroup(getPlayer(p), "diamond");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§5Youtuber") && p.hasPermission("lobby.rank.set.youtuber")) {
                setGroup(getPlayer(p), "youtuber");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§5Streamer") && p.hasPermission("lobby.rank.set.streamer")) {
                setGroup(getPlayer(p), "streamer");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§dTest-Developer") && p.hasPermission("lobby.rank.set.testdeveloper")) {
                setGroup(getPlayer(p), "testdeveloper");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§bTest-Supporter") && p.hasPermission("lobby.rank.set.testsupporter")) {
                setGroup(getPlayer(p), "testsupporter");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§bSupporter") && p.hasPermission("lobby.rank.set.supporter")) {
                setGroup(getPlayer(p), "supporter");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§3Team") && p.hasPermission("lobby.rank.set.team")) {
                setGroup(getPlayer(p), "team");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§aArchitekt") && p.hasPermission("lobby.rank.set.architekt")) {
                setGroup(getPlayer(p), "architekt");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§eSuper-Architekt") && p.hasPermission("lobby.rank.set.superarchitekt")) {
                setGroup(getPlayer(p), "superarchitekt");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§9Moderator") && p.hasPermission("lobby.rank.set.moderator")) {
                setGroup(getPlayer(p), "moderator");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§cSuper-Moderator") && p.hasPermission("lobby.rank.set.supermoderator")) {
                setGroup(getPlayer(p), "supermoderator");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§dDeveloper") && p.hasPermission("lobby.rank.set.developer")) {
                setGroup(getPlayer(p), "developer");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§cAdmin") && p.hasPermission("lobby.rank.set.admin")) {
                setGroup(getPlayer(p), "admin");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§4Super-Admin") && p.hasPermission("lobby.rank.set.superadmin")) {
                setGroup(getPlayer(p), "superadmin");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§l§4Owner") && p.hasPermission("lobby.rank.set.owner")) {
                setGroup(getPlayer(p), "Owner");
            }
            getPlayer(p).kickPlayer("§7Dein Rang wurde geändert!\nDein neuer Rang: " + e.getCurrentItem().getItemMeta().getDisplayName());
            p.sendMessage(Main.getPrefix() + "§aDu hast den Rang von §e" + target + " §aauf " + e.getCurrentItem().getItemMeta().getDisplayName() + " §agesetzt!");
            p.closeInventory();
        }
    }

    private Player getPlayer(Player p) {
        return Bukkit.getOfflinePlayer(Main.rank.get(p.getName())).getPlayer();
    }

    private void setGroup(Player target, String newGroup) {
        PermissionUser user = PermissionsEx.getUser(target);
        String[] groups = new String[1];
        groups[0] = newGroup;
        user.setGroups(groups);
    }

}












