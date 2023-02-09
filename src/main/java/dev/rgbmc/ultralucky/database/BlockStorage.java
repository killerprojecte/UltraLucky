package dev.rgbmc.ultralucky.database;

import dev.rgbmc.ultralucky.UltraLucky;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlockStorage implements Listener {

    private SQLiteDataSource source;
    private Connection connection;
    private List<String> cachedList = new ArrayList<>();

    public BlockStorage() {
        try {
            source = new SQLiteDataSource();
            source.setUrl("jdbc:sqlite:" + UltraLucky.instance.getDataFolder().getPath() + File.separator + "block_storage.db");
            connection = source.getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement checkStatement = connection.prepareStatement("SELECT name FROM sqlite_master WHERE type='table' AND name='ultralucky';");
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) return;
            statement.addBatch(
                    "CREATE TABLE \"ultralucky\" (\n" +
                            "  \"Location\" text(200,1) NOT NULL\n" +
                            ");\n" +
                            "PRAGMA foreign_keys = true;");
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        Bukkit.getScheduler().runTaskAsynchronously(UltraLucky.instance, () -> {
            if (!query(event.getBlock().getLocation())) {
                storage(event.getBlock().getLocation());
            }
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        if (event.getPlayer() == null) return;
        if (!event.canBuild()) return;
        Bukkit.getScheduler().runTaskAsynchronously(UltraLucky.instance, () -> {
            if (!query(event.getBlock().getLocation())) {
                storage(event.getBlock().getLocation());
            }
        });
    }

    public boolean query(Location location) {
        String str = locationToString(location);
        if (cachedList.contains(str)) return true;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Location FROM ultralucky GROUP BY Location HAVING Location = '" + str + "';");
            boolean status = preparedStatement.executeQuery().next();
            if (status) {
                cachedList.add(str);
            }
            return status;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void storage(Location location) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ultralucky (\"Location\") VALUES ('" + locationToString(location) + "');");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String locationToString(Location location) {
        return location.getWorld().getName() + ";;" +
                location.getBlockX() +
                ";;" +
                location.getBlockY() +
                ";;" +
                location.getBlockZ();
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
