package at.alex.Lobby.Coins;

import at.alex.Lobby.MySQL.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoinAPI {

    public static boolean isRegistered(String UUID) {
        ResultSet rs = MySQL.getResult("SELECT UUID FROM coins WHERE UUID='" + UUID + "'");
        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static long getCoins(String uuid) {
        try {
            PreparedStatement statement = MySQL.con.prepareStatement("SELECT coins FROM coins WHERE UUID = ?");
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getLong("coins");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void addCoins(String name, String uuid, long coins) {
        long current = getCoins(uuid);
        setCoins(name, uuid, current + coins);
    }

    public static void removeCoins(String name, String uuid, long coins) {
        long current = getCoins(uuid);
        setCoins(name, uuid, current - coins);
    }

    public static void setCoins(String name, String uuid, long coins) {
        if (getCoins(uuid) == -1) {
            try {
                PreparedStatement statement = MySQL.con.prepareStatement("INSERT INTO coins (NAME, UUID, coins) VALUES (?,?,?)");
                statement.setString(1, name);
                statement.setString(2, uuid);
                statement.setLong(3, coins);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                PreparedStatement statement = MySQL.con.prepareStatement("UPDATE coins SET coins = ? WHERE UUID = ?");
                statement.setString(2, uuid);
                statement.setLong(1, coins);
                statement.executeUpdate();
                PreparedStatement statementname = MySQL.con.prepareStatement("UPDATE coins SET NAME = ? WHERE UUID = ?");
                statementname.setString(2, uuid);
                statementname.setString(1, name);
                statementname.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
