package dev.rgbmc.ultralucky.fastindex;

import org.bukkit.Chunk;
import org.bukkit.block.Block;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IOUtils {
    protected static Map<String, ChunkIndex> chunkIndexMap = new HashMap<>();

    protected static void writeIndex(Chunk chunk, Block block, IndexState state) {
        if (!chunkIndexMap.containsKey(getChunkKey(chunk))) {
            chunkIndexMap.put(getChunkKey(chunk), new ChunkIndex(chunk.getX(), chunk.getZ(), chunk.getWorld().getName()));
        }
        IndexData indexData;
        if (FastIndex.isIndexed(block)) {
            indexData = FastIndex.getIndex(block);
            indexData.setMaterial(block.getType());
            indexData.setState(state);
        } else {
            indexData = new IndexData(block.getType(), state);
        }
        indexData.write(chunk, block);
    }

    protected static boolean isIndexed(Chunk chunk, Block block) {
        File file = new File(FastIndex.DIRECTORY, chunk.getWorld().getName() + "/" + chunk.getX() + "_" + chunk.getZ() + ".index");
        if (!file.exists()) return false;
        ChunkIndex chunkIndex = getChunkIndex(file, chunk);
        boolean exist = chunkIndex.getIndex().containsKey(block.getX() + "_" + block.getY() + "_" + block.getZ());
        //FastIndex.LOGGER.info(String.valueOf(exist));
        return exist;
    }

    protected static ChunkIndex getChunkIndex(File file, Chunk chunk) {
        if (chunkIndexMap.containsKey(getChunkKey(chunk))) {
            return chunkIndexMap.get(getChunkKey(chunk));
        }
        try {
            if (!file.exists()) {
                return new ChunkIndex(chunk.getX(), chunk.getZ(), chunk.getWorld().getName());
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ChunkIndex chunkIndex = (ChunkIndex) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            chunkIndexMap.put(getChunkKey(chunk), chunkIndex);
            return chunkIndex;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected static String readFile(File file) {
        try {
            return Files.readString(file.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected static void writeFile(File file, String content) {
        try {
            Files.writeString(file.toPath(), content, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected static void writeChunkIndex(File file, ChunkIndex chunkIndex, String chunk_key) {
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(chunkIndex);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected static void writeChunkIndex(ChunkIndex chunkIndex) {
        File file = new File(FastIndex.DIRECTORY, chunkIndex.getWorld() + "/" + chunkIndex.getX() + "_" + chunkIndex.getZ() + ".index");
        writeChunkIndex(file, chunkIndex, getChunkKey(chunkIndex));
    }

    protected static String getChunkKey(Chunk chunk) {
        return chunk.getWorld().getName() + "_" + chunk.getX() + "_" + chunk.getZ();
    }

    protected static String getChunkKey(ChunkIndex chunkIndex) {
        return chunkIndex.getWorld() + "_" + chunkIndex.getX() + "_" + chunkIndex.getZ();
    }

    protected static void flushAll() {
        for (ChunkIndex value : List.copyOf(chunkIndexMap.values())) {
            writeChunkIndex(value);
        }
    }

    protected static void flush(Chunk chunk) {
        writeChunkIndex(chunkIndexMap.get(IOUtils.getChunkKey(chunk)));
    }

    protected static void flush(String chunk_key) {
        writeChunkIndex(chunkIndexMap.get(chunk_key));
    }
}
