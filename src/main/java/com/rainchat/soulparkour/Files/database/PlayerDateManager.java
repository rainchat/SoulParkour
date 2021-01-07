package com.rainchat.soulparkour.Files.database;

import com.rainchat.soulparkour.Files.Configs.ConfigSettings;
import com.rainchat.soulparkour.utils.ServerLog;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class PlayerDateManager {
    private static SqlLite database = SqlLite.getInstance();
    private static PlayerDateManager instance = new PlayerDateManager();




    public static void addPlayer(Player player) throws SQLException {
        if (isExist(player)){
            return;
        }
        String syntax;
        try {
            syntax = "INSERT INTO soul_date (owner, energy, toggle, particle_stile) VALUES('"
                    + player.getUniqueId().toString() + "', '"
                    + ConfigSettings.MAX_ENERGY.getDouble() + "', '"
                    + "true" + "', '"
                    + "default" + "' );";
            PreparedStatement preparedStatement = database.getDatabaseConnection().prepareStatement(syntax);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            ServerLog.log(Level.WARNING, "There was an issue saving the soul_date " + player.getUniqueId().toString() + "' to the database (" + e.getMessage() + ").");
        }
    }
    public static boolean isExist(Player player) throws SQLException {

        String syntax;
        try {
            syntax = "SELECT * FROM soul_date WHERE owner ='"
                    + player.getUniqueId().toString() + "';";
            PreparedStatement preparedStatement = database.getDatabaseConnection().prepareStatement(syntax);
            preparedStatement.execute();
            ResultSet results = preparedStatement.executeQuery();
            if(!results.next()){
                return false;
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            ServerLog.log(Level.WARNING, "There was an issue saving the soul_date " + player.getUniqueId().toString() + "' to the database (" + e.getMessage() + ").");
        }
        return true;
    }

    public static boolean isToggle(Player player) throws SQLException {

        if (!isExist(player)){
           return false;
        }
        boolean isToggle = false;
        String syntax;
        try {
            syntax = "SELECT * FROM soul_date WHERE owner ='"
                    + player.getUniqueId().toString() + "';";
            PreparedStatement preparedStatement = database.getDatabaseConnection().prepareStatement(syntax);
            preparedStatement.execute();
            ResultSet results = preparedStatement.executeQuery();
            if(!results.next()){

                return false;
            }
            isToggle = results.getBoolean("toggle");
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            ServerLog.log(Level.WARNING, "There was an issue saving the soul_date " + player.getUniqueId().toString() + "' to the database (" + e.getMessage() + ").");
        }
        return isToggle;
    }

    public static void setEnergy(Player player, Double energy) throws SQLException {

        String syntax;
        try {
            syntax = "UPDATE soul_date SET energy='" + energy + "' WHERE owner like '" + player.getUniqueId().toString() +"'";
            PreparedStatement preparedStatement = database.getDatabaseConnection().prepareStatement(syntax);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            ServerLog.log(Level.WARNING, "There was an issue saving the soul_date " + player.getUniqueId().toString() + "' to the database (" + e.getMessage() + ").");
        }
    }

    public static boolean addEnergy(Player player, Double energy) throws SQLException {
        if ((Double)(getEnergy(player)+energy) < (Double)0.0){
            return false;
        } else if ((getEnergy(player)+energy)>ConfigSettings.MAX_ENERGY.getDouble()){
            setEnergy(player,ConfigSettings.MAX_ENERGY.getDouble());
            return true;
        } else {
            setEnergy(player,(getEnergy(player)+energy));
            return true;
        }
    }

    public static Double getEnergy(Player player) throws SQLException {
        Double x = 0.0;
        String syntax;
        try {
            syntax = "SELECT * FROM soul_date WHERE owner ='" + player.getUniqueId().toString() + "';";
            PreparedStatement preparedStatement = database.getDatabaseConnection().prepareStatement(syntax);
            preparedStatement.execute();
            ResultSet results = preparedStatement.executeQuery();
            x = results.getDouble("energy");
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            ServerLog.log(Level.WARNING, "There was an issue saving the soul_date " + player.getUniqueId().toString() + "' to the database (" + e.getMessage() + ").");
        }
        return x;
    }

    public static void deletePlayer(Player player) throws SQLException {

        String syntax;
        try {
            syntax = "DELETE FROM soul_date WHERE owner ='" + player.getUniqueId().toString() + "';";
            PreparedStatement preparedStatement = database.getDatabaseConnection().prepareStatement(syntax);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            ServerLog.log(Level.WARNING, "There was an issue saving the soul_date " + player.getUniqueId().toString() + "' to the database (" + e.getMessage() + ").");
        }
    }

    public static boolean toggleMode(Player player) throws SQLException {
        if (!isExist(player)){
            addPlayer(player);
            return true;
        }
        boolean isToggle = isToggle(player);
        String syntax;
        try {
            if (isToggle){
                syntax = "UPDATE soul_date SET toggle=false WHERE owner like '" + player.getUniqueId().toString() +"'";
            } else {
                syntax = "UPDATE soul_date SET toggle=true WHERE owner like '" + player.getUniqueId().toString() +"'";
            }

            PreparedStatement preparedStatement = database.getDatabaseConnection().prepareStatement(syntax);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            ServerLog.log(Level.WARNING, "There was an issue saving the soul_date " + player.getUniqueId().toString() + "' to the database (" + e.getMessage() + ").");
        }
        return isToggle;
    }

}
