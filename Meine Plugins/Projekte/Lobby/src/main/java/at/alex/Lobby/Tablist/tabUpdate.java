package at.alex.Lobby.Tablist;

import at.alex.Lobby.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class tabUpdate implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission("lobby.tablist.reload")) {
            if (args.length == 0) {
                TablistAPI.update();
                sender.sendMessage(Main.getPrefix() + "§aTablist erfolgreich reloaded!");
                return true;
            }
            sender.sendMessage(Main.getPrefix() + "§6cVerwende §6/tabreload");
            return true;
        }
        sender.sendMessage(Main.getNoperm());
        return true;
    }

}
