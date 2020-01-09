package at.alex.BungeeSystem;

import at.alex.BungeeSystem.Commands.BroadCastCommand;
import at.alex.BungeeSystem.Commands.getOnlineTime;
import at.alex.BungeeSystem.Commands.hubCommand;
import at.alex.BungeeSystem.Listeners.JoinListener;
import at.alex.BungeeSystem.MySQL.MySQL;
import at.alex.BungeeSystem.PunishManager.BanCommands.*;
import at.alex.BungeeSystem.PunishManager.MuteCommands.*;
import at.alex.BungeeSystem.PunishManager.PunishListener;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Main extends Plugin {

    public static String prefix = "";
    public static String noPerm = "";
    public static String mustPlayer = "";
    public static HashMap<String, HashMap<String, String>> banIDs = new HashMap<>();
    public static HashMap<String, HashMap<String, String>> muteIDs = new HashMap<>();
    public static String kickMessage = "";
    public static String muteMessage = "";
    public static String usageMessage = "";
    public static String banMessage = "";
    public static String isBannedMessage = "";
    public static String isMutedMessage = "";
    public static String neverPlayedBefore = "";
    public static String zahlenwertMessage = "";
    public static String tempbanmessage = "";
    public static String tempmutemessage = "";
    public static String unbanmessage = "";
    public static String unmutemessage = "";
    public static String sendermutemessage = "";


    @Override
    public void onEnable() {
        readconfig();
        MySQL.connect();
        MySQL.createTable();
        startTimer();
        new JoinListener(this);
        new PunishListener(this);
        getProxy().getPluginManager().registerCommand(this, new BroadCastCommand("broadcast"));
        getProxy().getPluginManager().registerCommand(this, new getOnlineTime("onlinetime"));
        getProxy().getPluginManager().registerCommand(this, new hubCommand("hub"));
        getProxy().getPluginManager().registerCommand(this, new BanCommand("ban"));
        getProxy().getPluginManager().registerCommand(this, new TempbanCommand("tempban"));
        getProxy().getPluginManager().registerCommand(this, new UnbanCommand("unban"));
        getProxy().getPluginManager().registerCommand(this, new CheckbanCommand("checkban"));
        getProxy().getPluginManager().registerCommand(this, new BanlogCommand("banlog"));
        getProxy().getPluginManager().registerCommand(this, new MutelogCommand("mutelog"));
        getProxy().getPluginManager().registerCommand(this, new CheckmuteCommand("checkmute"));
        getProxy().getPluginManager().registerCommand(this, new MuteCommand("mute"));
        getProxy().getPluginManager().registerCommand(this, new TempmuteCommand("tempmute"));
        getProxy().getPluginManager().registerCommand(this, new UnmuteCommand("unmute"));
        getLogger().info("§aPlugin enabled!");
    }

    @Override
    public void onDisable() {
        MySQL.close();
        getLogger().info("§cPlugin disabled!");
    }

    public void startTimer() {
        getProxy().getScheduler().schedule(this, new Runnable() {
            @Override
            public void run() {
                for (ProxiedPlayer players : getProxy().getPlayers()) {
                    int hours = 0, minutes = 0;
                    ResultSet rs = MySQL.getResult("SELECT OnlineHours, OnlineMinutes FROM PlayerTimes WHERE UUID = '" + players.getUniqueId().toString() + "'");
                    try{
                        while (rs.next()) {
                            hours = rs.getInt("OnlineHours");
                            minutes = rs.getInt("OnlineMinutes");
                        }
                    }catch (SQLException e) {
                        e.printStackTrace();
                    }
                    minutes++;
                    if (minutes == 60) {
                        hours++;
                        minutes = 0;
                        MySQL.update("UPDATE PlayerTimes SET OnlineHours = '" + hours + "' WHERE UUID = '" + players.getUniqueId().toString() + "'");
                    }
                    MySQL.update("UPDATE PlayerTimes SET OnlineMinutes = '" + minutes + "' WHERE UUID = '" + players.getUniqueId().toString() + "'");
                }
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    private void readconfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }

            File file = new File(getDataFolder().getPath(), "config.yml");
            if (!file.exists()) {
                file.createNewFile();
                Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
                config.set("MySql.username", "username");
                config.set("MySql.password", "password");
                config.set("MySql.database", "database");
                config.set("MySql.host", "localhost");
                config.set("MySql.port", "3306");
                config.set("prefix", "&7[&4System&7] &b");
                config.set("noperm", "&7[&4System&7] &cDazu hast du keine Rechte!");
                config.set("mustplayer", "&7[&4System&7] &cDazu musst du ein Spieler sein!");
                config.set("kickmessage", "&cDu wurdest vom Netzwerk gebannt!\n&7Grund: %grund%");
                config.set("mutemessage", "&7[&4System&7] &cDu wurdest gemuted!\n&7[&4System&7] &7Grund: %grund%");
                config.set("sendermutemessage", "&7[&4System&7] &7Du hast §e%spieler% §7für %grund% §7gemuted!");
                config.set("usagemessage", "&7[&4System&7] §cVerwende&6 %usage%");
                config.set("banmessage", "&7[&4System&7] &7Du hast &e%player% &7für %grund% &7gebannt!");
                config.set("isbannedmessage", "&7[&4System&7] &cDieser Spieler ist bereits gebannt!");
                config.set("ismutedmessage", "&7[&4System&7] &cDieser Spieler ist bereits gemuted!");
                config.set("neverplayedbefore", "&7[&4System&7] &cDieser Spieler war noch nie Online!");
                config.set("zahlenwertmessage", "&7[&4System&7] &cDer Zahlenwert muss eine Zahl sein!");
                config.set("tempbanmessage", "&7[&4System&7] &7Du hast &e%spieler% &7für &4%grund% &6%zeit% &6%format% &7gebannt!");
                config.set("tempmutemessage", "&7[&4System&7] &7Du hast &e%spieler% &7für &4%grund% &6%zeit% &6%format% &7gemuted!");
                config.set("unbanmessage", "&7[&4System&7] &7Du hast &e%spieler% &7entbannt!");
                config.set("unmutemessage", "&7[&4System&7] &7Du hast &e%spieler% &7entmuted!");
                config.set("PunishManager.BanIDs.99.reason", "&4Ban eines Admins");
                config.set("PunishManager.BanIDs.99.seconds", "-1");
                config.set("PunishManager.BanIDs.99.permission", "bungeesystem.punishmanager.ban.99");
                config.set("PunishManager.MuteIDs.99.reason", "&4Mute eines Admins");
                config.set("PunishManager.MuteIDs.99.seconds", "-1");
                config.set("PunishManager.MuteIDs.99.permission", "bungeesystem.punishmanager.mute.99");
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
            }
            Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

            MySQL.username = config.getString("MySql.username");
            MySQL.password = config.getString("MySql.password");
            MySQL.database = config.getString("MySql.database");
            MySQL.host = config.getString("MySql.host");
            MySQL.port = config.getString("MySql.port");
            prefix = ChatColor.translateAlternateColorCodes('&', config.getString("prefix"));
            noPerm = ChatColor.translateAlternateColorCodes('&', config.getString("noperm"));
            mustPlayer = ChatColor.translateAlternateColorCodes('&', config.getString("mustplayer"));
            kickMessage = ChatColor.translateAlternateColorCodes('&', config.getString("kickmessage"));
            muteMessage = ChatColor.translateAlternateColorCodes('&', config.getString("mutemessage"));
            usageMessage = ChatColor.translateAlternateColorCodes('&', config.getString("usagemessage"));
            banMessage = ChatColor.translateAlternateColorCodes('&', config.getString("banmessage"));
            isBannedMessage = ChatColor.translateAlternateColorCodes('&', config.getString("isbannedmessage"));
            neverPlayedBefore = ChatColor.translateAlternateColorCodes('&', config.getString("neverplayedbefore"));
            zahlenwertMessage = ChatColor.translateAlternateColorCodes('&', config.getString("zahlenwertmessage"));
            tempbanmessage = ChatColor.translateAlternateColorCodes('&', config.getString("tempbanmessage"));
            unbanmessage = ChatColor.translateAlternateColorCodes('&', config.getString("unbanmessage"));
            unmutemessage = ChatColor.translateAlternateColorCodes('&', config.getString("unmutemessage"));
            isMutedMessage = ChatColor.translateAlternateColorCodes('&', config.getString("ismutedmessage"));
            tempmutemessage = ChatColor.translateAlternateColorCodes('&', config.getString("tempmutemessage"));
            sendermutemessage = ChatColor.translateAlternateColorCodes('&', config.getString("sendermutemessage"));

            Collection<String> bankeys = config.getSection("PunishManager.BanIDs").getKeys();

            for (String key : bankeys) {
                HashMap<String, String> infos = new HashMap<>();
                Collection<String> idkeys = config.getSection("PunishManager.BanIDs." + key).getKeys();
                for (String idkey : idkeys) {
                    infos.put(idkey, ChatColor.translateAlternateColorCodes('&', config.getString("PunishManager.BanIDs." + key + "." + idkey)));
                }
                banIDs.put(key, infos);
            }

            Collection<String> mutekeys = config.getSection("PunishManager.MuteIDs").getKeys();

            for (String key : mutekeys) {
                HashMap<String, String> infos = new HashMap<>();
                Collection<String> idkeys = config.getSection("PunishManager.MuteIDs." + key).getKeys();
                for (String idkey : idkeys) {
                    infos.put(idkey, ChatColor.translateAlternateColorCodes('&', config.getString("PunishManager.MuteIDs." + key + "." + idkey)));
                }
                muteIDs.put(key, infos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
