package com.zundrel.ollivanders.api.event;

/**
 * This event is fired serverside after a player chooses a new spell
 * This event is cancelable, and has no result
 * This event is fired on MinecraftForge#EVENT_BUS
 */
public class PlayerSpellChooseEvent
{
//    private final PlayerEntity player;
//    private final String message;
//    private final ISpell previousSpell;
//    private ISpell newSpell;
//
//    public PlayerSpellChooseEvent(@Nonnull PlayerEntity entityPlayer, String message, ISpell previousSpell, @Nonnull ISpell newSpell)
//    {
//        this.player = entityPlayer;
//        this.message = message;
//        this.previousSpell = previousSpell;
//        this.newSpell = newSpell;
//    }
//
//    /**
//     * @return The player whose spell changed. The associated player will be logged in when this event fires.
//     */
//    @Nonnull
//    public PlayerEntity getPlayer()
//    {
//        return player;
//    }
//
//    /**
//     * @return The message containing info about the spell's verbal name (and possibly more info in some cases).
//     */
//    public String getMessage() {
//        return message;
//    }
//
//    /**
//     * @return The previous spell this player had selected. More often than not this will be null.
//     */
//    public ISpell getPreviousSpell() {
//        return previousSpell;
//    }
//
//    /**
//     * @return The spell this player has just selected. Will never be null.
//     */
//    @Nonnull
//    public ISpell getNewSpell() {
//        return newSpell;
//    }
//
//    /**
//     * @param newSpell The spell you want to change the player's new spell to.
//     *
//     * @return The spell this player has just selected. Will never be null.
//     */
//    public void setNewSpell(ISpell newSpell) {
//        this.newSpell = newSpell;
//    }
}