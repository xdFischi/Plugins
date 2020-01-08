package at.alex.BungeeSystem.OnlineTime;

import at.alex.BungeeSystem.MySQL.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OnlineTimeAPI {

    public static boolean isRegistered(String UUID) {
        ResultSet rs = MySQL.getResult("SELECT Spielername FROM PlayerTimes WHERE UUID='" + UUID + "'");
        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
