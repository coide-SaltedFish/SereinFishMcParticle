package net.sereinfish.mc.particle.entity;

import net.minecraft.nbt.NbtCompound;

import java.util.Calendar;

public class ParticleNBT {
    private String name = "";

    private String prefix = "";
    private String prefixColor = "";

    private String suffix = "";
    private String suffixColor = "";

    //前缀有效时间，为-1表示任意时间
    private int prefixTimeYear = -1;
    private int prefixTimeMonth = -1;
    private int prefixTimeDay = -1;

    private boolean longDistance = false;

    private boolean force = false;

    private long interval = 60;//两次粒子生成的时间间隔

    private float offsetX = 0;
    private float offsetY = 0;
    private float offsetZ = 0;

    //随机半径
    private float randomX = 0;
    private float randomY = 0;
    private float randomZ = 0;

    private float speed = 1;
    private int count = 1;

    public ParticleNBT(NbtCompound nbtCompound){
        if (nbtCompound.contains("name"))
            name = nbtCompound.getString("name");

        if (nbtCompound.contains("longDistance"))
            longDistance = nbtCompound.getBoolean("longDistance");

        if (nbtCompound.contains("offsetX"))
            offsetX = nbtCompound.getFloat("offsetX");

        if (nbtCompound.contains("offsetY"))
            offsetY = nbtCompound.getFloat("offsetY");

        if (nbtCompound.contains("offsetZ"))
            offsetZ = nbtCompound.getFloat("offsetZ");

        if (nbtCompound.contains("speed"))
            speed = nbtCompound.getFloat("speed");

        if (nbtCompound.contains("count"))
            count = nbtCompound.getInt("count");

        if (nbtCompound.contains("force"))
            force = nbtCompound.getBoolean("force");

        if (nbtCompound.contains("prefix"))
            prefix = nbtCompound.getString("prefix");

        if (nbtCompound.contains("prefixColor"))
            prefixColor = nbtCompound.getString("prefixColor");

        if (nbtCompound.contains("suffix"))
            suffix = nbtCompound.getString("suffix");

        if (nbtCompound.contains("suffixColor"))
            suffixColor = nbtCompound.getString("suffixColor");


        if (nbtCompound.contains("prefixTimeYear"))
            prefixTimeYear = nbtCompound.getInt("prefixTimeYear");

        if (nbtCompound.contains("prefixTimeMonth"))
            prefixTimeMonth = nbtCompound.getInt("prefixTimeMonth");

        if (nbtCompound.contains("prefixTimeDay"))
            prefixTimeDay = nbtCompound.getInt("prefixTimeDay");

        if (nbtCompound.contains("randomX"))
            randomX = nbtCompound.getInt("randomX");

        if (nbtCompound.contains("randomY"))
            randomY = nbtCompound.getInt("randomY");

        if (nbtCompound.contains("randomZ"))
            randomZ = nbtCompound.getInt("randomZ");
    }

    /**
     * 判断当前前缀是否生效
     * @return
     */
    public boolean isPrefixEnable(){
        Calendar calendar = Calendar.getInstance();
        if (prefixTimeYear != -1){
            if (calendar.get(Calendar.YEAR) != prefixTimeYear){
                return false;
            }
        }

        if (prefixTimeMonth != -1){
            if (calendar.get(Calendar.MONTH) + 1 != prefixTimeMonth){
                return false;
            }
        }

        if (prefixTimeDay != -1){
            if (calendar.get(Calendar.DAY_OF_MONTH) != prefixTimeDay){
                return false;
            }
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getPrefixColor() {
        return prefixColor;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getSuffixColor() {
        return suffixColor;
    }

    public int getPrefixTimeYear() {
        return prefixTimeYear;
    }

    public int getPrefixTimeMonth() {
        return prefixTimeMonth;
    }

    public int getPrefixTimeDay() {
        return prefixTimeDay;
    }

    public boolean isLongDistance() {
        return longDistance;
    }

    public boolean isForce() {
        return force;
    }

    public long getInterval() {
        return interval;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public float getOffsetZ() {
        return offsetZ;
    }

    public float getRandomX() {
        return randomX;
    }

    public float getRandomY() {
        return randomY;
    }

    public float getRandomZ() {
        return randomZ;
    }

    public float getSpeed() {
        return speed;
    }

    public int getCount() {
        return count;
    }
}
