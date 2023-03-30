package dev.rgbmc.ultralucky.database;

import dev.rgbmc.ultralucky.UltraLucky;
import dev.rgbmc.ultralucky.utils.AsyncFuture;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.sql.*;

public class BlockStorage implements Listener {

    private SQLiteDataSource source;
    private Connection connection;

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
                    "PRAGMA synchronous = OFF;\n" +
                            "CREATE TABLE \"ultralucky\" (\n" +
                            "  \"Location\" text(200,1) NOT NULL\n" +
                            ");\n" +
                            "PRAGMA foreign_keys = true;\n" +
                            "BEGIN;\n");
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        AsyncFuture<Void> asyncFuture = new AsyncFuture<>(() -> {
            if (!query(event.getBlock().getLocation())) {
                storage(event.getBlock().getLocation());
            }
            return null;
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        if (event.getPlayer() == null) return;
        if (!event.canBuild()) return;
        AsyncFuture<Void> asyncFuture = new AsyncFuture<>(() -> {
            if (!query(event.getBlock().getLocation())) {
                storage(event.getBlock().getLocation());
            }
            return null;
        });
        asyncFuture.execute();
    }

    public boolean query(Location location) {
        String str = locationToString(location);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Location FROM ultralucky GROUP BY Location HAVING Location = '" + str + "';");
            boolean status = preparedStatement.executeQuery().next();
            return status;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void storage(Location location) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ultralucky (\"Location\") VALUES ('" + locationToString(location) + "');");
            preparedStatement.executeUpdate();
            connection.prepareStatement("COMMIT;").execute();
            connection.prepareStatement("BEGIN;").execute();
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
            connection.prepareStatement("COMMIT;").execute();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
