package at.alex.Lobby.Scoreboard;

import at.alex.Lobby.Coins.CoinAPI;
import at.alex.Lobby.OnlineTime.OnTimeAPI;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;


public class ScoreboardAPI {

    public static void sendScoreboard(Player p) {
        Scoreboard board = new Scoreboard();

        ScoreboardObjective obj = board.registerObjective("§3FISCHI§a.NL", IScoreboardCriteria.b);

        PacketPlayOutScoreboardObjective createPacket = new PacketPlayOutScoreboardObjective(obj, 0);
        PacketPlayOutScoreboardObjective removePacket = new PacketPlayOutScoreboardObjective(obj, 1);
        PacketPlayOutScoreboardDisplayObjective displayPacket = new PacketPlayOutScoreboardDisplayObjective(1, obj);
        obj.setDisplayName("§3§lFISCHI§a.NL");

        ScoreboardScore s01 = new ScoreboardScore(board, obj, "§r              ");
        ScoreboardScore s02 = new ScoreboardScore(board, obj, "§fDein Rang:");
        ScoreboardScore s03 = new ScoreboardScore(board, obj, getRank(p));
        ScoreboardScore s04 = new ScoreboardScore(board, obj, "§a ");
        ScoreboardScore s05 = new ScoreboardScore(board, obj, "§fCoins:");
        ScoreboardScore s06 = new ScoreboardScore(board, obj, "§6" + CoinAPI.getCoins(p.getUniqueId().toString()));
        ScoreboardScore s07 = new ScoreboardScore(board, obj, "§c ");
        ScoreboardScore s08 = new ScoreboardScore(board, obj, "§fOnlinezeit:");
        ScoreboardScore s09 = new ScoreboardScore(board, obj, "§b" + OnTimeAPI.getOnlineTime(p.getUniqueId().toString()) + "§7h");

        s01.setScore(8);
        s02.setScore(7);
        s03.setScore(6);
        s04.setScore(5);
        s05.setScore(4);
        s06.setScore(3);
        s07.setScore(2);
        s08.setScore(1);
        s09.setScore(0);

        PacketPlayOutScoreboardScore ps01 = new PacketPlayOutScoreboardScore(s01);
        PacketPlayOutScoreboardScore ps02 = new PacketPlayOutScoreboardScore(s02);
        PacketPlayOutScoreboardScore ps03 = new PacketPlayOutScoreboardScore(s03);
        PacketPlayOutScoreboardScore ps04 = new PacketPlayOutScoreboardScore(s04);
        PacketPlayOutScoreboardScore ps05 = new PacketPlayOutScoreboardScore(s05);
        PacketPlayOutScoreboardScore ps06 = new PacketPlayOutScoreboardScore(s06);
        PacketPlayOutScoreboardScore ps07 = new PacketPlayOutScoreboardScore(s07);
        PacketPlayOutScoreboardScore ps08 = new PacketPlayOutScoreboardScore(s08);
        PacketPlayOutScoreboardScore ps09 = new PacketPlayOutScoreboardScore(s09);

        sendPacket(p, removePacket);
        sendPacket(p, createPacket);
        sendPacket(p, displayPacket);
        sendPacket(p, ps01);
        sendPacket(p, ps02);
        sendPacket(p, ps03);
        sendPacket(p, ps04);
        sendPacket(p, ps05);
        sendPacket(p, ps06);
        sendPacket(p, ps07);
        sendPacket(p, ps08);
        sendPacket(p, ps09);

    }

    private static void sendPacket(Player p, Packet<?> packet) {
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    private static String getRank(Player target) {
        String rank = "§7Player";

        if (target.hasPermission("lobby.tablist.owner")) {
            rank = "§4Owner";
        } else if (target.hasPermission("lobby.tablist.superadmin")) {
            rank = "§4Super-Admin";
        } else if (target.hasPermission("lobby.tablist.admin")) {
            rank = "§cAdmin";
        } else if (target.hasPermission("lobby.tablist.developer")) {
            rank = "§dDeveloper";
        } else if (target.hasPermission("lobby.tablist.supermoderator")) {
            rank = "§cSuper-Mod";
        } else if (target.hasPermission("lobby.tablist.moderator")) {
            rank = "§9Moderator";
        } else if (target.hasPermission("lobby.tablist.superarchitekt")) {
            rank = "§2Super-Archi";
        } else if (target.hasPermission("lobby.tablist.architekt")) {
            rank = "§aArchitekt";
        } else if (target.hasPermission("lobby.tablist.team")) {
            rank = "§3Team";
        } else if (target.hasPermission("lobby.tablist.supporter")) {
            rank = "§bSupporter";
        } else if (target.hasPermission("lobby.tablist.testsupporter")) {
            rank = "§bTest-Supporter";
        } else if (target.hasPermission("lobby.tablist.testdeveloper")) {
            rank = "§dTest-Developer";
        } else if (target.hasPermission("lobby.tablist.twitch")) {
            rank = "§5Twitch";
        } else if (target.hasPermission("lobby.tablist.youtuber")) {
            rank = "§5Youtuber";
        } else if (target.hasPermission("lobby.tablist.diamond")) {
            rank = "§3Diamond";
        } else if (target.hasPermission("lobby.tablist.platin")) {
            rank = "§fPlatin";
        } else if (target.hasPermission("lobby.tablist.vip")) {
            rank = "§6VIP";
        }

        return rank;
    }
}
