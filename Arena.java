package src;

import java.util.Random;
import java.util.Scanner;

/**********************************************************************************************
 * 
 * The Arena class in which the game is to be played?
 * 
 * @author Romandy Vu
 * @version March 26, 2021
 *********************************************************************************************/
public class Arena {

    /**The queue of monsters from the North*/
    private Queue<Creature> northQueue;

    /**The queue of monsters from the South*/
    private Queue<Creature> southQueue;

    /**The queue of monsters from the East*/
    private Queue<Creature> eastQueue;

    /**The queue of monsters from the West*/
    private Queue<Creature> westQueue;

    /**The player (user) character*/
    private Player player;

    /**************************************************************************************
     * 
     * The default constructor that creates the queues,player, and starting monsters.
     * 
     *************************************************************************************/
    public Arena() {
        //Prompts the player that the game has started.
        System.out.println("Welcome to the Arena...");
        System.out.println("To see the combat log of the previous round scroll up.");
        northQueue = new Queue<Creature>();
        southQueue = new Queue<Creature>();
        eastQueue = new Queue<Creature>();
        westQueue = new Queue<Creature>();
        player = new Player();
        //Adds some starting monsters.
        Creature starterMonster = new Creature(10,25);
        northQueue.enqueue(starterMonster);

        for (int i = 0; i < 10; i++){
            createMonster();
        }
    }

    /***********************************************************************************
     * 
     * A method that runs and keep track of the number of rounds in the game.
     * 
     **********************************************************************************/
    private void gameLoop() {
        int turnCounter = 0;
        while (!isGameOver()) {
            System.out.println();
            createMonster();
            playersTurn();
            monstersTurn();
            turnCounter++;
        }
        System.out.println("You survived " + turnCounter + " rounds.");
    }

    /****************************************************************************************
     * 
     * A method that returns a boolean value if the game is over (if the player is dead or
     * if all the queues are empty).
     * 
     * @return a boolean value if the game is over.
     ****************************************************************************************/
    private boolean isGameOver() {
        //Checks to see if all the queues are empty or if the player is dead.
        if ((northQueue.size() == 0 && southQueue.size() == 0 &&
         eastQueue.size() == 0 && westQueue.size() == 0)) {
            System.out.println("\nYou cleared all the queues, you win.");
            return true;
        }
        else if (player.isDead()){
            System.out.println("\nYou died, you lose.");
            return true;
        }

        return false;
    }

    /****************************************************************************************
     * 
     * A method that determines the probablity if a monster is created, its stats, and the
     * queue it is placed in.
     *
     ****************************************************************************************/
    private void createMonster() {
        
        //Creates a random object used to calculate if something happens or not.
        Random probability = new Random();
        
        //Determines the probability if a monster is created (currently 50% to spawn)
        if (probability.nextInt(2) % 2 == 0) {

            //Determines the health of the monster. (Range is currently 1 - 50).
            int health = probability.nextInt(50) + 1;

            //Determines the strength(max damage of the monster). (Range is currently 1-25).
            int strength = probability.nextInt(25) + 1;

            //Creates the monster with the random given stats.
            Creature spawnedEnemy = new Creature(strength, health);

            //Determines the probability of getting a spell (Currently 10% chance).
            if (probability.nextInt(10) == 0) {

                //Determines what spell the monster will have from the given integer.
                int spellType = probability.nextInt(4);

                if (spellType == 0) {
                    spawnedEnemy.giveSpell(SpellType.FROST);
                } else if (spellType == 1) {
                    spawnedEnemy.giveSpell(SpellType.FIRE);
                } else if (spellType == 2) {
                    spawnedEnemy.giveSpell(SpellType.LIGHTNING);
                } else {
                    spawnedEnemy.giveSpell(SpellType.HEAL);
                }
            }

            //Determines the queue of where the monster will be put into from the given integers.
            int queueIdentifier = probability.nextInt(4);

            if (queueIdentifier == 0) {
                northQueue.enqueue(spawnedEnemy);
            } else if (queueIdentifier == 1) {
                southQueue.enqueue(spawnedEnemy);
            } else if (queueIdentifier == 2) {
                eastQueue.enqueue(spawnedEnemy);
            } else {
                westQueue.enqueue(spawnedEnemy);
            }
        }
    }

    /************************************************************************************************
     * 
     * A method for the player's turn. If the player is frozen, their turn is skipped. They given
     * the stats on the monsters in the queue. If the player is not frozen they will be prompted to
     * give the direction of the attack, and the attack they wanted to use. If the player can level up
     * they will be prompted to do so from this method, but they can only level up once per turn.
     * 
     ************************************************************************************************/
    public void playersTurn() {

        //The variable of the monster that the player will attack.
        Creature monster;

        //Variable to set the monster to if the player decides to attack an empty queue.
        Creature thinAir = new Creature();

        //Refer to method description. Method was made so that the code doesn't look too long.
        getSituation();
        
        //This boolean variable is used to determine it the input from the user is valid.
        boolean isInvalid = true;
        
        /*(Might be issue here) Creates a scanner to get the user input from console. */
        Scanner scanner = new Scanner(System.in);
        
        //Variable is used for a comparison (converts the scanner to string).
        String userInput;

        //Option if the player can level up.
        if (player.canLevelUp()) {
            //sets variable to true as a valid input for the next loop.
            isInvalid = true;
            do{

            //Informs the user that they have level up, and the options to choose a single use spell
            System.out.println("LEVEL UP!\n Choose a single-use spell.");
            System.out.println("\"FIRE\" for a fire spell");
            System.out.println("\"FROST\" for a frost spell");
            System.out.println("\"LIGHTNING\" for a lightning spell");
            System.out.println("\"HEAL\" for a healing spell");
            
            //Gets the user input.
            userInput = scanner.nextLine();

            /*Runs the loop once, and will repeat if input is invalid.
            The sets the player casts on a certain spell depending on what the user chose. */
            
                if (userInput.toUpperCase().equals("FIRE")) {
                    player.levelUp(SpellType.FIRE);
                    isInvalid = false;
                }

                else if (userInput.toUpperCase().equals("FROST")) {
                    player.levelUp(SpellType.FROST);
                    isInvalid = false;
                }

                else if (userInput.toUpperCase().equals("LIGHTNING")) {
                    player.levelUp(SpellType.LIGHTNING);
                    isInvalid = false;
                } else if (userInput.toUpperCase().equals("HEAL")) {
                    player.levelUp(SpellType.HEAL);
                    isInvalid = false;
                }
                //Prompts when the input is invalid.
                if (isInvalid) {
                    System.out.println("Error with input, try again.");
                }
            } while (isInvalid);
        }
        
        //If the player is not frozen this will run (used to determine move).
        if (!player.isFrozen()) {
            //Need for the next loop as the player will need to enter valid input or the loop will repeat.
            isInvalid = true;
            
            //Loop runs once and will loop again if any of the input from the user is invalid.
            do {
                //Prompts options for where the player wants to attack.
                System.out.println("\nWhich direction will you plan your decision?");
                System.out.println("\"N\" for North");
                System.out.println("\"S\" for South");
                System.out.println("\"E\" for East");
                System.out.println("\"W\" for West\n");
                System.out.println("Any invalid input will force you to re-enter said inputs again.");
                System.out.println("Enter any invalid inputs if you wish change your decision before attacking.\n");
                System.out.println("To heal: choose a valid direction, select spells \"S\" and select the healing spell \"HEAL.\" \n");
                System.out.println("Inputs are not case-sensitive.\n");
                userInput = scanner.nextLine();
                System.out.println();

                //Checks to see if the input is valid.
                if (userInput.toUpperCase().equals("N") || userInput.toUpperCase().equals("S")
                        || userInput.toUpperCase().equals("E") || userInput.toUpperCase().equals("W")) {

                    /*Depending on the valid input prompted, the monster variable will be set the monster from
                    the direction the player is attacking. Checks to see if there is the monster on the selected
                    queue, if there are no monsters on the selected queue the player decision will be an
                    inefficient attack*/
                    if (userInput.toUpperCase().equals("N") && northQueue.size() > 0) {
                        monster = northQueue.peek();
                    } else if (userInput.toUpperCase().equals("S") && southQueue.size() > 0) {
                        monster = southQueue.peek();
                    } else if (userInput.toUpperCase().equals("E") && eastQueue.size() > 0) {
                        monster = eastQueue.peek();
                    } else if (userInput.toUpperCase().equals("W") && westQueue.size() > 0) {
                        monster = westQueue.peek();
                    }
                    else{
                        monster = thinAir;
                    }
                    
                    //Prompts the option for the type of attack the player wants to do.
                    System.out.println("What attack will you do?");
                    System.out.println("\"S\" to cast a spell");
                    System.out.println("\"A\" for a regular attack\n");
                        
                        
                    //Gets the next input from user.
                    userInput = scanner.nextLine();
                        
                    //Runs the action of the player (determined by the input.)
                    //The player chose a regular attack.
                    if (userInput.toUpperCase().equals("A")) {
                    //Determines the potential damage the player can do.
                    int damage = player.attack();
                    //Damages the monster.
                    monster.hurt(damage);
                    isInvalid = false;
                            
                    //Informs the damage the player did.
                    System.out.println("You dealt " + damage + " damage");
                    }
                    //The player chose a spell attack. 
                    else if (userInput.toUpperCase().equals("S")) {
                            
                    //Prompts the user for the spell that the player want to cast.
                    System.out.println("What Spell will you cast?");
                    System.out.println("\"FIRE\" for a fire spell");
                    System.out.println("\"FROST\" for a frost spell");
                    System.out.println("\"LIGHTNING\" for a lightning spell");
                    System.out.println("\"HEAL\" for a healing spell\n");
                            
                    /*Refer to the Player Class for information about the method. Pretty much contains
                    the number of casts for each spell. If the player chooses to cast a spell with no
                    casts left, the spell with be ineffective and the player will lose their turn.*/
                    System.out.println(player.getSpellCasts() + "\n");
                            
                    //Gets the next input from user.
                    userInput = scanner.nextLine();
                    System.out.println();

                    //The conditional statements determine action of the spell the player chose to cast.
                    if (userInput.toUpperCase().equals("FIRE")) {
                        if (player.canCast(SpellType.FIRE)) {
                            player.setOnFire(monster);
                            System.out.println("You successfully cast the fire spell");
                                }
                        else {
                            System.out.println("You can't cast the spell!");
                            System.out.println("You lost your turn!");
                            }
                            isInvalid = false;
                    }
                    else if (userInput.toUpperCase().equals("FROST")) {
                        if (player.canCast(SpellType.FROST)){
                            player.freeze(monster);
                                System.out.println("You successfully cast the freeze spell");
                        }
                        else {
                                System.out.println("You can't cast the spell!");
                                System.out.println("You lost your turn!");
                        }
                                isInvalid = false;
                            }

                    else if (userInput.toUpperCase().equals("LIGHTNING")) {
                        if (player.canCast(SpellType.LIGHTNING)) {
                            monster.hurt(player.getStrength());
                                System.out.println("You successfully cast the lightning spell");
                        }
                        else {
                                System.out.println("You can't cast the spell!");
                                System.out.println("You lost your turn!");
                        }
                                isInvalid = false;
                    } 
                    else if (userInput.toUpperCase().equals("HEAL")) {
                        if (player.canCast(SpellType.HEAL)) {
                            player.heal(player.getStrength());
                            System.out.println("You successfully cast the healing spell");
                        } 
                        else {
                            System.out.println("You can't cast the spell!");
                            System.out.println("You lost your turn!");
                        }
                            isInvalid = false;
                    }
                }
                if((!isInvalid) && monster == thinAir && !userInput.equalsIgnoreCase("HEAL")){
                    System.out.println("If you damaged something.. it was thin air..");
                }
            }
            
                //Prompts a message when the input is invalid from the user.
                if (isInvalid) {
                    System.out.println("Error with the input. Try again.");
                }
                //Loop will run again if input is invalid.
            } while (isInvalid);
        }

        //Prompts a message informing that the player is frozen.
        else {
                System.out.println("You are currently frozen, so you lose a turn.");
            }
            
            //Decrement the status effects (if applicable).
            player.decFreezeTimer();
            player.decFireTimer();

            
                
            //This here to close scanner, currently there is an issue if uncommenting the close.
            //scanner.close();
    }
    

    /*********************************************************************************************************
     * 
     * A method that determines the actions of the monsters from the queue.
     * 
     ********************************************************************************************************/
    public void monstersTurn() {
        //A variable that will be set to the monster in the queue.
        Creature monster;

        /*The loop is used to reduce the number of code written. First determines the action the monster from
        the north, then south, then east, then west.*/
        for (int i = 0; i < 4; i++) {
            //Variable used to get the queue direction the monster is in.
            Queue<Creature> queueDirection = null;
            
            //Variable for the print statement
            String direction = "";
            
            //The conditional statement gets the queue.
            if (i == 0) {
                queueDirection = northQueue;
                direction = "North";
            } 
            else if (i == 1) {
                queueDirection = southQueue;
                direction = "South";
            }
            else if (i == 2) {
                queueDirection = eastQueue;
                direction = "East";
            } 
            else if (i == 3) {
                queueDirection = westQueue;
                direction = "West";
            }


            //Checks to see if queue is not empty to get monster.
            if(queueDirection.size() != 0){
                /*Monster variable is set to the monster of a queue direction determined from the conditional statement
                above*/
                monster = queueDirection.peek();

                
                

                //Checks to see if the monster is dead.
                if (monster.isDead()) {
                    //Add XP to the player from the dead monster.
                    player.addXP(monster.getStrength() + monster.getHealth());
                    System.out.println("The " + direction + " monster has been slained!");
                    System.out.println("You gained " + (monster.getStrength() + monster.getHealth()) + " XP from the " + direction + " monster!");
                    System.out.println();


                    //If the monster is dead, it is removed from the given queue.
                    queueDirection.dequeue();

                    //Checks to see if the queue is empty so that it won't perform the action of a null or dead monster.
                    if (queueDirection.size() != 0) {
                        //Prompts that a monster is dead, but another has appeared if queue is not empty.
                        System.out.println("However there is another monster in queue...");
                        //Gets the next monster from the queue.
                        monster = queueDirection.peek();
                    } 
                    //If the queue is empty monster is set to null so it won't run the action of a dead monster.
                    else {
                        monster = null;
                    }
                }
                //If the monster is not null, actions will be performed (if not frozen also).
                if (monster != null) {
                    if(!monster.isFrozen()){
                    //First checks to see if a spell can be cast, otherwise will do a regular attack.
                    if (monster.canCast(SpellType.FIRE)) {
                        monster.setOnFire(player);
                        System.out.println("The " + direction + " monster set you on fire!");
                    }
                    else if (monster.canCast(SpellType.FROST)) {
                        monster.freeze(player);
                        System.out.println("The " + direction + " monster froze you!");
                    }
                    else if(monster.canCast(SpellType.LIGHTNING)){
                        monster.electrocute(player);
                        System.out.println("The " + direction + " monster shocked you!");
                    }
                    else if (monster.canCast(SpellType.HEAL)){
                        monster.heal(monster.getStrength());
                        System.out.println("The " + direction + " monster healed themselves!");
                    }
                    else{
                        int monsterDamage = monster.attack();
                        player.hurt(monsterDamage);
                        System.out.println("The " + direction + " monster damaged you for " + monsterDamage);
                    }
                }
                //If the monster is frozen, print this.
                else{
                    System.out.println("The monster from the " + direction + "is frozen, losing its turn.\n");
                }

                    //Decrement the time of the monster status effects.
                    monster.decFireTimer();
                    monster.decFreezeTimer();
                }
                

        }
    }
    //Used to seperate the rounds.
    System.out.println("=======================================================================================");
    }

    /***********************************************************************************************************
     * 
     * A method that prints to the console the player's health, strength, and the stats of the monsters from
     * the queue direction, also checks to see if the monster is dead again (damage from fire).
     **********************************************************************************************************/
    public void getSituation() {

        //A loop to loop around the monsters of the queues to print their stats.
        Creature monster;
        for (int i = 0; i < 4; i++) {
            //Determines the queue being looked at from loop.
            String direction = "";
            Queue<Creature> queueDirection;
            if (i == 0) {
                queueDirection = northQueue;
                direction = "North";
            } else if (i == 1) {
                queueDirection = southQueue;
                direction = "South";
            }

            else if (i == 2) {
                queueDirection = eastQueue;
                direction = "East";
            } 
            else {
                queueDirection = westQueue;
                direction = "West";
            }

            //Determines if the queue is empty.
            if (queueDirection.size() == 0) {
                System.out.println("There are no monster from the " + direction + ". For now...");
                System.out.println("");
            } 
            else {
                /*Gets the first monster in the queue, if the monster is dead, add XP to player
                and get the next monster and prints its stats (direction, strength, health, spell respectively). 
                 If not dead get the stats of the monster */
                monster = queueDirection.peek();
                if(monster.isDead()){
                    player.addXP((monster.getStrength() + monster.getHealth()));
                    System.out.println("A monster from the " + direction + " succumbed from it wounds. You gained " + (monster.getStrength() + monster.getHealth()) + " XP.");
                    monster = null;
                    queueDirection.dequeue();
                    monster = queueDirection.peek();
                }
                System.out.println("The monster from the " + direction + " has:");
                System.out.println(monster.getStrength() + " Strength");
                System.out.println(monster.getCurrentHealth() + "/" + monster.getHealth() + " Health left");
                SpellType monsterSpell = monster.getSpell();
                System.out.println("Spell: " + monsterSpell);
                System.out.println();
            }
        }
        //Prints the monster left in queue.
        System.out.println("\nMonsters left in queue:");
        System.out.println("North: " + northQueue.size());
        System.out.println("South: " + southQueue.size());
        System.out.println("East: " + eastQueue.size());
        System.out.println("West: " + westQueue.size());
        System.out.println();
        //Gets the current stats of the player.
        System.out.println("Your health is currently: " + player.getCurrentHealth() + "/" + player.getHealth());
        System.out.println("Your strength is: " + player.getStrength());
        System.out.println("Your current XP is: " + player.getXP());
        System.out.println();
    }




    //RUN this code to play the game.
    public static void main(String[] args){
        Arena game = new Arena();
        game.gameLoop();
        

    }
}
