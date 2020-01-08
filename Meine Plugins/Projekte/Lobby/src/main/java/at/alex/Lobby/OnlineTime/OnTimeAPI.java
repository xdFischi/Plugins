package at.alex.Lobby.OnlineTime;

import at.alex.Lobby.MySQL.MySQL;
import at.alex.Lobby.Scoreboard.ScoreboardAPI;
import at.alex.Lobby.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class OnTimeAPI {

    public static void startTimer() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    int hours = 0;
                    int minutes = 0;
                    ResultSet rs = MySQL.getResult("SELECT OnlineHours, OnlineMinutes FROM PlayerTimes WHERE UUID = '" + players.getUniqueId().toString() + "'");
                    try {
                        while (rs.next()) {
                            hours = Integer.parseInt(rs.getString("OnlineHours"));
                            minutes = Integer.parseInt(rs.getString("OnlineMinutes"));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    minutes++;
                    if (minutes > 58) {
                        minutes = 0;
                        hours++;
                    }
                    try {
                        MySQL.update("UPDATE PlayerTimes SET OnlineHours = '" + hours + "', OnlineMinutes = '" + minutes + "' WHERE UUID = '" + players.getUniqueId().toString() + "'");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ScoreboardAPI.sendScoreboard(players);
                }
            }
        }, 20*60, 20*60);
    }

    private static boolean isRegistered(String UUID) {
        ResultSet rs = MySQL.getResult("SELECT Spielername FROM PlayerTimes WHERE UUID='" + UUID + "'");
        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static String getOnlineTime(String UUID) {

        if (!isRegistered(UUID)) {
            registerNew(UUID);
        }

        ResultSet rs = MySQL.getResult("SELECT OnlineHours, OnlineMinutes FROM PlayerTimes WHERE UUID = '" + UUID + "'");
        try {
            while (rs.next()) {
                return (rs.getString("OnlineHours"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "0";
    }

    private static void registerNew(String uuid) {
        try {
            String playername = Bukkit.getPlayer(UUID.fromString(uuid)).getName();
            MySQL.update("INSERT INTO PlayerTimes (Spielername, UUID, OnlineHours, OnlineMinutes) VALUES('" + playername + "','" + uuid + "','" + "0" + "','" + "0" + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
