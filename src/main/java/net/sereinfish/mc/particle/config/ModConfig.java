package net.sereinfish.mc.particle.config;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.sereinfish.mc.particle.Start;

import java.io.*;
import java.util.*;

public class ModConfig implements Serializable {
    @Serial
    private static final long serialVersionUID = 12345L;

    private static final File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "SFParticleModConfig.bin");

    private Map<String,ModConfigData> configList = new HashMap<>();

    /**
     * 得到指定玩家的配置
     * @param player
     * @return
     */
    public ModConfigData get(PlayerEntity player){
        if (!configList.containsKey(player.getUuid().toString())){
            configList.put(player.getUuid().toString(), new ModConfigData(this));
            save();
        }
        return configList.get(player.getUuid().toString()).setModConfig(this);
    }


    /**
     * 保存
     */
    public void save(){
        try {
//            FileHandle.write(configFile, JSON.toJSONString(this));
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(configFile));
            os.writeObject(this); // 将User对象写进文件
            os.flush();
            os.close();
        } catch (IOException e) {
            Start.LOGGER.error("配置保存失败", e);
        }
    }

    public Map<String, ModConfigData> getConfigList() {
        return configList;
    }

    public void setConfigList(Map<String, ModConfigData> configList) {
        this.configList = configList;
    }

    /**
     * 得到配置
     * @return
     * @throws IOException
     */
    public static ModConfig read() throws IOException, ClassNotFoundException {
        if (configFile.exists() && configFile.isFile()){
//            String json = FileHandle.read(configFile);
//            return JSON.parseObject(json, ModConfig.class);
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(configFile));
            ModConfig modConfig = (ModConfig) is.readObject(); // 从流中读取User的数据
            is.close();
            return modConfig;
        }else {
            ModConfig modConfig = new ModConfig();
            modConfig.save();
            return modConfig;
        }
    }
}
