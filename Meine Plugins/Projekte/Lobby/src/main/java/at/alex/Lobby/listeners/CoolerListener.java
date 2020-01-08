package at.alex.Lobby.listeners;

import at.alex.Lobby.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

public class CoolerListener implements Listener {

    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        if (Main.isBuild(e.getPlayer().getUniqueId().toString()) == false) {
            Player p = e.getPlayer();
            ItemStack item = e.getItemDrop().getItemStack().clone();
            item.setAmount(p.getInventory().getItemInHand().getAmount() + 1);
            e.getItemDrop().remove();
            p.getInventory().setItem(p.getInventory().getHeldItemSlot(), item);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (Main.isBuild(e.getWhoClicked().getUniqueId().toString()) == false) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDestroy(BlockBreakEvent e) {
        if (Main.isBuild(e.getPlayer().getUniqueId().toString()) == false) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        if (!Main.isBuild(e.getPlayer().getUniqueId().toString())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                e.setCancelled(true);
            }
            if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (Main.isCheck(e.getDamager().getUniqueId().toString()) && Bukkit.getOnlinePlayers().contains(e.getEntity())) {
            e.setCancelled(false);
        }
    }

}
