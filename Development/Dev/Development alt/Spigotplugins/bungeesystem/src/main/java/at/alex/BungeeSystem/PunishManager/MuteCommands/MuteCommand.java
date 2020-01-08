package at.alex.BungeeSystem.PunishManager.MuteCommands;

import at.alex.BungeeSystem.Main;
import at.alex.BungeeSystem.PunishManager.MuteManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MuteCommand extends Command {

    public MuteCommand(String name) {
        super(name, "bungeesystem.punishmanager.mute", new String[]{});
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 2) {
            String mutername = "";
            if (sender instanceof ProxiedPlayer) {
                mutername = sender.getName();
            } else {
                mutername = "CONSOLE";
            }
            if (MuteManager.isRegistred(args[0])) {
                if (Main.banIDs.containsKey(args[1])) {
                    if (sender.hasPermission(MuteManager.getIdPermission(args[1]))) {
                        if (!MuteManager.isMute(MuteManager.getOfflineUUID(args[0]))) {
                            MuteManager.mute(MuteManager.getOfflineName(MuteManager.getOfflineUUID(args[0])), MuteManager.getOfflineUUID(args[0]), MuteManager.getIdSeconds(args[1]), MuteManager.getIdReason(args[1]), mutername, System.currentTimeMillis());

                            sender.sendMessage();

                            String bm = Main.muteMessage;
                            bm = bm.replace("%player%", args[0]);
                            bm = bm.replace("%grund%", MuteManager.getIdReason(args[1]));
                            ProxiedPlayer pp = ProxyServer.getInstance().getPlayer(args[0]);
                            if (pp instanceof ProxyServer) {
                                pp.sendMessage(bm);
                            }

                        } else sender.sendMessage(Main.isMutedMessage);
                    } else sender.sendMessage(Main.noPerm);
                } else MuteManager.sendMuteIDs(sender);
            } else sender.sendMessage(Main.neverPlayedBefore);

        } else sender.sendMessage(Main.usageMessage.replace("%usage%", "/mute <spieler> <id>"));
    }

}
