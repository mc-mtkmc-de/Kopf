package de.kopf.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.SimpleDateFormat;

import de.kopf.data.Data;
import de.kopf.kopfmanager.ErschaffeKopf;

public class KopfCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage("Du musst ein Spieler sein.");
            return  false;
        }

        Player p = ((Player) sender);

        if(!p.hasPermission("kopf.use")){
            p.sendMessage(Data.noPerm);
            return false;
        }

        if(args.length == 1){
            if(ErschaffeKopf.canGetSkull(p)){
            	ErschaffeKopf.setWatingTime(p);
                ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (byte)3);
                SkullMeta im = (SkullMeta) item.getItemMeta();
                im.setOwner(args[0]);
                im.setDisplayName("§6Kopf von " + args[0]);
                item.setItemMeta(im);
                p.getInventory().addItem(item);
                p.sendMessage(Data.Prefix + "§aDu hast den Kopf von §6" + args[0] + " §abekommen.");
                playSoundForAll(Sound.ENTITY_WITHER_DEATH, 1, 2);
            }else{
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                p.sendMessage(Data.Prefix + "§4Du musst noch bis §e" + simpleDateFormat.format(ErschaffeKopf.getRemindingTIme(p)) + " §4warten.");
            }
        }else{
            if(ErschaffeKopf.canGetSkull(p)){
                p.sendMessage(Data.Prefix + "§aUsage: /kopf <Spieler>");
            }else{
                p.sendMessage(Data.Prefix + "§cUsage: /kopf <Spieler>");
            }
        }

        return false;
    }

    private void playSoundForAll(Sound sound, Integer v1, Integer v2){
        Bukkit.getOnlinePlayers().forEach(o -> o.playSound(o.getLocation(), sound, v1, v2));
    }
}