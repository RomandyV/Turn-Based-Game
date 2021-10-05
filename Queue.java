package src;


/**************************************************************************
 * 
 * A Class that imitates a queue (First one in, First one out).
 * 
 * @author Romandy Vu
 * @version Earlier than 4/2/2021
 *************************************************************************/
public class Queue<E> extends SLL<E> {
    
    /*********************************************************************
     * 
     * The default constructor.
     * 
     *********************************************************************/
    public Queue(){
        super();
    }

    /*************************************************************************
     * 
     * A method that adds the item to the end of the queue.
     * 
     * @param item the item of element E (The type specified when 
     * creating the queue)  that is being added to the queue.
     * @return none (Void return type)
     *********************************************************************/
    public void enqueue(E item){
        //Calls the method of the SLL class (parent/super class)
        add(item);
        
    }

    /*********************************************************************
     * 
     * A method that removes and return the first item of the current queue
     * 
     * @param None
     * @return The first element that was first in queue before being removed
     * is of type "E" (The type specified when creating the queue)
     *********************************************************************/
    public E dequeue(){
        //Calls the method of the SLL class (parent/super class)
        return remove(0);

    }

    /***********************************************************************
     * 
     * A method that returns the size/length of the queue. 
     * 
     * @param None
     * @return an int that represent the length of the queue.
     ***********************************************************************/
    public int size(){
        return length();
    }

    /*******************************************************************************
     * 
     * A method that returns the first element of the queue(but does not remove
     * from the queue).
     * 
     * @param None
     * @return the element of type "E" (The type specified when creating the queue).
     *******************************************************************************/
    public E peek(){
        return get(0);
    }

    /********************************************************************************
     * 
     * A method that returns a boolean value if the queue is empty.
     * 
     * @param None
     * @return a boolean value if the size is 0 (true) or greater than 0 (false).
     *******************************************************************************/
    public boolean isEmpty(){
        if(size() == 0){
            return true;
        }

        return false;
    }


}
