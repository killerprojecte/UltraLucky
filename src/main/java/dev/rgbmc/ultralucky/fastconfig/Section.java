package dev.rgbmc.ultralucky.fastconfig;

import java.util.List;

public interface Section {
    Object get(String key);

    default String getString(String key) {
        return (String) get(key);
    }

    default int getInt(String key) {
        return (Integer) get(key);
    }

    default long getLong(String key) {
        return (Long) get(key);
    }

    default short getShort(String key) {
        return (Short) get(key);
    }

    default double getDouble(String key) {
        return (Double) get(key);
    }

    default float getFloat(String key) {
        return (Float) get(key);
    }

    default boolean getBoolean(String key) {
        return (Boolean) get(key);
    }

    default Object getObject(String key) {
        return get(key);
    }

    default List getList(String key) {
        return (List) get(key);
    }

    default List<String> getStringList(String key) {
        return (List<String>) get(key);
    }

    default List<Integer> getIntList(String key) {
        return (List<Integer>) get(key);
    }

    default List<Long> getLongList(String key) {
        return (List<Long>) get(key);
    }

    default List<Short> getShortList(String key) {
        return (List<Short>) get(key);
    }

    default List<Double> getDoubleList(String key) {
        return (List<Double>) get(key);
    }

    default List<Float> getFloatList(String key) {
        return (List<Float>) get(key);
    }

    default List<Boolean> getBooleanList(String key) {
        return (List<Boolean>) get(key);
    }

    default Section getSection(String key) {
        return (Section) get(key);
    }

    default boolean isSection(String key) {
        return get(key) instanceof Section;
    }

    List<String> getKeys(boolean deep);

    default boolean contains(String key) {
        return get(key) != null;
    }
}
