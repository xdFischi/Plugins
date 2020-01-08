package at.alex.BungeeSystem.Commands;

import at.alex.BungeeSystem.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BroadCastCommand extends Command {

    public BroadCastCommand(String name) {
        super(name, "bungeesystem.broadcast", new String[]{"bc"});
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer pp = (ProxiedPlayer) sender;


            if (args.length > 0) {
                String message = " ";
                for(int i = 0; i < args.length; i++) {
                    message = message + args[i] + " ";
                }

                message = ChatColor.translateAlternateColorCodes('&', message);

                ProxyServer.getInstance().broadcast("§7»");
                ProxyServer.getInstance().broadcast("§7[§4Broadcast§7] §d" + message);
                ProxyServer.getInstance().broadcast("§7»");

            } else pp.sendMessage(Main.prefix + "Benutze §6/broadcast §7<§6Nachricht§7>");
        } else sender.sendMessage(Main.mustPlayer);

    }
}
