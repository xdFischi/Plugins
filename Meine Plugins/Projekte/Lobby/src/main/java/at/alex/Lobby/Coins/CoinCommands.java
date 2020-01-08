package at.alex.Lobby.Coins;

import at.alex.Lobby.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("setcoins")) {
            if (sender.hasPermission("lobby.coins.removecoins")) {
                if (args.length == 2) {
                    OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                    if (CoinAPI.isRegistered(op.getUniqueId().toString())) {
                        try {
                            long coins = Long.parseUnsignedLong(args[1]);
                            CoinAPI.setCoins(op.getName(), op.getUniqueId().toString(), coins);
                            sender.sendMessage(Main.getPrefix() + "§aDu hast die Coins von §6" + op.getName() + "§a auf §6" + coins + "§a gesetzt.");
                        } catch (NumberFormatException e) {
                            sender.sendMessage(Main.getPrefix() + "§cVerwende §6/setcoins <Spieler> <Coins>");
                        }
                    } else sender.sendMessage(Main.getPrefix() + "§cDer Spieler war noch nie auf dem Server!");
                } else sender.sendMessage(Main.getPrefix() + "§cVerwende §6/setcoins <Spieler> <Coins>");
            } else sender.sendMessage(Main.getPrefix() + Main.getNoperm());
        }

        if (cmd.getName().equalsIgnoreCase("addcoins")) {
            if (sender.hasPermission("lobby.coins.addcoins")) {
                if (args.length == 2) {
                    OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                    if (CoinAPI.isRegistered(op.getUniqueId().toString())) {
                        try {
                            long coins = Long.parseUnsignedLong(args[1]);
                            if (CoinAPI.getCoins(op.getUniqueId().toString()) == -1) {
                                CoinAPI.setCoins(op.getName(), op.getUniqueId().toString(), coins);
                                sender.sendMessage(Main.getPrefix() + "§aDu hast §6" + op.getName() + " §e" + coins + " §6Coins§a hinzugefügt.");
                            } else {
                                CoinAPI.addCoins(op.getName(), op.getUniqueId().toString(), coins);
                                sender.sendMessage(Main.getPrefix() + "§aDu hast §6" + op.getName() + " §e" + coins + " §6Coins§a hinzugefügt.");
                            }
                        } catch (NumberFormatException e) {
                            sender.sendMessage(Main.getPrefix() + "§cVerwende §6/addcoins <Spieler> <Coins>");
                        }
                    } else sender.sendMessage(Main.getPrefix() + "§cDer Spieler war noch nie auf dem Server!");
                } else sender.sendMessage(Main.getPrefix() + "§cVerwende §6/addcoins <Spieler> <Coins>");
            } else sender.sendMessage(Main.getPrefix() + Main.getNoperm());
        }

        if (cmd.getName().equalsIgnoreCase("removecoins")) {
            if (sender.hasPermission("lobby.coins.removecoins")) {
                if (args.length == 2) {
                    OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                    if (CoinAPI.isRegistered(op.getUniqueId().toString())) {
                        try {
                            long coins = Long.parseUnsignedLong(args[1]);
                            if (coins <= CoinAPI.getCoins(op.getUniqueId().toString())) {
                                CoinAPI.removeCoins(op.getName(), op.getUniqueId().toString(), coins);
                                sender.sendMessage(Main.getPrefix() + "§aDu hast §6" + op.getName() + " §e" + coins + " §6Coins§a entfernt.");
                            }
                            else {
                                CoinAPI.setCoins(op.getName(), op.getUniqueId().toString(), 0);
                                sender.sendMessage(Main.getPrefix() + "§aDu hast §6" + op.getName() + " §e" + coins + " §6Coins§a entfernt.");
                            }
                        } catch (NumberFormatException e) {
                            sender.sendMessage(Main.getPrefix() + "§cVerwende §6/removecoins <Spieler> <Coins>");
                        }
                    } else sender.sendMessage(Main.getPrefix() + "§cDer Spieler war noch nie auf dem Server!");
                } else sender.sendMessage(Main.getPrefix() + "§cVerwende §6/removecoins <Spieler> <Coins>");
            } else sender.sendMessage(Main.getPrefix() + Main.getNoperm());
        }

        if (cmd.getName().equalsIgnoreCase("coins")) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    p.sendMessage(Main.getPrefix() + "§aDu hast §e" + CoinAPI.getCoins(p.getUniqueId().toString()) + " §6Coins§a.");
                } else sender.sendMessage(Main.getPrefix() + "§cDazu musst du ein Spieler sein!");
            } else if (args.length == 1) {
                if (sender.hasPermission("lobby.coins.coinsother")) {
                    OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                    if (CoinAPI.isRegistered(op.getUniqueId().toString())) {
                        sender.sendMessage(Main.getPrefix() + "§aDer Spieler §6" + op.getName() + " §ahat §e" + CoinAPI.getCoins(op.getUniqueId().toString()) + " §6Coins§a.");
                    } else sender.sendMessage(Main.getPrefix() + "§cDer Spieler war noch nie auf dem Server!");
                } else {
                    sender.sendMessage(Main.getPrefix() + Main.getNoperm());
                }
            } else {
                sender.sendMessage(Main.getPrefix() + "§cVerwende §6/coins §coder §6/coins <Spieler>§c.");
            }
        }

        return false;
    }

}
