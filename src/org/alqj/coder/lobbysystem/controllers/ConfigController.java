package org.alqj.coder.lobbysystem.controllers;

import org.alqj.coder.lobbysystem.LobbySystem;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import sun.security.krb5.Config;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

public class ConfigController {

    private final LobbySystem ls;
    private YamlConfiguration configuration;
    private File file;

    public ConfigController(LobbySystem ls, String name, boolean defaults, boolean comments){
        Reader reader;
        this.ls = ls;
        this.comments = comments;
        this.file = new File(ls.getDataFolder(), name + ".yml");
        this.configuration = YamlConfiguration.loadConfiguration(file);

        if(comments) reader = new InputStreamReader(getContent(new InputStreamReader(ls.getResource(name + ".yml"), StandardCharsets.UTF_8)));

        else reader = new InputStreamReader(ls.getResource(name + ".yml"), StandardCharsets.UTF_8);

        YamlConfiguration loadConfiguration = YamlConfiguration.loadConfiguration(reader);
        try{
            if(!file.exists()){
                configuration.addDefaults((Configuration) loadConfiguration);
                configuration.options().copyDefaults(true);
                if(comments) save();

                else configuration.save(file);

            } else {
                if(defaults){
                    configuration.addDefaults((Configuration) loadConfiguration);
                    configuration.options().copyDefaults(true);
                    if(comments) save();

                    else configuration.save(file);
                }

                configuration.load(file);
            }
        } catch(IOException | InvalidConfigurationException ex) {}
    }

    private boolean comments;

    public void reload(){
        try{
            configuration.load(file);
        } catch(IOException | InvalidConfigurationException ex){
            ex.printStackTrace();
        }
    }

    public InputStream getContent(Reader reader){
        try{
            String plugin = ls.getDescription().getName();
            int commentNum = 0;
            StringBuilder whole = new StringBuilder();
            BufferedReader buffered = new BufferedReader(reader); String currentLine;
            while((currentLine = buffered.readLine()) != null){
                if(currentLine.startsWith("#")){
                    String addLine = currentLine.replaceFirst("#", plugin + "_COMMENT_" + commentNum + ":");
                    whole.append(addLine).append("\n");
                    commentNum++;
                    continue;
                }
                whole.append(currentLine).append("\n");
            }

            String config = whole.toString();
            InputStream stream = new ByteArrayInputStream(config.getBytes(StandardCharsets.UTF_8));
            buffered.close();
            return stream;
        } catch(IOException ex){
            ex.printStackTrace();
            return null;
        }
    }

    private String prepareStringConfig(String config){
        String[] lines = config.split("\n");
        StringBuilder string = new StringBuilder();
        for(String line : lines){
            if(line.startsWith(ls.getDescription().getName() + "_COMMENT")){
                String normalComment, comment = "#" + line.trim().substring(line.indexOf(":") + 1);

                if(comment.startsWith("# ' ")) normalComment = comment.substring(0, comment.length() - 1).replaceFirst("# ' ", "# ");

                else normalComment = comment;

                string.append(normalComment).append("\n");

            } else {
                string.append(line).append("\n");
            }
        }
        return string.toString();
    }

    public void save(){
        if(comments){
            try{
                InputStream stream = ls.getResource(file.getName());
                if(file.exists()) file.delete();

                Files.copy(stream, file.toPath());
            } catch(IOException ex){
                ex.printStackTrace();
            }
        } else {
            try{
                configuration.save(file);
            } catch(IOException ioException) {}
        }
    }

    public YamlConfiguration getConfig() { return configuration; }

    public String get(String str){
        if(configuration.getString(str) == null) return "";

        return configuration.getString(str);
    }

    public String get(Player player, String str) {
        if (configuration.getString(str) == null) return "";

        String string = configuration.getString(str);
        return string;
    }

    public int getInt(String str) { return configuration.getInt(str); }

    public double getDouble(String str) { return configuration.getDouble(str); }

    public List<String> getList(String list) { return configuration.getStringList(list); }

    public boolean isSet(String str) { return configuration.isSet(str); }

    public void set(String str, Object obj){
        configuration.set(str, obj);
    }

    public boolean getBoolean(String str) { return configuration.getBoolean(str); }
}
