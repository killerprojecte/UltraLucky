package dev.rgbmc.ultralucky.fastindex;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

public class IndexData implements Serializable {
    private Material material;
    private IndexState state;

    protected IndexData(Material material, IndexState state) {
        this.material = material;
        this.state = state;
    }

    protected static IndexData read(Chunk chunk, Block block) {
        File file = new File(FastIndex.DIRECTORY, chunk.getWorld().getName() + "/" + chunk.getX() + "_" + chunk.getZ() + ".index");
        ChunkIndex chunkIndex = IOUtils.getChunkIndex(file, chunk);
        IndexData indexData = chunkIndex.getIndex().get(block.getX() + "_" + block.getY() + "_" + block.getZ());
        return new IndexData(indexData.getMaterial(), indexData.getState());
    }

    public IndexState getState() {
        return state;
    }

    public void setState(IndexState state) {
        this.state = state;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    protected void write(Chunk chunk, Block block) {
        File file = new File(FastIndex.DIRECTORY, chunk.getWorld().getName() + "/" + chunk.getX() + "_" + chunk.getZ() + ".index");
        ChunkIndex chunkIndex = IOUtils.getChunkIndex(file, chunk);
        Map<String, IndexData> index = chunkIndex.getIndex();
        index.put(block.getX() + "_" + block.getY() + "_" + block.getZ(), this);
        chunkIndex.setIndex(index);
        IOUtils.chunkIndexMap.put(IOUtils.getChunkKey(chunk), chunkIndex);
    }

    protected void write(Location location) {
        Chunk chunk = location.getChunk();
        File file = new File(FastIndex.DIRECTORY, chunk.getWorld().getName() + "/" + chunk.getX() + "_" + chunk.getZ() + ".index");
        ChunkIndex chunkIndex = IOUtils.getChunkIndex(file, chunk);
        Map<String, IndexData> index = chunkIndex.getIndex();
        index.put(location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ(), this);
        chunkIndex.setIndex(index);
        IOUtils.chunkIndexMap.put(IOUtils.getChunkKey(chunk), chunkIndex);
    }
}
