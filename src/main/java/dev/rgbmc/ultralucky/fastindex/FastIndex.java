package dev.rgbmc.ultralucky.fastindex;

import dev.rgbmc.ultralucky.fastindex.listeners.BlockListener;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;

public class FastIndex {
    private static final Timer timer = new Timer("FastIndex-Save-Timer");
    public static Logger LOGGER = Logger.getLogger("FastIndex");
    protected static File DIRECTORY = new File(System.getProperty("user.dir") + "/fastindex/");
    private static ExecutorService executor;
    private static boolean first = true;
    private static boolean first_block = true;
    private static boolean lockChunk = false;
    private static long count = 0L;
    private static Plugin register;

    public static void initIndex(Plugin plugin) {
        executor = Executors.newFixedThreadPool(plugin.getConfig().getInt("indexer-cores"), new IndexThreadFactory());
        register = plugin;
        executor.execute(() -> {
            LOGGER.info("FastIndex 快速索引器正在初始化...");
            if (!DIRECTORY.exists()) {
                LOGGER.info("检测到这是第一次加载FastIndex正在创建索引库");
                DIRECTORY.mkdirs();
            }
            File lock = new File(DIRECTORY, "init.lock");
            if (!lock.exists()) {
                lockChunk = true;
                LOGGER.info("正在初始化FastIndex索引库");
                new Thread(() -> {
                    for (World world : Bukkit.getWorlds()) {
                        Path saveFolder = world.getWorldFolder().toPath()
                                .resolve(getDIM(world.getEnvironment()))
                                .toAbsolutePath().normalize();
                        if (!Files.exists(saveFolder)) {
                            Path direct = world.getWorldFolder().toPath();
                            if (Files.exists(direct) && direct.endsWith(getDIM(world.getEnvironment())))
                                saveFolder = direct;
                        }
                        Path region_path = saveFolder.resolve("region");
                        File region_folder = region_path.toFile();
                        if (!region_folder.exists()) {
                            LOGGER.warning("无法读取世界: " + world.getName() + " 的区块文件 已跳过索引");
                            continue;
                        }
                        File[] files = region_folder.listFiles(file -> file.getName().endsWith(".mca"));
                        int size = files.length;
                        for (int i = 0; i < size; i++) {
                            File mca_file = files[i];
                            count++;
                            // Example Name: r.4.-4.mca
                            String name = mca_file.getName().replaceFirst("r\\.", "").replaceFirst("\\.mca", "");
                            String[] coordinate = name.split("\\.");
                            int x = Integer.parseInt(coordinate[0]);
                            int z = Integer.parseInt(coordinate[1]);
                            //world.loadChunk(x, z);
                            updateChunk(world.getChunkAt(x, z), IndexState.INIT, true);
                        }
                    }
                    while (count > 0L) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    lockChunk = false;
                }, "FastIndex-Initiator-Thread").start();
                try {
                    lock.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Bukkit.getPluginManager().registerEvents(new BlockListener(), plugin);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                flushAll();
            }
        }, 1000L * 60L * 5L, 1000L * 60L * 5L);
    }

    protected static void updateIndex(Block block, IndexState indexState) {
        IOUtils.writeIndex(block.getChunk(), block, indexState);
    }

    public static void updateIndex(Block block) {
        executor.execute(() -> updateIndex(block, IndexState.CREATE));
    }

    public static void updateIndex(Location location, Material material) {
        executor.execute(() -> updateIndex(location.getBlock()));
    }

    public static IndexData getIndex(Block block) {
        if (!isIndexed(block)) {
            throw new RuntimeException(new IllegalStateException("The block is not indexed (" + block.getX() + "," + block.getY() + "," + block.getZ() + ")"));
        }
        return IndexData.read(block.getChunk(), block);
    }

    public static boolean isIndexed(Block block) {
        return IOUtils.isIndexed(block.getChunk(), block);
    }

    public static boolean isIndexed(Location location) {
        return IOUtils.isIndexed(location.getChunk(), location.getBlock());
    }

    public static void updateChunk(Chunk chunk, IndexState indexState, boolean unloadChunk) {
        int minY;
        int maxY = chunk.getWorld().getMaxHeight();
        try {
            Method min_height_method = chunk.getWorld().getClass().getDeclaredMethod("getMinHeight");
            minY = (int) min_height_method.invoke(chunk.getWorld());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {
            minY = 0;
        }
        int temp = minY;
        executor.execute(() -> {
            long time = System.currentTimeMillis();
            final int tempY = temp;
            final int tempMaxY = maxY;
            int blocks = 0;
            for (int y = tempY; y < tempMaxY; y++) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        long block_time = System.currentTimeMillis();
                        Block block = chunk.getBlock(x, y, z);
                        if (first_block) {
                            LOGGER.info("从区块获取方块耗时 " + (System.currentTimeMillis() - block_time) + "ms");
                        }
                        block_time = System.currentTimeMillis();
                        updateIndex(block, indexState);
                        if (first_block) {
                            LOGGER.info("保存方块索引耗时 " + (System.currentTimeMillis() - block_time) + "ms");
                            first_block = false;
                        }
                        blocks++;
                    }
                }
            }
            IOUtils.flush(chunk);
            if (first) {
                first = false;
                LOGGER.info("更新索引 " + blocks + " 条 耗时 " + (System.currentTimeMillis() - time) + "ms");
            }
            if (unloadChunk) {
                Bukkit.getScheduler().runTask(register, () -> chunk.getWorld().unloadChunk(chunk));
            }
            System.gc();
        });
    }

    public static void updateChunk(Chunk chunk, IndexState indexState) {
        updateChunk(chunk, indexState, false);
    }

    public static void updateChunk(Chunk chunk) {
        updateChunk(chunk, IndexState.CREATE);
    }

    public static void flush(Chunk chunk) {
        IOUtils.flush(chunk);
    }

    public static void flushAll() {
        IOUtils.flushAll();
    }

    public static void flushAsync(Chunk chunk) {
        CompletableFuture.runAsync(() -> flush(chunk));
    }

    public static void flushAllAsync() {
        CompletableFuture.runAsync(FastIndex::flushAll);
    }

    private static String getDIM(World.Environment environment) {
        switch (environment) {
            case THE_END: {
                return "DIM1";
            }
            case NETHER: {
                return "DIM-1";
            }
            default: {
                return "";
            }
        }
    }

    public static boolean isLockChunk() {
        return lockChunk;
    }

    public static void close() {
        IOUtils.flushAll();
        executor.shutdown();
    }

    private static class IndexThreadFactory implements ThreadFactory {
        private int counter = 0;

        @Override
        public Thread newThread(@NotNull Runnable r) {
            Thread thread = new Thread(r, "FastIndex-Indexer-" + counter);
            counter++;
            return thread;
        }
    }
}
