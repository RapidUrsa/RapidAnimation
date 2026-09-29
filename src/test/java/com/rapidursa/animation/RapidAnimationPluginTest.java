package com.rapidursa.animation;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class RapidAnimationPluginTest
{
    public static void main(String[] args) throws Exception
    {
        ExternalPluginManager.loadBuiltin(RapidAnimationPlugin.class);
        RuneLite.main(args);
    }
}
