package at.alex.BungeeSystem.PunishManager;

import at.alex.BungeeSystem.Main;
import at.alex.BungeeSystem.MySQL.MySQL;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MuteManager {

        /*
    Syntax: Spielername, UUID, Ende, Grund, GebanntVon, GebanntAm
     */

    public static void mute(String playername, String uuid, long seconds, String grund, String mutername, long gemutetam) {
        long end = 0;
        if (seconds == -1) {
            end = -1;
        } else {
            long current = System.currentTimeMillis();
            long millis = seconds * 1000;
            end = current + millis;
        }
        try {
            PreparedStatement statement = MySQL.con.prepareStatement("INSERT INTO MutedPlayers (Spielername, UUID, Ende, Grund, GemutedVon, GemutedAm) VALUES (?,?,?,?,?,?)");
            statement.setString(1, playername);
            statement.setString(2, uuid);
            statement.setLong(3, end);
            statement.setString(4, grund);
            statement.setString(5, mutername);
            statement.setLong(6, gemutetam);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ProxiedPlayer pp = ProxyServer.getInstance().getPlayer(uuid);
        if (pp != null) {
            if (pp.isConnected()) {
                String message = Main.kickMessage.replace("%grund%", grund);
                pp.sendMessage(message);
            }
        }
    }

    public static void unMute(String uuid, long gemutedam, long gemutedbis, String grund, String gemutedvon, String unmutegrund, String unmuter) {
        try {
            PreparedStatement statement = MySQL.con.prepareStatement("INSERT INTO MuteLogs (UUID, GemutedAm, GemutedBis, Grund, GemutedVon, UnmuteGrund, UnmutedVon) VALUES(?,?,?,?,?,?,?)");
            statement.setString(1,uuid);
            statement.setLong(2, gemutedam);
            statement.setLong(3, gemutedbis);
            statement.setString(4, grund);
            statement.setString(5, gemutedvon);
            statement.setString(6, unmutegrund);
            statement.setString(7, unmuter);
            statement.executeUpdate();
            MySQL.update("DELETE FROM MutedPlayers WHERE UUID = '" + uuid + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isMute(String uuid) {
        ResultSet rs = MySQL.getResult("SELECT Spielername FROM MutedPlayers WHERE UUID = '" + uuid + "'");
        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getOfflineUUID(String name) {
        ResultSet rs = MySQL.getResult("SELECT UUID FROM PlayerTimes WHERE Spielername = '" + name + "'");
        try {
            while (rs.next()) {
                return rs.getString("UUID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getOfflineName(String uuid) {
        ResultSet rs = MySQL.getResult("SELECT Spielername FROM PlayerTimes WHERE UUID = '" + uuid + "'");
        try {
            while (rs.next()) {
                return rs.getString("Spielername");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getReason(String uuid) {
        ResultSet rs = MySQL.getResult("SELECT Grund FROM MutedPlayers WHERE UUID = '" + uuid + "'");
        try {
            while (rs.next()) {
                return rs.getString("Grund");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Long getEnd(String uuid) {
        ResultSet rs = MySQL.getResult("SELECT Ende FROM MutedPlayers WHERE UUID = '" + uuid + "'");
        try {
            while (rs.next()) {
                return rs.getLong("Ende");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMuter(String uuid) {
        ResultSet rs = MySQL.getResult("SELECT GemutedVon FROM MutedPlayers WHERE UUID = '" + uuid + "'");
        try {
            while (rs.next()) {
                return rs.getString("GemutedVon");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getRemainingTime(String uuid) {
        long end = getEnd(uuid);
        if (end == -1) {
            return "§4PERMANENT";
        }
        Date remainingDate = new Date(end);

        return remainingDate.toLocaleString();
    }

    public static void sendMuteIDs(CommandSender p) {
        p.sendMessage(Main.prefix + "§7--------------- §4Mute-IDs §7---------------");
        for (Map.Entry<String, HashMap<String, String>> map : Main.muteIDs.entrySet()) {
            p.sendMessage("§8- §c" + map.getKey() + " §7| " + map.getValue().get("reason"));
        }
        p.sendMessage(Main.prefix + "§7----------------------------------------");
    }

    public static Long getIdSeconds(String id) {
        return Long.parseLong(Main.muteIDs.get(id).get("seconds"));
    }

    public static String getIdReason(String id) {
        return Main.muteIDs.get(id).get("reason");
    }

    public static String getIdPermission(String id) {
        return Main.muteIDs.get(id).get("permission");
    }

    public static boolean isRegistred(String name) {
        ResultSet rs = MySQL.getResult("SELECT UUID FROM PlayerTimes WHERE Spielername='" + name + "'");
        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Long getMuteDay(String uuid) {
        ResultSet rs = MySQL.getResult("SELECT GemutedAm FROM MutedPlayers WHERE UUID = '" + uuid + "'");
        try {
            while (rs.next()) {
                return rs.getLong("GemutedAm");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    ---------- Mute-Log ----------
    Name + UUID
    Erstellt am:
    Aufgehoben am: -> wenn aufgehoben
    Aufhebungsgrund: -> wenn aufgehoben
    Mutegrund:
    Gemuted von:
    Entmuted von:
    -----------------------------

    UUID VARCHAR(100), GebanntAm VARCHAR(100), GebanntBis LONG, Grund VARCHAR(100), GebanntVon VARCHAR(100), UnbanGrund VARCHAR(100), UnbannedVon VARCHAR(100)
     */

    public static HashMap<Integer, HashMap<String, String>> getLogs(String uuid) {
        HashMap<Integer, HashMap<String, String>> logs = new HashMap<>();
        int count = 0;
        ResultSet rs = MySQL.getResult("SELECT * FROM MuteLogs WHERE UUID = '" + uuid + "'");
        try {
            while (rs.next()) {
                count++;
                HashMap<String, String> log = new HashMap<>();
                log.put("gemutedam", rs.getString("GemutedAm"));
                log.put("gemutedbis", rs.getString("GemutedBis"));
                log.put("grund", rs.getString("Grund"));
                log.put("gemutedvon", rs.getString("GemutedVon"));
                log.put("unmutegrund", rs.getString("UnmuteGrund"));
                log.put("unmutedvon", rs.getString("UnmutedVon"));
                logs.put(count, log);
            }
        } catch (SQLException e) {

        }
        return logs;
    }

}
