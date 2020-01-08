package at.alex.BungeeSystem.Commands;

import at.alex.BungeeSystem.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class hubCommand extends Command {

    public hubCommand(String name) {
        super(name, "bungeesystem.lobby", new String[]{"loby"});
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer ps = (ProxiedPlayer)sender;
            if (ps.getServer().getInfo().getName().equalsIgnoreCase("hub")) {
                ps.sendMessage(Main.prefix + "§cDu bist bereits in der Lobby!");
                return;
            }
            ServerInfo target = ProxyServer.getInstance().getServerInfo("Lobby");
            ps.connect(target);
            return;
        }
        sender.sendMessage(Main.prefix + "§cDiesen Befehl kann nur ein Spieler ausführen!");
        return;
    }
}
