package de.kopf.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;


import de.kopf.util.KopfPlayer;
import de.kopf.commands.KopfCommand;
import de.kopf.commands.KopfReload;
import de.kopf.data.Data;
import de.kopf.data.FileManager;
import de.kopf.mysql.MySQL;
import de.kopf.listener.KopfJoinListener;

public class Kopf extends JavaPlugin {

    public static HashMap<Player, KopfPlayer> players = new HashMap<>();

    private static Kopf inctance;
    public static Kopf getInctance(){
        return inctance;
    }

    @Override
    public void onEnable() {
        inctance = this;
        try {
            loadFile();
            loadCommands();
            loadListener(Bukkit.getPluginManager());
            if(Data.useMySQL){
                loadMySQL();
                Bukkit.getOnlinePlayers().forEach(p -> p.kickPlayer("§cServer reload!"));
            }
        }catch (Exception e1){
            e1.printStackTrace();
            log("§4Fehler: §cPlugin konnte nicht geladen werden.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }finally{
            log("§aPlugin geladen.");
        }
    }

    @Override
    public void onDisable() {
        log("§cPlugin entladen.");
    }

    private void loadCommands(){
        getCommand("kopf").setExecutor(new KopfCommand());
        getCommand("kreload").setExecutor(new KopfReload());
    }

    private void loadListener(PluginManager pm){
        pm.registerEvents(new KopfJoinListener(),this);
    }

    private void loadFile(){
        FileManager.loadFile();
        FileManager.readFile();
    }

    private void loadMySQL(){
        MySQL.connect();
        MySQL.update("CREATE TABLE IF NOT EXISTS Kopf (UUID VARCHAR(255),Time LONG)");
    }

    public static void log(String msg){
        Bukkit.getConsoleSender().sendMessage(Data.Prefix + msg);
    }

}