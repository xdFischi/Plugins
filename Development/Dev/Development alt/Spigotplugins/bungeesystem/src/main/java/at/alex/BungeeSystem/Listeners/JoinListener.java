package at.alex.BungeeSystem.Listeners;

import at.alex.BungeeSystem.MySQL.MySQL;
import at.alex.BungeeSystem.OnlineTime.OnlineTimeAPI;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class JoinListener implements Listener {

    public JoinListener(Plugin plugin) {
        ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler
    public void onPlayerJoin(ServerConnectedEvent e) {

        ProxiedPlayer p = e.getPlayer();

        if (!OnlineTimeAPI.isRegistered(p.getUniqueId().toString())) {
            MySQL.update("INSERT INTO PlayerTimes (Spielername, UUID, OnlineHours, OnlineMinutes) VALUES('" + p.getName() + "','" + p.getUniqueId().toString() + "','" + "0" + "','" + "0" + "')");
        } else {
            MySQL.update("UPDATE PlayerTimes SET Spielername = '" + p.getName() + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
        }

    }

}
