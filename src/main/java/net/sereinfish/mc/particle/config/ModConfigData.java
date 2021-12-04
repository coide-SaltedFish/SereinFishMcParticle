package net.sereinfish.mc.particle.config;

import java.io.Serial;
import java.io.Serializable;

public class ModConfigData implements Serializable {
    @Serial
    private static final long serialVersionUID = 12345L;

    private transient  ModConfig modConfig;

    private boolean allEnable = true;//总开关

    private boolean otherEnable = true;//看别人

    private boolean onlyOwnEnable = false;//不给别人看

    private boolean onlyOtherEnable = false;//

    private boolean prefixEnable = true;//是否启用自己的前缀

    private boolean suffixEnable = true;//是否启用自己的后缀

    public ModConfigData(){}

    public ModConfigData(ModConfig config){
        this.modConfig = config;
    }

    public ModConfigData setModConfig(ModConfig modConfig) {
        this.modConfig = modConfig;
        return this;
    }

    public void allEnable(boolean allEnable) {
        this.allEnable = allEnable;
        modConfig.save();
    }

    public void otherEnable(boolean otherEnable) {
        this.otherEnable = otherEnable;
        modConfig.save();
    }

    public void onlyOwnEnable(boolean onlyOwnEnable) {
        this.onlyOwnEnable = onlyOwnEnable;
        modConfig.save();
    }

    public void onlyOtherEnable(boolean onlyOtherEnable) {
        this.onlyOtherEnable = onlyOtherEnable;
        modConfig.save();
    }

    public void prefixEnable(boolean prefixEnable) {
        this.prefixEnable = prefixEnable;
        modConfig.save();
    }

    public void suffixEnable(boolean suffixEnable) {
        this.suffixEnable = suffixEnable;
        modConfig.save();
    }

    public void setAllEnable(boolean allEnable) {
        this.allEnable = allEnable;
    }

    public void setOtherEnable(boolean otherEnable) {
        this.otherEnable = otherEnable;
    }

    public void setOnlyOwnEnable(boolean onlyOwnEnable) {
        this.onlyOwnEnable = onlyOwnEnable;
    }

    public void setOnlyOtherEnable(boolean onlyOtherEnable) {
        this.onlyOtherEnable = onlyOtherEnable;
    }

    public void setPrefixEnable(boolean prefixEnable) {
        this.prefixEnable = prefixEnable;
    }

    public void setSuffixEnable(boolean suffixEnable) {
        this.suffixEnable = suffixEnable;
    }

    public boolean isAllEnable() {
        return allEnable;
    }

    public boolean isOtherEnable() {
        return otherEnable;
    }

    public boolean isOnlyOwnEnable() {
        return onlyOwnEnable;
    }

    public boolean isOnlyOtherEnable() {
        return onlyOtherEnable;
    }

    public boolean isPrefixEnable() {
        return prefixEnable;
    }

    public boolean isSuffixEnable() {
        return suffixEnable;
    }
}
