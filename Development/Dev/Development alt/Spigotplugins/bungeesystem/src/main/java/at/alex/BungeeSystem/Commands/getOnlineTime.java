package at.alex.BungeeSystem.Commands;

import at.alex.BungeeSystem.Main;
import at.alex.BungeeSystem.MySQL.MySQL;
import at.alex.BungeeSystem.OnlineTime.OnlineTimeAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.ResultSet;
import java.sql.SQLException;

public class getOnlineTime extends Command {


    public getOnlineTime(String name) {
        super(name, "bungeesystem.getonlinetime", new String[]{"ontime"});
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1){
            ProxiedPlayer ps = ProxyServer.getInstance().getPlayer(args[0]);

            if (ps == null) {
                sender.sendMessage(Main.prefix + "§cDieser Spieler war noch nie zuvor online!");
                return;
            }

            if (OnlineTimeAPI.isRegistered(ps.getUniqueId().toString())) {
                ResultSet rs = MySQL.getResult("SELECT OnlineHours, OnlineMinutes FROM PlayerTimes WHERE UUID = '" + ps.getUniqueId().toString() + "'");
                try {
                    while (rs.next()) {
                        sender.sendMessage(Main.prefix + "§7Der Spieler §e" + ps.getDisplayName() + " §7spielt seit §e" + rs.getString("OnlineHours") + " Stunden §7und §e" + rs.getString("OnlineMinutes") + " Minuten§7.");
                        return;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            sender.sendMessage(Main.prefix + "§cDieser Spieler hat den Server noch nie betreten!");
            return;

        }else if (args.length == 0) {


            if (sender instanceof ProxiedPlayer) {
                ProxiedPlayer ps = (ProxiedPlayer)sender;
                if (OnlineTimeAPI.isRegistered(ps.getUniqueId().toString())) {
                    ResultSet rs = MySQL.getResult("SELECT OnlineHours, OnlineMinutes FROM PlayerTimes WHERE UUID = '" + ps.getUniqueId().toString() + "'");
                    try {
                        while (rs.next()) {
                            sender.sendMessage(Main.prefix + "§7Der Spieler §e" + ps.getDisplayName() + " §7spielt seit §e" + rs.getString("OnlineHours") + " Stunden §7und §e" + rs.getString("OnlineMinutes") + " Minuten§7.");
                            return;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
            sender.sendMessage(Main.prefix + "§cDazu musst du ein Spieler sein!");
            return;
        }
        sender.sendMessage(Main.prefix + "§cVerwende §6/onlinetime §coder §6/onlinetime <player>");
        return;
    }
}
