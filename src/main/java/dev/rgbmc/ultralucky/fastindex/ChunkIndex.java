package dev.rgbmc.ultralucky.fastindex;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ChunkIndex implements Serializable {
    private final int x;
    private final int z;
    private final String world;
    private Map<String, IndexData> index;

    public ChunkIndex(int x, int z, String world) {
        this.x = x;
        this.z = z;
        this.world = world;
        index = new HashMap<>();
    }

    public Map<String, IndexData> getIndex() {
        return index;
    }

    public void setIndex(Map<String, IndexData> index) {
        this.index = index;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public String getWorld() {
        return world;
    }
}
