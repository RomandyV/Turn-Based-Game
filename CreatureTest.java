package src;

import org.junit.Test;

import static org.junit.Assert.*;

public class CreatureTest{
    
    @Test

    public void testCreatureConstructor(){
        Creature creature = new Creature(45, 50);
        assertTrue(creature.getCurrentHealth() == 50);
        assertTrue(creature.getStrength() == 45);
        assertTrue(creature.getSpell() == null);
        assertTrue(creature.getHealth() == 50);

        Creature creature2 = new Creature(55, 501);
        assertTrue(creature2.getCurrentHealth() == 501);
        assertTrue(creature2.getStrength() == 55);
        assertTrue(creature2.getSpell() == null);
        assertTrue(creature2.getHealth() == 501);

        assertFalse(creature == creature2);
    }

    @Test
    public void testSetandGet(){
        Creature creature = new Creature(45, 50);
        assertTrue(creature.getCurrentHealth() == 50);
        assertTrue(creature.getStrength() == 45);
        assertTrue(creature.getSpell() == null);
        assertTrue(creature.getHealth() == 50);

        creature.setHealth(100);
        creature.setStrength(32);
        assertFalse(creature.getCurrentHealth() == 100);
        assertTrue(creature.getStrength() == 32);
        assertTrue(creature.getSpell() == null);
        assertTrue(creature.getHealth() == 100);

        creature.setCurrentHealth(100);
        assertTrue(creature.getCurrentHealth() == 100);
    }

    @Test

    public void testAttack(){
        Creature creature = new Creature(45, 50);
        //Test when not frozen or dead.
        assertTrue(creature.attack() >= 0 && creature.attack() <= creature.getStrength());
        assertTrue(creature.attack() >= 0 && creature.attack() <= 50);
        assertTrue(creature.attack() >= 0 && creature.attack() <= creature.getStrength());
        assertTrue(creature.attack() >= 0 && creature.attack() <= 50);
        assertTrue(creature.attack() >= 0 && creature.attack() <= creature.getStrength());
        assertTrue(creature.attack() >= 0 && creature.attack() <= 50);

        creature.setStrength(0);
        assertTrue(creature.attack() == 0);
        assertTrue(creature.attack() == 0);
        assertTrue(creature.attack() == 0);

        creature.setStrength(10);

        assertTrue(creature.attack() >= 0 && creature.attack() <= creature.getStrength());
        assertTrue(creature.attack() >= 0 && creature.attack() <= 10);

        //Test when frozen, but not dead.
        creature.giveSpell(SpellType.FROST);
        creature.freeze(creature);

        assertTrue(creature.attack() == 0);
        assertTrue(creature.attack() == 0);
        assertTrue(creature.attack() == 0);

        creature.decFreezeTimer();
        creature.decFreezeTimer();
        creature.decFreezeTimer();
        creature.decFreezeTimer();

        assertFalse(creature.isFrozen());

        //Test when dead, but not frozen
        creature.setCurrentHealth(0);

        assertTrue(creature.isDead());

        assertTrue(creature.attack() == 0);
        assertTrue(creature.attack() == 0);
        assertTrue(creature.attack() == 0);



    }

    @Test
    public void testHurt(){
        Creature creature = new Creature(45, 50);

        creature.hurt(50);
        assertTrue(creature.getCurrentHealth() == 0);
        assertTrue(creature.getHealth() == 50);
        creature.setCurrentHealth(50);
        assertTrue(creature.getCurrentHealth() == 50);
        assertTrue(creature.getHealth() == 50);

        for (int i = 1; i < 10; i++){
            
            creature.hurt(1);
            assertTrue(creature.getCurrentHealth() == creature.getHealth() - i);

            int tempHealth = creature.getCurrentHealth();

            creature.hurt(i);
            assertTrue(tempHealth == creature.getCurrentHealth() + i);

            creature.setCurrentHealth(tempHealth);
            
        }

    }

    @Test
    public void testIsDead(){
        Creature creature = new Creature(45, 50);

        assertFalse(creature.isDead());

        creature.hurt(50);
        
        assertTrue(creature.isDead());

        Creature creature2 = new Creature(100, 100);

        assertFalse(creature2.isDead());

        for(int i = 1; i < 100; i++){
            creature2.hurt(1);
            assertFalse(creature2.isDead());
        }

        creature2.hurt(1);
        assertTrue(creature2.isDead());
    }

    @Test
    public void testgiveSpell(){
        Creature creature = new Creature(45, 50);
        assertTrue(creature.getSpell() == null);
        creature.giveSpell(SpellType.FIRE);
        assertTrue(creature.getSpell() == SpellType.FIRE);
        creature.giveSpell(SpellType.FROST);
        assertTrue(creature.getSpell() == SpellType.FROST);
        assertFalse(creature.getSpell() == SpellType.FIRE);
        creature.giveSpell(SpellType.HEAL);
        assertTrue(creature.getSpell() == SpellType.HEAL);
        assertFalse(creature.getSpell() == SpellType.FIRE);
        assertFalse(creature.getSpell() == SpellType.FROST);
        creature.giveSpell(SpellType.LIGHTNING);
        assertTrue(creature.getSpell() == SpellType.LIGHTNING);
        assertFalse(creature.getSpell() == SpellType.FIRE);
        assertFalse(creature.getSpell() == SpellType.FROST);
        assertFalse(creature.getSpell() == SpellType.HEAL);
        creature.giveSpell(null);
        assertFalse(creature.getSpell() == SpellType.LIGHTNING);
        assertFalse(creature.getSpell() == SpellType.FIRE);
        assertFalse(creature.getSpell() == SpellType.FROST);
        assertFalse(creature.getSpell() == SpellType.HEAL);
    }

    @Test
    public void testCanCast(){
        Creature creature = new Creature(45, 50);
        assertFalse(creature.canCast(SpellType.FIRE));
        assertFalse(creature.canCast(SpellType.FROST));
        assertFalse(creature.canCast(SpellType.HEAL));
        assertFalse(creature.canCast(SpellType.LIGHTNING));

        Creature creature2 = new Creature(4325, 510);
        creature2.giveSpell(SpellType.FIRE);
        assertTrue(creature2.canCast(SpellType.FIRE));
        assertFalse(creature2.canCast(SpellType.FROST));
        assertFalse(creature2.canCast(SpellType.HEAL));
        assertFalse(creature2.canCast(SpellType.LIGHTNING));

        Creature creature3 = new Creature(4325, 510);
        creature3.giveSpell(SpellType.FROST);
        assertFalse(creature3.canCast(SpellType.FIRE));
        assertTrue(creature3.canCast(SpellType.FROST));
        assertFalse(creature3.canCast(SpellType.HEAL));
        assertFalse(creature3.canCast(SpellType.LIGHTNING));

        Creature creature4 = new Creature(25, 10);
        creature4.giveSpell(SpellType.HEAL);
        assertFalse(creature4.canCast(SpellType.FIRE));
        assertFalse(creature4.canCast(SpellType.FROST));
        assertTrue(creature4.canCast(SpellType.HEAL));
        assertFalse(creature4.canCast(SpellType.LIGHTNING));

        Creature creature5 = new Creature(4325, 510);
        creature5.giveSpell(SpellType.LIGHTNING);
        assertFalse(creature5.canCast(SpellType.FIRE));
        assertFalse(creature5.canCast(SpellType.FROST));
        assertFalse(creature5.canCast(SpellType.HEAL));
        assertTrue(creature5.canCast(SpellType.LIGHTNING));
    }
    
    @Test
    public void testFreezeLogic(){
        Creature creature = new Creature(45, 50);
        Creature creature2 = new Creature(43,434);

        //Test if the target is frozen depending if the creature has the spell.
        creature.freeze(creature2);
        assertFalse(creature2.isFrozen());

        creature2.freeze(creature);
        assertFalse(creature.isFrozen());

        creature.giveSpell(SpellType.FROST);
        creature2.giveSpell(SpellType.FROST);

        assertTrue(creature.getSpell() == SpellType.FROST);
        assertTrue(creature2.getSpell() == SpellType.FROST);

        //Test when the target is frozen and can cast freeze spell.
        creature.freeze(creature2);
        assertTrue(creature2.isFrozen());

        creature2.freeze(creature);
        assertFalse(creature.isFrozen());

        assertTrue(creature.getSpell() == null);
        assertTrue(creature2.getSpell() == SpellType.FROST);

        creature2.decFreezeTimer();
        creature2.decFreezeTimer();
        creature2.decFreezeTimer();
        creature2.decFreezeTimer();

        assertFalse(creature2.isFrozen());

        creature.decFreezeTimer();
        creature.decFreezeTimer();
        creature.decFreezeTimer();
        creature.decFreezeTimer();
        creature.decFreezeTimer();
        creature.giveSpell(SpellType.FROST);

        assertFalse(creature.isFrozen());

        creature2.freeze(creature);

        assertTrue(creature.isFrozen());
        assertTrue(creature2.getSpell() == null);

        creature.freeze(creature2);
        assertTrue(creature.getSpell() == SpellType.FROST);
        assertFalse(creature2.isFrozen());

        creature.decFreezeTimer();
        creature.decFreezeTimer();
        creature.decFreezeTimer();
        creature.decFreezeTimer();

        //Test if can cast spell, if dead.
        assertFalse(creature.isFrozen());
        creature.setCurrentHealth(0);
        assertTrue(creature.isDead());

        creature.giveSpell(SpellType.FROST);
        creature.freeze(creature2);
        assertFalse(creature2.isFrozen());
    }

    @Test
    public void testFireLogic(){
        //Test if casts are successful if there is a cast.
        Creature creature = new Creature(45, 50);
        Creature creature2 = new Creature(43,434);

        creature.setOnFire(creature2);
        assertFalse(creature2.isOnFire());

        creature2.setOnFire(creature);
        assertFalse(creature.isOnFire());

        creature.giveSpell(SpellType.FIRE);
        creature2.giveSpell(SpellType.FIRE);

        assertTrue(creature.getSpell() == SpellType.FIRE);
        assertTrue(creature2.getSpell() == SpellType.FIRE);

        creature.setOnFire(creature2);
        assertTrue(creature2.isOnFire());

        creature2.setOnFire(creature);
        assertTrue(creature.isOnFire());

        assertTrue(creature.getSpell() == null);
        assertTrue(creature2.getSpell() == null);

        creature2.decFireTimer();
        creature2.decFireTimer();
        creature2.decFireTimer();
        creature2.decFireTimer();

        assertFalse(creature2.isOnFire());

        creature.decFireTimer();
        creature.decFireTimer();
        creature.decFireTimer();
        creature.decFireTimer();
        creature.decFireTimer();
        creature2.giveSpell(SpellType.FIRE);

        assertFalse(creature.isOnFire());

        creature2.setOnFire(creature);

        assertTrue(creature.isOnFire());
        assertTrue(creature2.getSpell() == null);

        creature.setOnFire(creature2);

        //Test if the target takes damage.
        creature2 = new Creature(100,100);
        creature = new Creature(100,100);

        creature.giveSpell(SpellType.FIRE);

        creature.setOnFire(creature2);

        assertTrue(creature2.isOnFire());

        creature2.decFireTimer();
        assertTrue(creature2.getCurrentHealth() < creature2.getHealth());

        //Test if it can cast when frozen.

        creature = new Creature(50,50);
        creature2 = new Creature(50,50);

        creature.giveSpell(SpellType.FIRE);
        creature2.giveSpell(SpellType.FROST);

        creature2.freeze(creature);

        assertTrue(creature.isFrozen());

        creature.setOnFire(creature2);

        assertFalse(creature2.isOnFire());

        //Test if it can cast when dead.

        creature = new Creature(343,43);
        creature2 = new Creature(34,343);

        creature.giveSpell(SpellType.FIRE);
        creature.setCurrentHealth(0);

        assertTrue(creature.isDead());

        creature.setOnFire(creature2);
        assertFalse(creature2.isOnFire());


    }

    @Test
    public void testHeal(){
        //Test if not dead nor frozen.
        Creature creature = new Creature(50, 55);
        creature.setCurrentHealth(3);
        creature.heal(50);
        assertTrue(creature.getCurrentHealth() == 3);
        creature.giveSpell(SpellType.HEAL);
        creature.heal(50);
        assertTrue(creature.getCurrentHealth() == 53);
        assertTrue(creature.getSpell() == null);
        creature.giveSpell(SpellType.HEAL);
        creature.heal(8768767);
        assertTrue(creature.getCurrentHealth() == 55);

        //Test when dead.
        creature.setCurrentHealth(0);
        assertTrue(creature.isDead());

        creature.giveSpell(SpellType.HEAL);
        creature.heal(50);

        assertTrue(creature.isDead());

        //Test when frozen.
        creature = new Creature(43,43);
        creature.giveSpell(SpellType.FROST);
        creature.freeze(creature);
        creature.setCurrentHealth(3);
        creature.giveSpell(SpellType.HEAL);
        creature.heal(50);
        assertTrue(creature.getCurrentHealth() ==3);


    }

    @Test
    public void testElectrocute(){
        //Test when not dead nor frozen and if there are casts.
        Creature creature = new Creature(100,343);
        Creature creature2 = new Creature(1,1000);

        creature.electrocute(creature2);
        assertTrue(creature2.getCurrentHealth() == creature2.getHealth());

        creature.giveSpell(SpellType.LIGHTNING);

        creature.electrocute(creature2);

        assertTrue(creature2.getCurrentHealth() + 100 == creature2.getHealth());

        //Test when frozen
        creature.giveSpell(SpellType.LIGHTNING);
        creature2.giveSpell(SpellType.FROST);

        creature2.freeze(creature);
        creature.electrocute(creature2);
        assertTrue(creature2.getCurrentHealth() + 100 == creature2.getHealth());

        //Test when dead.
        creature.decFreezeTimer();
        creature.decFreezeTimer();
        creature.decFreezeTimer();
        creature.decFreezeTimer();

        creature.setCurrentHealth(0);

        creature.electrocute(creature2);

        assertTrue(creature2.getCurrentHealth() + 100 == creature2.getHealth());

    }


}
