package at.alex.BungeeSystem.PunishManager;

import at.alex.BungeeSystem.Main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class PunishListener implements Listener {

    public PunishListener(Plugin plugin) {
        ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler
    public void onChatMessage(ChatEvent e) {
        if (e.getSender() instanceof ProxiedPlayer) {
            if (MuteManager.isMute(((ProxiedPlayer) e.getSender()).getUniqueId().toString())) {

                if (!e.isCommand()) {
                    long current = System.currentTimeMillis();
                    long end = MuteManager.getEnd(((ProxiedPlayer) e.getSender()).getUniqueId().toString());
                    if (current > end || end == -1) {
                        MuteManager.unMute(((ProxiedPlayer) e.getSender()).getUniqueId().toString(),MuteManager.getMuteDay(((ProxiedPlayer) e.getSender()).getUniqueId().toString()),System.currentTimeMillis(),MuteManager.getReason(((ProxiedPlayer) e.getSender()).getUniqueId().toString()) ,MuteManager.getMuter(((ProxiedPlayer) e.getSender()).getUniqueId().toString()),"Auslauf er Zeit","CONSOLE");
                    } else {
                        ((ProxiedPlayer) e.getSender()).sendMessage(Main.prefix + "§cDu bist gemuted!\n" +
                                "§eGrund: " + MuteManager.getReason(((ProxiedPlayer) e.getSender()).getUniqueId().toString()) +
                                Main.prefix + "§eEnde: §9" + MuteManager.getRemainingTime(((ProxiedPlayer) e.getSender()).getUniqueId().toString()));
                    }
                    e.setCancelled(true);
                }else{
                    e.setCancelled(false);
                }
            }
        }
    }

    @EventHandler
    public void onLogin(ServerConnectEvent e) {
        if (BanManager.isBanned(e.getPlayer().getUniqueId().toString())) {
            long current = System.currentTimeMillis();
            long end = BanManager.getEnd(e.getPlayer().getUniqueId().toString());
            if (current < end || end == -1) {
                e.getPlayer().disconnect("§cDu wurdest von Netzwerk gebannt!\n" +
                        "§7Grund: " + BanManager.getReason(e.getPlayer().getUniqueId().toString()) +
                        "\n" +
                        "§7Ende: §9" + BanManager.getRemainingTime(e.getPlayer().getUniqueId().toString()));
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onJoin(ServerConnectedEvent e) {
        if (BanManager.isBanned(e.getPlayer().getUniqueId().toString())) {
            BanManager.unBan(e.getPlayer().getUniqueId().toString(), BanManager.getBanDay(e.getPlayer().getUniqueId().toString()), System.currentTimeMillis(), BanManager.getReason(e.getPlayer().getUniqueId().toString()), BanManager.getBanner(e.getPlayer().getUniqueId().toString()), "Ablauf der Zeit", "CONSOLE");
        }
    }

}
