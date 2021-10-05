package src;
import java.util.Random;

/*****************************************************************************
 * 
 * A class that is a child of the Creature class. The class is for the player.
 * The spell methods also check to see if the player can cast the spell (has
 * a cast, is not frozen, and is not dead.)
 * 
 * @author Romandy Vu
 * @version 3/29/2021
 *****************************************************************************/
public class Player extends Creature {
    
    /**An variable that holds an int for the experience of the player.
     * Once it reaches 100, the player can level up.*/
    private int XP;

    /**A int variable that holds the number of cast of the healing spell as the player
     * might not use the spell when they level up.*/
    private int healCasts;

    /**A int variable that holds the number of cast of the fire spell as the player
     * might not use the spell when they level up.*/
    private int fireCasts;

    /**A int variable that holds the number of cast of the lightning spell as the player
     * might not use the spell when they level up.*/
    private int lightningCasts;

    /**A int variable that holds the number of cast of the freeze spell as the player
     * might not use the spell when they level up.*/
    private int frostCasts;

    /**********************************************************************************
     * 
     * A default constructor of the Player Class. The stats are randomized where the
     * strength ranges from 25 to 50, and max health ranging from 100-150.
     *********************************************************************************/
    public Player(){
        //Calls the default constructor of the Creature class.
        super();
        
        //variable of the random class used to determine the randomization of the stats.
        Random chance = new Random();
        
        //int variable to hold the random max health stats. (Can be 100-150)
        int maxHealth = chance.nextInt(51) + 100;

        //int variable to hold the random strength stats. (Can be 25-50)
        int strength = chance.nextInt(26) + 25;

        //sets the strength of the player.
        setStrength(strength);
        //sets the max health of the player.
        setHealth(maxHealth);

        //sets the current health of the player.
        setCurrentHealth(maxHealth);

        //starting condition of the player.
        XP = 0;
        healCasts = 0;
        fireCasts = 0;
        lightningCasts = 0;
        frostCasts = 0;
    }

    /***********************************************************************************
     * 
     * A method that adds to the player's XP by the given amount.
     * 
     * @param amt an int of that is added to the players xp.
     * @return None (void return type.)
     **********************************************************************************/
    public void addXP(int amt){
        XP += amt;
    }

    /**********************************************************************************
     * 
     * A method that returns the player's XP.
     * 
     * @param None
     * @return an int of the player's XP.
     *********************************************************************************/
    public int getXP(){
        return XP;

    }

    /**********************************************************************************
     * 
     * A method that returns a boolean value if the player can level up.
     * 
     * @param None
     * @return a boolean value if the player can level up (XP >= 100).
     **********************************************************************************/
    public boolean canLevelUp(){
        return XP >= 100;
    }

    /**********************************************************************************
     * 
     * A method that level up the player enabling the player to cast a spell and an
     * increase in stats. + 5 strength, + 10 for max health, and completely
     * heals the player. Can only work if player XP is >= 100.
     * 
     * @param type the type of spell the player can now cast (single use).
     * @return None.
     *********************************************************************************/
    public void levelUp(SpellType type){
        //Checks to see it the player can level up.
        if(canLevelUp()){
            //Removes 100 XP from the player's XP.
            XP -= 100;
            //Adds an additional 5 points to the strength of the player.
            setStrength(getStrength() + 5);

            //Adds an additonal 10 points to the max health of the player.
            setHealth(getHealth() + 10);

            //Completely heals the player.
            setCurrentHealth(getHealth());

            //Depending on what spell the player chose, the ability to cast is incremented.
            if(type == SpellType.FIRE){
                fireCasts++;
            }
            else if(type == SpellType.FROST){
                frostCasts++;
            }
            else if(type == SpellType.HEAL){
                healCasts++;
            }
            else if(type == SpellType.LIGHTNING){
                lightningCasts++;
            }
        }
    }

    /*******************************************************************************
     * 
     * A method that returns the number of casts of each spell the player has. (Used
     * for one of the console prompts Look at Arena.java).
     * 
     * @return a string of that shows how much casts the player has for each spell.
     ******************************************************************************/
    public String getSpellCasts(){
        return "You have:" +
        "\n" + fireCasts + " fire spells" +
        "\n" + frostCasts + " frost spells" +
        "\n" + lightningCasts + " lightning spells" +
        "\n" + healCasts + " healing spells\n";
    }

    /*********************************************************************************
     * 
     * A method that returns if the player can cast the spell. Overrides the
     * Creature canCast(SpellType type) method.
     * 
     * @param type the spell of type SpellType that is being checked.
     * @return a boolean value if the casts of the spell that the 
     * player has is greater than 0.
     * @throws illegalArgumentException when the parameter is not one of the 4
     * avaliable spells (currently).
     *********************************************************************************/
    @Override
     public boolean canCast(SpellType type){
        /*Checks if the parameter matches the avaliable spells, 
        then return the boolean value if the spell cast is greater than 0
        if not throw an exception*/
        if(type == SpellType.FIRE){
            return fireCasts > 0;
        }
        else if (type == SpellType.FROST){
            return frostCasts > 0;
        }
        else if (type == SpellType.LIGHTNING){
            return lightningCasts > 0;
        }
        else if(type == SpellType.HEAL){
            return healCasts > 0;
        }
        throw new IllegalArgumentException();
        
    }

    /***********************************************************************
     * 
     * A method that sets the specified target on fire for a certain amount
     * of turns. Can only be used if there is a cast avaliable and the
     * player is not frozen and not dead.
     * 
     * @param target a creture that is being set on fire.
     * @return None (Void return type).
     *********************************************************************/
    @Override
    public void setOnFire(Creature target){
        if(canCast(SpellType.FIRE) && (!isFrozen()) && (!isDead())){
        //Here for the part of the if statement of the super class method.
        giveSpell(SpellType.FIRE);
        super.setOnFire(target);
        //Decrement the fire casts.
        fireCasts--;
        }
    }

    /**********************************************************************************
     * 
     * A method that freezes the target, disabling the targets chance to make a move.
     * Can only be used if there is a cast avaliable and the player is not frozen
     * and not dead.
     * 
     * @param target a creature that is being frozen.
     * @return None (Void return type).
     ***********************************************************************************/
    @Override
    public void freeze(Creature target){
        if(canCast(SpellType.FROST) && (!isFrozen()) && (!isDead()) ){
        //Here for the part of the if statement of the super class method.
        giveSpell(SpellType.FROST);
        //Calls the super class method to do the same thing.
        super.freeze(target);
        //Decrements frost casts.
        frostCasts--;
        }
    }

    /***********************************************************************
     * 
     * A method that heals the creature that is casting it. Can only be used 
     * if there is a cast avaliable and the player is not frozen and not dead.
     * 
     * @param amt an int that that represents the max healing that is given
     * to the object.
     * @return None (Void return type).
     *********************************************************************/
    @Override
    public void heal(int amt){
        if(canCast(SpellType.HEAL) && (!isFrozen()) && (!isDead())){
        //Here for the part of the if statement of the super class method.
        giveSpell(SpellType.HEAL);
        //Calls the super class method to do the same thing.
        super.heal(amt);
        //Decrement the healing casts.
        healCasts--;
        }
    }

    /***************************************************************************
     * 
     * A method that deals damage to the given target. the damage dealt depends
     * on the strength of the creature. Can only be used 
     * if there is a cast avaliable and the player is not frozen and not dead.
     * 
     * @param target the Creature that is being shocked.
     * @return None (void return type).
     **************************************************************************/
    @Override
    public void electrocute(Creature target){
        if(canCast(SpellType.LIGHTNING) && (!isFrozen()) && (!isDead()) ){
        //Here for the part of the if statement of the super class method.
        giveSpell(SpellType.LIGHTNING);
        //Calls the super class (Creature) method to do the same thing.
        super.electrocute(target);
        //Decrement the electrocute casts.
        lightningCasts--;
        }
    }

}
