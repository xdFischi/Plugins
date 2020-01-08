package at.alex.Lobby.listeners;

import at.alex.Lobby.Coins.CoinAPI;
import at.alex.Lobby.Scoreboard.ScoreboardAPI;
import at.alex.Lobby.Tablist.TablistAPI;
import at.alex.Lobby.main.Main;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerjoin(PlayerJoinEvent e) {

        //region Lobby
        Player p = e.getPlayer();
        if (!Main.isSpec(p)) {
            e.setJoinMessage("§7» §a" + p.getName());
        }
        p.setGameMode(GameMode.ADVENTURE);

        Main.setInv(p);
        Main.setAllP(p.getUniqueId().toString());

        if (p.hasPermission("lobby.team")) {
            Main.addTeam(p);
        }

        for (Player vanished : Main.getSpec()) {
            p.hidePlayer(vanished);
        }

        for (Player player : Main.getHideAll()) {
            player.hidePlayer(p);
        }

        if (!p.hasPermission("lobby.team")) {
            for (Player player : Main.getShowTeam()) {
                player.hidePlayer(p);
            }
        }

        for (Player silent : Main.getSilent()) {
            p.hidePlayer(silent);
            silent.hidePlayer(p);
        }
        //endregion

        //region Coins

        if (!CoinAPI.isRegistered(p.getUniqueId().toString())) {
            CoinAPI.setCoins(p.getName(), p.getUniqueId().toString(), 1000);
        }

        //endregion

        //region Tablist/Scoreboard

        ScoreboardAPI.sendScoreboard(e.getPlayer());

        new TablistAPI().addPlayer(e.getPlayer());

        //endregion


    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        //region Lobby
        Player p = e.getPlayer();
        e.setQuitMessage("§7« §c" + p.getName());

        if (Main.isBuild(p.getUniqueId().toString())) {
            Main.remBuild(p.getUniqueId().toString());
        }
        if (Main.isSpec(p)) {
            Main.remSpec(p);
        }
        if (Main.isAllP(p.getUniqueId().toString())) {
            Main.remAllP(p.getUniqueId().toString());
        }
        if (Main.isCheck(p.getUniqueId().toString())) {
            Main.remCheck(p.getUniqueId().toString());
        }
        //endregion

        //region Tablist/Scoreboard

        new TablistAPI().removePlayer(e.getPlayer());

        if (Main.rank.containsKey(p.getName())) {
            Main.rank.remove(p.getName());
        }

        //endregion
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent e) {
        e.setLeaveMessage("§7« §c" + e.getPlayer().getName());

        if (Main.rank.containsKey(e.getPlayer().getName())) {
            Main.rank.remove(e.getPlayer().getName());
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();

        if (p.getInventory().getTitle().equalsIgnoreCase("§6Rank-Changer")) {
            if (Main.rank.containsKey(p.getName())) {
                Main.rank.remove(p.getName());
            }
        }
    }

}
