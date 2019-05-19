package de.kopf.kopfmanager;

import org.bukkit.entity.Player;

import de.kopf.main.Kopf;
import de.kopf.data.Data;
import de.kopf.data.FileManager;
import de.kopf.mysql.SQLManager;

public class ErschaffeKopf {

    public static boolean canGetSkull(Player player){
        long end;
        if(player.hasPermission("kopf.admin")){
            return true;
        }
        if(Data.useMySQL){
            end = Kopf.players.get(player).getTime() + Data.kopfCooldown*1000L;
        }else{
            end = FileManager.getFile().getLong("Users." + player.getUniqueId().toString() + ".Time") + Data.kopfCooldown*1000L;
        }


        if(System.currentTimeMillis() >= end){
            return true;
        }else{
            return false;
        }
    }


    public static Long getRemindingTIme(Player player){
        if(Data.useMySQL){
            return (Kopf.players.get(player).getTime() + Data.kopfCooldown*1000L);
        }else{
            return (FileManager.getFile().getLong("Users." + player.getUniqueId().toString() + ".Time") + Data.kopfCooldown*1000L);
        }
    }

    public static void setWatingTime(Player player){
        if(Data.useMySQL){
            SQLManager.updateData(player, System.currentTimeMillis());
        }else{
            FileManager.getFile().setValue("Users." + player.getUniqueId().toString() + ".Time", System.currentTimeMillis());
            FileManager.getFile().save();
        }
    }


}