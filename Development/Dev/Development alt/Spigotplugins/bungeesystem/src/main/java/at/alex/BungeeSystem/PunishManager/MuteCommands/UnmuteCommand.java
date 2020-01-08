package at.alex.BungeeSystem.PunishManager.MuteCommands;

import at.alex.BungeeSystem.Main;
import at.alex.BungeeSystem.PunishManager.MuteManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class UnmuteCommand extends Command {

    public UnmuteCommand(String name) {
        super(name, "bungeesystem.punishmanager.unmute", new String[]{});
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 2) {
            String unmutername = "";
            if (sender instanceof ProxiedPlayer) {
                unmutername = sender.getName();
            } else {
                unmutername = "CONSOLE";
            }

            String unmutereason = "";
            for (int i = 1; i < args.length; i++) {
                unmutereason += args[i] + " ";
            }

            if (MuteManager.isRegistred(args[0])) {
                if (MuteManager.isMute(MuteManager.getOfflineUUID(args[0]))) {
                    MuteManager.unMute(
                            MuteManager.getOfflineUUID(args[0]),
                            MuteManager.getMuteDay(MuteManager.getOfflineUUID(args[0])),
                            System.currentTimeMillis(),
                            MuteManager.getReason(MuteManager.getOfflineUUID(args[0])),
                            MuteManager.getMuter(MuteManager.getOfflineUUID(args[0])),
                            unmutereason,
                            unmutername);
                    sender.sendMessage(Main.unmutemessage.replace("%spieler%", args[0]));
                } else sender.sendMessage(Main.prefix + "§cDieser Spieler ist nicht gemuted!");
            } else sender.sendMessage(Main.neverPlayedBefore);
        } else sender.sendMessage(Main.usageMessage.replace("%usage%", "/unmute <spieler> <grund>"));
    }

}
