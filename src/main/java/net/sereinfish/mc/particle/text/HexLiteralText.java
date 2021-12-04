package net.sereinfish.mc.particle.text;

import net.minecraft.entity.boss.BossBar;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

public class HexLiteralText extends LiteralText {
    public HexLiteralText(String string) {
        super(string);
    }

    public MutableText formatted(String hex) {
        if (hex.matches("#[0-9a-fA-f]{6}")){
            this.setStyle(this.getStyle().withColor(BossBar.Color.valueOf(hex).hashCode()));
            return this;
        }

        if (Formatting.byName(hex) != null){
            this.setStyle(this.getStyle().withFormatting(Formatting.byName(hex)));
            return this;
        }

        this.setStyle(this.getStyle().withFormatting(Formatting.WHITE));
        return this;
    }
}
