package com.rapidursa.animation;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("rapidanimation")
public interface RapidAnimationConfig extends Config
{
    @ConfigItem(keyName = "enabled", name = "Replace spell teleports", description = "Show the quetzal animation and bird during standard spellbook teleports. Visual only.", position = 0)
    default boolean enabled() { return true; }
}
