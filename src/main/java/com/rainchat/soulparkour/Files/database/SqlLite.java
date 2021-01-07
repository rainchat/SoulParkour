package com.rainchat.soulparkour.Files.database;

import com.rainchat.soulparkour.Files.FileManager;
import com.rainchat.soulparkour.SoulParkourMain;
import com.rainchat.soulparkour.utils.ServerLog;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class SqlLite {
    private static SqlLite instance = new SqlLite();

    private Connection databaseConnection;
    private String tableName;

    public static SqlLite getInstance() {
        return instance;
    }

    public void onLoad() {
        FileConfiguration file = FileManager.Files.CONFIG.getFile();

        File warpsFile = new File(SoulParkourMain.getPluginInstance().getDataFolder(), "/PlayerDate.db"), backupFile = new File(SoulParkourMain.getPluginInstance().getDataFolder(), "/warps-backup.db");

        long databaseStartTime = System.currentTimeMillis();

        tableName = "create table if not exists soul_date (owner varchar(100) PRIMARY KEY, energy DOUBLE, toggle BOOLEAN, particle_stile varchar(100))";


        setupDatabase(false);

        if (getDatabaseConnection() != null)
            ServerLog.log(Level.INFO, "Communication to the database was successful. (Took " + (System.currentTimeMillis() - databaseStartTime) + "ms)");
        else {
            ServerLog.log(Level.WARNING, "Communication to the database failed. (Took " + (System.currentTimeMillis() - databaseStartTime) + "ms)");
            SoulParkourMain.getPluginInstance().getServer().getPluginManager().disablePlugin(SoulParkourMain.getPluginInstance());
            return;
        }
    }

    private void setupDatabase(boolean useMySQL) {
        if (getDatabaseConnection() != null) return;
        FileConfiguration file = FileManager.Files.CONFIG.getFile();

        try {
            Statement statement;
            if (!useMySQL) {
                Class.forName("org.sqlite.JDBC");
                setDatabaseConnection(DriverManager.getConnection("jdbc:sqlite:" + SoulParkourMain.getPluginInstance().getDataFolder() + "/PlayerDate.db"));
            }

            statement = getDatabaseConnection().createStatement();
            statement.executeUpdate(tableName);

            statement.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            ServerLog.log(Level.WARNING, "There was an issue involving the SQL connection.");
        }
    }

    public Connection getDatabaseConnection() {
        return databaseConnection;
    }

    private void setDatabaseConnection(Connection connection) {
        this.databaseConnection = connection;
    }
}
