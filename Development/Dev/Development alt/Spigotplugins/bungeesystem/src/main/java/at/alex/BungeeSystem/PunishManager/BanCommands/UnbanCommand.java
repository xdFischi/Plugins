package at.alex.BungeeSystem.PunishManager.BanCommands;

import at.alex.BungeeSystem.Main;
import at.alex.BungeeSystem.PunishManager.BanManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class UnbanCommand extends Command {

        /*
    Syntax: /ban <Spieler> <Ban-ID>
    String uuid, long gebanntam, long gebanntbis, String grund, String gebanntvon, String unbangrund
     */

    public UnbanCommand(String name) {
        super(name, "bungeesystem.punishmanager.unban", new String[]{});
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 2) {
            String unbannername = "";
            if (sender instanceof ProxiedPlayer) {
                unbannername = sender.getName();
            } else {
                unbannername = "CONSOLE";
            }

            String unbanreason = "";
            for (int i = 1; i < args.length; i++) {
                unbanreason += args[i] + " ";
            }

            if (BanManager.isRegistred(args[0])) {
                if (BanManager.isBanned(BanManager.getOfflineUUID(args[0]))) {
                    BanManager.unBan(
                            BanManager.getOfflineUUID(args[0]),
                            BanManager.getBanDay(BanManager.getOfflineUUID(args[0])),
                            System.currentTimeMillis(),
                            BanManager.getReason(BanManager.getOfflineUUID(args[0])),
                            BanManager.getBanner(BanManager.getOfflineUUID(args[0])),
                            unbanreason,
                            unbannername);
                    sender.sendMessage(Main.unbanmessage.replace("%spieler%", args[0]));
                } else sender.sendMessage(Main.prefix + "§cDieser Spieler ist nicht gebannt!");
            } else sender.sendMessage(Main.neverPlayedBefore);
        } else sender.sendMessage(Main.usageMessage.replace("%usage%", "/unban <spieler> <grund>"));
    }
}
