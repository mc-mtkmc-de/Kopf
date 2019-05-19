package de.kopf.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.kopf.data.Data;
import de.kopf.data.FileManager;

public class KopfReload implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {


        if(commandSender.hasPermission("kopf.reload") || commandSender.hasPermission("kopf.admin")){
            FileManager.relaodFile();
            commandSender.sendMessage(Data.Prefix + "§aDie Config wurde erneut eingelesen.");
        }else{
            commandSender.sendMessage(Data.noPerm);
        }


        return false;
    }
}