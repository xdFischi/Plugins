package at.alex.Lobby.Scoreboard;

import at.alex.Lobby.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class scoreUpdate implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission("lobby.scoreboard.reload")) {
            if (args.length == 0) {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    ScoreboardAPI.sendScoreboard(players);
                }
                sender.sendMessage(Main.getPrefix() + "§aScoreboard erfolgreich reloaded!");
                return true;
            }
            sender.sendMessage(Main.getPrefix() + "§cVerwende §6/scorereload");
            return true;
        }
        sender.sendMessage(Main.getNoperm());
        return true;
    }

}
