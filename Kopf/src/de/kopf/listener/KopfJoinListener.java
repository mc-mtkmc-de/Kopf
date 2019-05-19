package de.kopf.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import de.kopf.main.Kopf;
import de.kopf.data.Data;
import de.kopf.data.FileManager;
import de.kopf.mysql.SQLManager;

public class KopfJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(Data.useMySQL){
            String uuid = e.getPlayer().getUniqueId().toString();
            if(SQLManager.inKopf(uuid)){
                Kopf.players.put(e.getPlayer(), SQLManager.getData(uuid));
            }else{
                SQLManager.addToKopf(uuid, 0);
                Kopf.players.put(e.getPlayer(), SQLManager.getData(uuid));
                Kopf.log("§6" + e.getPlayer().getName() + " §awurde zur Datenbank hinzugefügt!");
            }
        }else{
            if(!FileManager.getFile().valueExist("Users." + e.getPlayer().getUniqueId().toString() + ".Time")){
                savePlayerToFile(e.getPlayer());
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        if(e.getMessage().equalsIgnoreCase("#patty")){
            e.setCancelled(true);
            e.getPlayer().sendMessage(Data.Prefix + "§dSystem by PattyXDHD");
            e.getPlayer().sendMessage(Data.Prefix + " §8» §byoutube.com/pattyxdhd");
        }
    }

    private void savePlayerToFile(Player p ){
        FileManager.relaodFile();
        FileManager.getFile().setValue("Users." + p.getUniqueId().toString() + ".Time", 0);
        FileManager.getFile().save();
        Kopf.log("§6" + p.getName() + " §awurde zur Config hinzugefügt!");
    }

}