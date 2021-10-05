package src;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    @Test
    public void testInheritance(){
        Player player = new Player();
        Creature creature = new Creature(1000,10);

        player.hurt(5);

        assertTrue(player.getCurrentHealth() + 5 == player.getHealth());

        creature.giveSpell(SpellType.FIRE);

        int tmpHealth = player.getCurrentHealth();

        creature.setOnFire(player);

        assertTrue(player.isOnFire());

        player.decFireTimer();
        player.decFireTimer();
        player.decFireTimer();
        player.decFireTimer();

        assertFalse(player.isOnFire());

        assertTrue(player.getCurrentHealth() < tmpHealth);

        assertFalse(player.isFrozen());

        creature.giveSpell(SpellType.FROST);
        creature.freeze(player);

        assertTrue(player.isFrozen());

        creature.giveSpell(SpellType.LIGHTNING);
        player.setCurrentHealth(player.getHealth());

        creature.electrocute(player);

        assertTrue(player.getCurrentHealth() + creature.getStrength() == player.getHealth());

        assertTrue(player.isDead());
    }

    @Test
    public void testXP(){
        Player player = new Player();

        player.addXP(50);
        assertTrue(player.getXP() == 50);

        player.addXP(-50);
        assertTrue(player.getXP() == 0);

        player.addXP(-50);
        assertTrue(player.getXP() == -50);

        player.addXP(50);
        assertTrue(player.getXP() == 0);
    }

    @Test
    public void testCanLevelUp(){
        Player player = new Player();

        assertFalse(player.canLevelUp());
        player.addXP(99);
        assertFalse(player.canLevelUp());
        player.addXP(1);
        assertTrue(player.canLevelUp());
    }

    @Test
    public void testLevelUp(){
        Player player = new Player();
        player.setCurrentHealth(10);

        player.levelUp(SpellType.HEAL);

        assertTrue(player.getCurrentHealth() == 10);
        String test = player.getSpellCasts();

        assertFalse(test.contains("1"));

        player.addXP(1000000);
        int strength = player.getStrength();
        int health = player.getHealth();
        player.levelUp(SpellType.HEAL);
        test = player.getSpellCasts();
        assertTrue(strength + 5 == player.getStrength());
        assertTrue(health + 10 == player.getHealth());
        assertTrue(player.getCurrentHealth() == player.getCurrentHealth());
        
        assertTrue(test.contains("1"));

        for(int i = 0 ; i < 4; i++){
            strength = player.getStrength();
            health = player.getHealth();
            player.levelUp(SpellType.HEAL);
            assertTrue(strength + 5 == player.getStrength());
            assertTrue(health + 10 == player.getHealth());
            assertTrue(player.getCurrentHealth() == player.getCurrentHealth());
        }

        for(int i = 0 ; i < 4; i++){
            strength = player.getStrength();
            health = player.getHealth();
            player.levelUp(SpellType.LIGHTNING);
            assertTrue(strength + 5 == player.getStrength());
            assertTrue(health + 10 == player.getHealth());
            assertTrue(player.getCurrentHealth() == player.getCurrentHealth());
        }

        for(int i = 0 ; i < 3; i++){
            strength = player.getStrength();
            health = player.getHealth();
            player.levelUp(SpellType.FROST);
            assertTrue(strength + 5 == player.getStrength());
            assertTrue(health + 10 == player.getHealth());
            assertTrue(player.getCurrentHealth() == player.getCurrentHealth());
        }

        for(int i = 0 ; i < 2; i++){
            strength = player.getStrength();
            health = player.getHealth();
            player.levelUp(SpellType.FIRE);
            assertTrue(strength + 5 == player.getStrength());
            assertTrue(health + 10 == player.getHealth());
            assertTrue(player.getCurrentHealth() == player.getCurrentHealth());
        }

        test = player.getSpellCasts();

        //Test the count of spell casts.
        assertTrue(test.contains("5"));
        assertTrue(test.contains("4"));
        assertTrue(test.contains("3"));
        assertTrue(test.contains("2"));
    }

    @Test
    public void testPlayerSpells(){
        Player player = new Player();
        Creature creature = new Creature(100,100);
        player.setCurrentHealth(50);
        player.setHealth(100);
        

        //Test with no spell casts.
        player.setOnFire(creature);
        player.freeze(creature);
        player.heal(50);
        player.electrocute(creature);

        assertFalse(creature.isFrozen());
        assertFalse(creature.isOnFire());
        assertFalse(player.getCurrentHealth() == player.getHealth());
        assertFalse(creature.getCurrentHealth() < creature.getHealth());

        //Test with spell casts but frozen.
        creature.giveSpell(SpellType.FROST);
        player.addXP(1000);
        player.levelUp(SpellType.FIRE);
        player.levelUp(SpellType.HEAL);
        player.levelUp(SpellType.LIGHTNING);
        player.levelUp(SpellType.FROST);
        player.setCurrentHealth(50);
        player.setHealth(100);
        creature.freeze(player);
        player.heal(50);
        player.freeze(creature);
        player.setOnFire(creature);
        player.electrocute(creature);
        assertFalse(creature.isFrozen());
        assertFalse(creature.isOnFire());
        assertFalse(player.getCurrentHealth() == player.getHealth());
        assertFalse(creature.getCurrentHealth() < creature.getHealth());

        player.decFreezeTimer();
        player.decFreezeTimer();
        player.decFreezeTimer();
        player.decFreezeTimer();

        //Test when not frozen or dead.
        player.setOnFire(creature);
        assertTrue(creature.isOnFire());

        player.freeze(creature);
        assertTrue(creature.isFrozen());

        creature.decFreezeTimer();
        creature.decFreezeTimer();
        creature.decFreezeTimer();
        creature.decFreezeTimer();

        assertFalse(creature.isFrozen());
        player.freeze(creature);
        assertFalse(creature.isFrozen());

        creature.decFireTimer();
        creature.decFireTimer();
        creature.decFireTimer();
        creature.decFireTimer();
        
        assertFalse(creature.isOnFire());
        player.setOnFire(creature);
        assertFalse(creature.isOnFire());

        player.hurt(10);
        int curHealth = player.getCurrentHealth();
        player.heal(25);
        assertTrue(player.getCurrentHealth() == curHealth + 25);
        player.heal(25);
        assertFalse(player.getCurrentHealth() == player.getHealth());

        player.setStrength(50);
        creature.setCurrentHealth(100);
        creature.setHealth(100);
        player.electrocute(creature);
        
        assertTrue(creature.getHealth() == creature.getCurrentHealth() + 50);
        player.electrocute(creature);
        assertTrue(creature.getHealth() == creature.getCurrentHealth() + 50);

        player.levelUp(SpellType.HEAL);
        player.addXP(1000);
        player.levelUp(SpellType.FIRE);
        player.levelUp(SpellType.FROST);
        player.levelUp(SpellType.LIGHTNING);

        player.setCurrentHealth(100);
        player.setHealth(100);

        creature = new Creature(1000,1000);
        
        //Test when the player is dead.
        player.hurt(100);

        assertTrue(player.isDead());

        player.heal(100);

        assertTrue(player.isDead());

        player.setOnFire(creature);
        player.freeze(creature);
        player.electrocute(creature);

        assertFalse(creature.isFrozen());
        assertFalse(creature.isOnFire());
        assertFalse(creature.getCurrentHealth() < creature.getHealth());


    }
}
