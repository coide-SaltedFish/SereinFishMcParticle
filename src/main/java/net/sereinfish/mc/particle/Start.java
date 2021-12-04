package net.sereinfish.mc.particle;

import net.fabricmc.api.ModInitializer;
import net.sereinfish.mc.particle.command.ModCommand;
import net.sereinfish.mc.particle.config.ModConfig;
import net.sereinfish.mc.particle.particle.ParticleManager;
import net.sereinfish.mc.particle.particle.ParticleNameMapping;
import net.sereinfish.mc.particle.particle.TickTaskManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Start implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("SereinFishMod");
	public static ModConfig modConfig;

	@Override
	public void onInitialize() {
		LOGGER.info("sereinfish-mc-particle Start");
		try {
			modConfig = ModConfig.read();
			LOGGER.info("Config init success!");
		} catch (Exception e) {
			LOGGER.error("Config init fail!", e);
			LOGGER.error("sereinfish-mc-particle init fail!");
			return;//停止初始化mod
		}
		ParticleNameMapping.init();
		ParticleManager.init();
		Start.LOGGER.info("ParticleManager init success!");
		TickTaskManager.init();
		Start.LOGGER.info("TickTaskManager init success!");
		ModCommand.init();
		Start.LOGGER.info("Command init success!");
		LOGGER.info("sereinfish-mc-particle init success!");
	}
}
