package net.sereinfish.mc.particle.particle;

import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.sereinfish.mc.particle.Start;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 粒子名称映射
 */
public class ParticleNameMapping {
    public static ParticleNameMapping INSTANCE;

    private Map<String, DefaultParticleType> map = new HashMap<>();

    private ParticleNameMapping(){
        //初始化
        for (Field field: ParticleTypes.class.getDeclaredFields()){
            if (DefaultParticleType.class == field.getType()){
                try {
                    map.put(((DefaultParticleType) field.get(ParticleTypes.class)).asString(), (DefaultParticleType) field.get(ParticleTypes.class));
                    //Start.LOGGER.info("{}", ((DefaultParticleType) field.get(ParticleTypes.class)).asString());
                } catch (IllegalAccessException e) {
                    Start.LOGGER.error("粒子效果名称映射初始化失败：{}", field.getName(), e);
                }
            }
        }
        Start.LOGGER.info("粒子效果名称映射初始化完成，共加载 {}", map.size());
    }

    public static void init(){
        INSTANCE = new ParticleNameMapping();
    }

    public DefaultParticleType get(String name){
        DefaultParticleType type = null;
        type = map.get(name);
        if (type != null){
            return type;
        }

        for (String n: map.keySet()){
            if (n.equalsIgnoreCase(name)){
                type = map.get(n);
                break;
            }else {
                if (n.contains(":")){
                    if (n.split(":")[1].equalsIgnoreCase(name)){
                        type = map.get(n);
                        break;
                    }
                }
            }
        }

        return type;
    }
}
