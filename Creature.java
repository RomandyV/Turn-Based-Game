package src;
import java.util.Random;

/********************************************************************************************
 * 
 * A class for the creature class. The creature class is used for the player's enemies/hostiles
 * and is also used for the player (as the player class inherits from the Creature Class). 
 * The spell methods also check to see if the creature can cast the spell (has
 * a cast, is not frozen, and is not dead.)
 * 
 * @author Romandy Vu
 * @version 3/29/2021
 * 
 ********************************************************************************************/
public class Creature {
    /**The type of spell the creature has.*/
    private SpellType spell;
    
    /**The strength (potential max damage) of the creature. */
    private int strength;

    /**The max health of the creature. */
    private int maxHealth;

    /**The status effect if the creature is frozen (boolean).*/
    private boolean isFrozen;

    /**The current health of the monster. */
    private int currentHealth;

    /**A boolean field if the monster is dead*/
    private boolean isDead;

    /**The time left before it unfreezes. If greater than 0, it will lose said turns,
     * value decreases every turn.*/
    private int freezeTime;

    /**The time left before it is no longer on fire. If greater than 0, it will take damage
     * until time is 0.*/
    private int onFireTime;

    /**The status effect if the creature is on fire (boolean). */
    private boolean isOnFire;

    /****************************************************************************************
     * 
     * The default constructor of the creature class, used for the default constructor for
     * the Player Class (child class).
     * 
     **************************************************************************************/
    public Creature(){
        isFrozen = false;
        isDead = false;
        isOnFire = false;
    }

    /***************************************************************************************
     *
     * A constructor that sets the strength and the max Health of the creature.
     *  
     * @param strength an int of the strength (possible max damage) of the creature.
     * @param maxHealth an int of the max health of the creature.
     * @return None (default constructor).
     **************************************************************************************/
    public Creature(int strength, int maxHealth){
        this.strength = strength;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        isFrozen = false;
        isDead = false;
        isOnFire = false;
        spell = null;
    }

    /************************************************************************************
     * 
     * A method that returns an int that represents the damage of the creature.
     * 
     * @param None
     * @return an int of the damage dealt. The damage will be 0 if the creature is frozen or dead.
     * If the creature is not frozen or not dead, returns an int ranging from 0 to the "strength"
     * (max damage possible).
     ***********************************************************************************/
    public int attack(){
        //Checks if creature is frozen, if so, returns 0.
        if(isFrozen || isDead){
            return 0;
        }
        /*If not, creates a random class to determine the damage dealt ranging from 0
        to strength and returns that*/
        Random damage = new Random();
        return damage.nextInt(strength + 1);
    }

    /**********************************************************************************
     * 
     * A method that subtracts the health of the monster from the given damage received.
     * 
     * @param damage an int of the damage done to the monster (used to subtract from
     * current health).
     * @return None (Void return type) 
     **********************************************************************************/
    public void hurt(int damage){
        currentHealth -= damage;
        if(currentHealth <= 0){
            this.isDead = true;
        }
    }

    /*************************************************************************************
     * 
     * A method that enables the creature to cast the given spell.
     * 
     * @param type the spell that the monster can cast (Refer to SpellType.java for options).
     * @return None (Void return type).
     ***************************************************************************************/
    public void giveSpell(SpellType type){
        spell = type;
    }

    /**************************************************************************************
     * 
     * A method that returns the spell that the creature has.
     * 
     * @param None.
     * @return an SpellType of the spell the creature has.
     **************************************************************************************/
    public SpellType getSpell(){
        return spell;
    }
    
    /*************************************************************************************
     * 
     * A method that returns the strength of the creature.
     * 
     * @param None
     * @return an int of the creature's strength.
     *************************************************************************************/
    public int getStrength(){
        return strength;
    }

    /*************************************************************************************
     * 
     * A method that sets the strength of the creature.
     * 
     * @param strength an int value that the creature's strength is changing to.
     * @return None (Void return type).
     ************************************************************************************/
    public void setStrength(int strength){
        this.strength = strength;
    }

    /***********************************************************************************
     * 
     * A method that returns the current health of the creature.
     * 
     * @param None
     * @return an int of the current health of the creature.
     ************************************************************************************/
    public int getCurrentHealth(){
        return currentHealth;
    }

    /************************************************************************************
     * 
     * A method that sets the current health of the creature.
     * 
     * @param health an int that the currentHealth is being set to.
     * @return None.
     ***********************************************************************************/
    public void setCurrentHealth(int health){
        currentHealth = health;
        if(this.currentHealth <= 0){
            this.isDead = true;
        }
    }

    /***********************************************************************************
     * 
     * A method that sets the max health of the creature.
     * @param health an int value that the creature's max health is being set to.
     * @return None (Void return type).
     **********************************************************************************/
    public void setHealth(int health){
        this.maxHealth = health;
    }

    /**********************************************************************************
     * 
     * A method that returns the max health of the creature.
     * 
     * @param None
     * @return an int of the creature's max health.
     ********************************************************************************/
    public int getHealth(){
        return maxHealth;
    }

    /*******************************************************************************
     * 
     * A method that returns a boolean value if the monster is dead. It is dead
     * if the current health is less or equal to 0.
     * 
     * @param None
     * @return a boolean value if the monster is dead.
     ******************************************************************************/
    public boolean isDead(){
        if(currentHealth <= 0){
            this.isDead = true;
        }
        return this.isDead;
    }

    /******************************************************************************
     * 
     * A method that returns a boolean value if the monster can cast the specified
     * spell.
     * 
     * @param type the spellType that is a variable in SpellType.java.
     * @return a boolean value if the monster has the spell.
     ******************************************************************************/
    public boolean canCast(SpellType type){
        return spell == type;
    }

    /**********************************************************************************
     * 
     * A method that freezes the target, disabling the targets chance to make a move.
     * Can only be cast if there is a cast avaliable and the creature is not dead and
     * not frozen.
     * 
     * @param target a creature that is being frozen.
     * @return None (Void return type).
     ***********************************************************************************/
    public void freeze(Creature target){
        /*Checks to see if the creature can cast the spell(is not frozen, dead,
        and has the ability to cast)*/
        if((!isFrozen) && canCast(SpellType.FROST) && (!isDead())){
        //A variable of type random used to determine the amount of time frozen.
        Random chance = new Random();

        //Removes the spell from the creature (single use spell).
        spell = null;
        
        //Freezes the target for a certain amount of time/turns (random 2-4)
        target.freezeTime += chance.nextInt(3) + 2;
        //Sets the status effect of the target being frozen.
        target.isFrozen = true;
        }
        
    }

    /******************************************************************************
     * 
     * A method that returns a boolean value if the creature is frozen.
     * 
     * @param None
     * @return a boolean value if the creature is frozen.
     ****************************************************************************/
    public boolean isFrozen(){
        return isFrozen;
    }

    /***************************************************************************
     * 
     * A method that decrements the freeze timer, and sets the frozen status to
     * false if freeze time is 0.
     * 
     * @param None
     * @return None (Void return type).
     *************************************************************************/
    public void decFreezeTimer(){
        //Decrements if the timer is greater than 0.
        if(freezeTime > 0){
            freezeTime--;
        }
        //Checks to see if freezeTime is 0, to change the freeze status.
        if(freezeTime == 0){
            isFrozen = false;
        }
    }

    /***********************************************************************
     * 
     * A method that sets the specified target on fire for a certain amount
     * of turns. Can only be cast if there is a cast avaliable 
     * and the creature is not dead and not frozen.
     * 
     * @param target a creture that is being set on fire.
     * @return None (Void return type).
     *********************************************************************/
    public void setOnFire(Creature target){
        /*Checks to see if the creature can cast the spell(is not frozen, dead,
        and has the ability to cast)*/
        if((!isFrozen) && canCast(SpellType.FIRE) && (!isDead())){
        //A variable of type random to determine the time on fire.
        Random chance = new Random();
        
        //Removes the spell from the monster (single use spell).
        spell = null;

        //Sets the target on fire for 2-4 turns.
        target.onFireTime += chance.nextInt(3) + 2;
        
        //Sets the status to true.
        target.isOnFire = true;
        }
        
    }

    /*********************************************************************
     * 
     * A method that returns if the creature is on fire.
     * 
     * @param None
     * @return a boolean value if the creature is on fire.
     ********************************************************************/
    public boolean isOnFire(){
        return isOnFire;
    }

    /*********************************************************************
     * 
     * A method that decrements the fire timer and damages the creature
     * (or player) by ~10-25% of their max health while on fire.
     * 
     * @param None
     * @return None (void return type).
     ********************************************************************/
    public void decFireTimer(){
        /*Checks if the timer is greater than 0, so it won't decrement
        to a negative value*/
        if(onFireTime > 0){
            //A random class variable used to determine the damage of the fire.
            Random chance = new Random();
            
            //A variable to store and determine the fire damage.
            int fireDamage = ((chance.nextInt(16) + 10) * getHealth()) / 100;

            //Hurts the target from the fire damage.
            hurt(fireDamage);

            //Decrement fire time.
            onFireTime--;
        }
        //Checks to see if timer is 0, so it can set to false.
        if(onFireTime == 0){
            isOnFire = false;
        }
    }

    /**********************************************************************
     * 
     * A method that heals the creature for a set amount. If the current
     * health of the creature exceeds the creature's max health, then
     * the current health would be the creature's max health. Can only be cast 
     * if there is a cast avaliable and the creature is not dead and
     * not frozen.
     * 
     * @param amt an int that is added to the creature's current health.
     * @return None (void return type).
     *********************************************************************/
    public void heal(int amt){
        /*Checks to see if the creature can cast the spell(is not frozen, dead,
        and has the ability to cast)*/
        if((!isFrozen) && canCast(SpellType.HEAL) && (!isDead())){
            /*Checks to see the creature overhealed. If so set the current health
            to the max health*/
            if(currentHealth + amt > maxHealth){
                currentHealth = maxHealth;
            }
            //Else heals the creature by set amount
            else{
                currentHealth += amt;
            }
            //Removes the spell from creature (single use.)
            spell = null;
        }
        
    }

    /***************************************************************************
     * 
     * A method that deals damage to the given target. the damage dealt depends
     * on the strength of the creature. Can only be cast if there is a cast avaliable 
     * and the creature is not dead and not frozen.
     * 
     * @param target the Creature that is being shocked.
     * @return None (void return type).
     **************************************************************************/
    public void electrocute(Creature target){
        /*Checks to see if the creature can cast the spell(is not frozen, dead,
        and has the ability to cast)*/
        if((!isFrozen) && canCast(SpellType.LIGHTNING) && (!isDead())){
            target.hurt(this.strength);
            spell = null;
        }
        
    }
    
}
