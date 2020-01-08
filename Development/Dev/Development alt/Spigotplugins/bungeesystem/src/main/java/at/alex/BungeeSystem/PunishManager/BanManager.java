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

public class BanManager {

    /*
    Syntax: Spielername, UUID, Ende, Grund, GebanntVon, GebanntAm
     */

    public static void ban(String playername, String uuid, long seconds, String grund, String bannername, long gebanntam) {
        long end = 0;
        if (seconds == -1) {
            end = -1;
        } else {
            long current = System.currentTimeMillis();
            long millis = seconds * 1000;
            end = current + millis;
        }
        try {
            PreparedStatement statement = MySQL.con.prepareStatement("INSERT INTO BannedPlayers (Spielername, UUID, Ende, Grund, GebanntVon, GebanntAm) VALUES (?,?,?,?,?,?)");
            statement.setString(1, playername);
            statement.setString(2, uuid);
            statement.setLong(3, end);
            statement.setString(4, grund);
            statement.setString(5, bannername);
            statement.setLong(6, gebanntam);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ProxiedPlayer pp = ProxyServer.getInstance().getPlayer(playername);
        if (pp != null) {
            if (pp.isConnected()) {
                String message = Main.kickMessage.replace("%grund%", grund);
                pp.disconnect(new TextComponent(message));
            }
        } else System.out.println("ban null");
    }

    public static void unBan(String uuid, long gebanntam, long gebanntbis, String grund, String gebanntvon, String unbangrund, String unbanner) {
        try {
            PreparedStatement statement = MySQL.con.prepareStatement("INSERT INTO BanLogs (UUID, GebanntAm, GebanntBis, Grund, GebanntVon, UnbanGrund, UnbannedVon) VALUES(?,?,?,?,?,?,?)");
            statement.setString(1,uuid);
            statement.setLong(2, gebanntam);
            statement.setLong(3, gebanntbis);
            statement.setString(4, grund);
            statement.setString(5, gebanntvon);
            statement.setString(6, unbangrund);
            statement.setString(7, unbanner);
            statement.executeUpdate();
            MySQL.update("DELETE FROM BannedPlayers WHERE UUID = '" + uuid + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isBanned(String uuid) {
        ResultSet rs = MySQL.getResult("SELECT Spielername FROM BannedPlayers WHERE UUID = '" + uuid + "'");
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
        ResultSet rs = MySQL.getResult("SELECT Grund FROM BannedPlayers WHERE UUID = '" + uuid + "'");
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
        ResultSet rs = MySQL.getResult("SELECT Ende FROM BannedPlayers WHERE UUID = '" + uuid + "'");
        try {
            while (rs.next()) {
                return rs.getLong("Ende");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getBanner(String uuid) {
        ResultSet rs = MySQL.getResult("SELECT GebanntVon FROM BannedPlayers WHERE UUID = '" + uuid + "'");
        try {
            while (rs.next()) {
                return rs.getString("GebanntVon");
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

    public static void sendBanIDs(CommandSender p) {
        p.sendMessage(Main.prefix + "§7--------------- §4Ban-IDs §7---------------");
        for (Map.Entry<String, HashMap<String, String>> map : Main.banIDs.entrySet()) {
            p.sendMessage("§8- §c" + map.getKey() + " §7| " + map.getValue().get("reason"));
        }
        p.sendMessage(Main.prefix + "§7---------------------------------------");
    }

    public static Long getIdSeconds(String id) {
        return Long.parseLong(Main.banIDs.get(id).get("seconds"));
    }

    public static String getIdReason(String id) {
        return Main.banIDs.get(id).get("reason");
    }

    public static String getIdPermission(String id) {
        return Main.banIDs.get(id).get("permission");
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

    public static Long getBanDay(String uuid) {
        ResultSet rs = MySQL.getResult("SELECT GebanntAm FROM BannedPlayers WHERE UUID = '" + uuid + "'");
        try {
            while (rs.next()) {
                return rs.getLong("GebanntAm");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    ---------- Ban-Log ----------
    Name + UUID
    Erstellt am:
    Aufgehoben am: -> wenn aufgehoben
    Aufhebungsgrund: -> wenn aufgehoben
    Bangrund:
    Gebannt von:
    Unbanned von:
    -----------------------------

    UUID VARCHAR(100), GebanntAm VARCHAR(100), GebanntBis LONG, Grund VARCHAR(100), GebanntVon VARCHAR(100), UnbanGrund VARCHAR(100), UnbannedVon VARCHAR(100)
     */

    public static HashMap<Integer, HashMap<String, String>> getLogs(String uuid) {
        HashMap<Integer, HashMap<String, String>> logs = new HashMap<>();
        int count = 0;
        ResultSet rs = MySQL.getResult("SELECT * FROM BanLogs WHERE UUID = '" + uuid + "'");
        try {
            while (rs.next()) {
                count++;
                HashMap<String, String> log = new HashMap<>();
                log.put("gebanntam", rs.getString("GebanntAm"));
                log.put("gebanntbis", rs.getString("GebanntBis"));
                log.put("grund", rs.getString("Grund"));
                log.put("gebanntvon", rs.getString("GebanntVon"));
                log.put("unbangrund", rs.getString("UnbanGrund"));
                log.put("unbannedvon", rs.getString("UnbannedVon"));
                logs.put(count, log);
            }
        } catch (SQLException e) {

        }
        return logs;
    }

}
