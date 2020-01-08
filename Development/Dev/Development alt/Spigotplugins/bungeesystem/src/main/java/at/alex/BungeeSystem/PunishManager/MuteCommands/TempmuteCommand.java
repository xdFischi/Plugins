package at.alex.BungeeSystem.PunishManager.MuteCommands;

import at.alex.BungeeSystem.Main;
import at.alex.BungeeSystem.PunishManager.MuteManager;
import at.alex.BungeeSystem.PunishManager.TimeUnit;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.List;
import java.util.Map;

public class TempmuteCommand extends Command {

    public TempmuteCommand(String name) {
        super(name, "bungeesystem.punishmanager.tempmute", new String[]{});
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 4) {
            String bannername = "";
            if (sender instanceof ProxiedPlayer) {
                bannername = sender.getName();
            } else {
                bannername = "CONSOLE";
            }
            if (MuteManager.isRegistred(args[0])) {
                if (!MuteManager.isMute(MuteManager.getOfflineUUID(args[0]))) {

                    long value = 0;
                    try {
                        value = Integer.valueOf(args[1]);
                    } catch (NumberFormatException e) {
                        sender.sendMessage(Main.zahlenwertMessage);
                    }

                    String unitString = args[2];
                    String reason = "";
                    for (int i = 3; i < args.length; i++) {
                        reason += args[i] + " ";
                    }
                    List<String> unitList = TimeUnit.getTimeUnits();
                    if (unitList.contains(unitString.toLowerCase())) {
                        TimeUnit unit = TimeUnit.getTimeUnit(unitString);
                        long seconds = value * unit.getSeconds();

                        String tbm = Main.tempmutemessage;
                        tbm = tbm.replace("%spieler%", args[0]);
                        tbm = tbm.replace("%grund%", reason);
                        tbm = tbm.replace("%zeit%", Long.toString(value));
                        if (seconds > 1) {
                            tbm = tbm.replace("%format%", unit.getMultiname());
                        } else {
                            tbm = tbm.replace("%format%", unit.getSinglename());
                        }
                        MuteManager.mute(MuteManager.getOfflineName(MuteManager.getOfflineUUID(args[0])), MuteManager.getOfflineUUID(args[0]), seconds, reason, bannername, System.currentTimeMillis());
                        sender.sendMessage(tbm);
                    } else {
                        sender.sendMessage(Main.prefix + "§cDiese Time-ID existiert nicht!");
                        sender.sendMessage(Main.prefix + "§7---------- §4Time-IDs §7----------");
                        for (Map.Entry<String, String> entry : TimeUnit.getFullNames().entrySet()) {
                            sender.sendMessage("§7- §e" + entry.getKey() + " §7| §e" + entry.getValue());
                        }
                        sender.sendMessage(Main.prefix + "§7------------------------------");
                    }
                } else sender.sendMessage(Main.isMutedMessage);
            } else sender.sendMessage(Main.neverPlayedBefore);

        } else sender.sendMessage(Main.usageMessage.replace("%usage%", "/tempmute <spieler> <zahl> <einheit> <grund>"));
    }

}
