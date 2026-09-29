package com.rapidursa.animation;

import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.api.ActorSpotAnim;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
    name = "Rapid Animation",
    description = "Replace standard spellbook teleport visuals with the quetzal animation and bird",
    tags = {"teleport", "animation", "quetzal", "visual"}
)
public class RapidAnimationPlugin extends Plugin
{
    private static final int STANDARD_SPELL_TELEPORT = 714;
    private static final int QUETZAL_PLAYER_ANIMATION = 10944;
    private static final int QUETZAL_GRAPHIC = 2735;
    private static final int PLAYER_START_FRAME = 25;
    private static final int GRAPHIC_START_FRAME = 1;
    private static final int GRAPHIC_DELAY_TICKS = 38;
    private static final int MAX_TICKS = 120;
    private static final int PREVIEW_SPOT_KEY = 0x52A71;

    @Inject private Client client;
    @Inject private ClientThread clientThread;
    @Inject private RapidAnimationConfig config;

    private int activeTicks;
    private WorldPoint origin;
    private boolean applying;
    private boolean graphicStarted;

    @Provides
    RapidAnimationConfig provideConfig(ConfigManager manager)
    {
        return manager.getConfig(RapidAnimationConfig.class);
    }

    @Override
    protected void shutDown()
    {
        clientThread.invoke(() -> stop(client.getLocalPlayer()));
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged event)
    {
        if (event.getGameState() == GameState.LOADING || event.getGameState() == GameState.LOGIN_SCREEN)
        {
            stop(client.getLocalPlayer());
        }
    }

    @Subscribe
    public void onAnimationChanged(AnimationChanged event)
    {
        Player player = client.getLocalPlayer();
        if (player == null || event.getActor() != player || applying || !config.enabled())
        {
            return;
        }
        if (player.getAnimation() == STANDARD_SPELL_TELEPORT)
        {
            if (activeTicks == 0)
            {
                origin = player.getWorldLocation();
                activeTicks = 1;
                graphicStarted = false;
            }
            applyPlayerAnimation(player);
        }
    }

    @Subscribe
    public void onClientTick(ClientTick ignored)
    {
        if (activeTicks == 0)
        {
            return;
        }
        Player player = client.getLocalPlayer();
        if (player == null || !config.enabled() || !player.getWorldLocation().equals(origin)
            || ++activeTicks > MAX_TICKS)
        {
            stop(player);
            return;
        }
        if (player.getAnimation() == STANDARD_SPELL_TELEPORT)
        {
            applyPlayerAnimation(player);
        }
        if (!graphicStarted && activeTicks >= GRAPHIC_DELAY_TICKS)
        {
            startGraphic(player);
        }
    }

    private void applyPlayerAnimation(Player player)
    {
        applying = true;
        try
        {
            player.setAnimation(QUETZAL_PLAYER_ANIMATION);
            player.setAnimationFrame(PLAYER_START_FRAME);
        }
        finally
        {
            applying = false;
        }
    }

    private void startGraphic(Player player)
    {
        // Player animation updates must not restart the bird's timeline.
        graphicStarted = true;
        player.createSpotAnim(PREVIEW_SPOT_KEY, QUETZAL_GRAPHIC, 0, 0);
        for (ActorSpotAnim effect : player.getSpotAnims())
        {
            if (effect.getHash() == PREVIEW_SPOT_KEY)
            {
                effect.setFrame(GRAPHIC_START_FRAME);
                effect.setCycle(0);
                break;
            }
        }
    }

    private void stop(Player player)
    {
        if (player != null)
        {
            player.removeSpotAnim(PREVIEW_SPOT_KEY);
        }
        graphicStarted = false;
        activeTicks = 0;
        origin = null;
    }
}
