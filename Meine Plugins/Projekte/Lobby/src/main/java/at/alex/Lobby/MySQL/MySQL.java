package at.alex.Lobby.MySQL;

import at.alex.Lobby.main.Main;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {

    public static String username;
    public static String password;
    public static String database;
    public static String host;
    public static String port;
    public static Connection con;


    public static void connect() {
        if (!isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" +  database + "?autoReconnect=true", username, password);
                Bukkit.getConsoleSender().sendMessage(Main.getPrefix() + "MySQL Verbindung aufgebaut!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close() {
        if (isConnected()) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Bukkit.getConsoleSender().sendMessage(Main.getPrefix() + "MySQL Verbindung geschlossen!");
        }
    }

    public static boolean isConnected() {
        return con != null;
    }

    public static void createTable() {
        if (isConnected()) {
            try {
                con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS PlayerTimes (Spielername VARCHAR(100), UUID VARCHAR(100), OnlineHours VARCHAR(100), OnlineMinutes VARCHAR(100))");
                con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS coins(Name VARCHAR(100), UUID VARCHAR(100), Coins VARCHAR(100))");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    public static void update(String qry) {
        if (isConnected()) {
            try {
                con.createStatement().executeUpdate(qry);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ResultSet getResult(String qry) {
        if (isConnected()) {
            try {
                return con.createStatement().executeQuery(qry);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
