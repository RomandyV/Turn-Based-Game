package src;

import java.util.NoSuchElementException;

/**********************************************************************
 * 
 *Imitation of a LinkedList
 * "Set/sets" refers to the node data itself being changed, "point/points" means
 * whatever the node data is pointing to (next)
 * @author + Romandy Vu (Worked with Natalie Kline)
 * @version March 17, 2021
 **********************************************************************/
public class SLL<E> {
    
    /**the head of the list. */
    private Node<E> head;
    
    /**the size of the list.*/
    private int length; 

    /********************************************************************
     * 
     * The default constructor
     * 
     *  @param None
     *  @return None
     * *****************************************************************/
    public SLL(){ 
        head = null;
        length = 0;
    }

    /*******************************************************************
     * 
     * This method is an int that returns the length of the list.
     * 
     * @param None
     * @return an integer of the list length.
     *******************************************************************/
    public int length(){
        return this.length;
    }

    /*********************************************************************
     * 
     * A boolean method that returns true when the element is added.
     * 
     * @param element is of type "E" (determined when creating the list).
     * @return True if the element is added to the list.
     *********************************************************************/
    public boolean add(E element){
        /*creates variable "last" of type Node<E>. 
        Similar to creating int variable x (int x;). and sets to the last
        node of the list that contains the last piece of data.*/
        Node<E> last = getLast();
        
        //Creates a Node<E> object to store the input.
        Node<E> newEntry = new Node<E>(); 
        
        //sets the data of newNode to the input.
        newEntry.data = element; 
        
        //checks if head is null, if so sets head to newEntry (node).
        if(head == null){ 
            head = newEntry;
        } 
        //Else points the last node to the newly added Node.
        else {
            last.next = newEntry; 
        }
        //Increments to keep track of the length of the list.
        length++;

        return true;
    }
//DON'T THINK ABOUT THE INDEX. THINK RELATIVE.
    
    /**************************************************************************
     * 
     * A method of type Node<E> (private class of the list) 
     * that returns the last node.
     * 
     * @param None
     * @return the last "node" of the list.
     ***************************************************************************/
    private Node<E> getLast(){
        //Creates a local variable and sets it to the head.
        Node<E> current = head; 

        /*Gets the last node of the list by determine which node points to null
        as the last node SHOULD point to null. Initally checks if the head is null
        as the list could be empty*/
        while(head!=null && current.next != null){ 
            current = current.next;
        }

        return current;
    }

    /******************************************************************************
     * 
     * A private class that helps the SLL class function. 
     * It helps by storing the data/element and the location of the next data/element.
     *****************************************************************************/
    private class Node<E> { 
        /**The data that is being stored */
        public E data; 

        /**The node that "this" node is pointing to*/
        public Node<E> next;
    }
    
    /******************************************************************************
     * 
     *A method that clears the list.
     * @param None
     * @return None (type void)
     *****************************************************************************/
    public void clear(){
        head = null;
        length = 0;
    }

    /*****************************************************************************
     * 
     * A method of that adds the element to the specified index.
     * 
     * @param index the index number the element is being inserted to.
     * @param element the element/data that is being stored of the type that was
     * specified when creating the list.
     * @throws IndexOutOfBoundsException when the index is out of bounds 
     * (less than 0 or is greater or equal to the size of the list (unless the
     * index and length is 0)).
     ****************************************************************************/
    public void add(int index, E element){

        //Checks to see if the index is out of bounds.
        if((index >= length || index < 0) && index != 0){
            throw new IndexOutOfBoundsException();
        }

        //Creates a node to hold the input of the element.
        //TODO Change to Node(); if error.
        Node<E> tmp = new Node<E>();
        tmp.data = element;
        
        //Checks if index is 0, if so,
        if(index == 0){
            /*Points the new node to the head so that the node data of the head
            and the node after doesn't get lost as they store the data/element*/
            tmp.next = head;

            //Sets the head to new node.
            head = tmp;
        } else {
            //Creates a variable that references the the node previous to the index.
            Node<E> prior = getNode(index - 1);
            
            /*Makes the new node point(next) to whatever the previous node was pointing at
            so that data/element doesn't get lost*/
            tmp.next = prior.next;
            //Makes the previous node point to the new node.
            prior.next = tmp;
        }
        //Increments to keep track of the list length.
        length++;
    }

    /****************************************************************************
     * 
     * A method that returns the element of the specified index.
     * 
     * @param index the index that is being looked at.
     * @return the element of the specified index.
     * @throws IndexOutOfBoundsException when the index is greater or equal to
     * the size of the list or is less than 0.
     * @throws NoSuchElementException when the data returned is null.
     ***************************************************************************/
    public E get(int index){
        //Checks to see if index is out of bounds.
        if(index >= length || index < 0){
            throw new IndexOutOfBoundsException();
        }
        //Checks to see if the data is null.
        if(getNode(index).data == null){
            throw new NoSuchElementException();
        }

        return getNode(index).data;
    }

    /**************************************************************************
     * 
     * A private method that returns the node of the list (helps the public
     * class out).
     * 
     * @param index the index of the list that is being looked at.
     * @return the node of the given index.
     * @throws IndexOutOfBoundsException when the index is greater than or equal
     * to the length of the list or is less than 0.
     ****************************************************************************/
    private Node<E> getNode(int index){
        //Checks to see if the index is out of bounds.
        if(index >= length || index < 0){
            throw new IndexOutOfBoundsException();
        }
        //Creates a counter to keep track of how many nodes were passed.
        int count = 0;
        
        /*Creates a variable that starts at the head. This is the variable that is
        being returned */
        Node<E> current = head;
        //Checks until count is at the specified index to return that node information.
        while(count < index){
            current = current.next;
            count++;
        }
        return current;
    }

    /************************************************************************
     * 
     * A method that removes the element at the specified index and returns
     * the data being removed.
     *
     * @param index The index that is being looked at to remove the element at
     * that index.
     * @return the data of the index that is being removed.
     * @throws IndexOutOfBoundsException When the index is greater or equal
     * to the length of the list or is less than 0.
     * @throws NoSuchElementException when the data/element being returned is null
     * 
     ************************************************************************/
    public E remove(int index){
        //Checks to see if index is out of bounds.
        if((index >= length || index < 0)){
            throw new IndexOutOfBoundsException();
        }

        /*Creates a variable that sets to the node of the given index to get
        its data as it needs to be returned.*/
        E returnData = getNode(index).data;
        
        /*Checks the node that is being removed is the head
         if so, sets the head to the next node severing the node that was orginally
         the head*/
        if (index == 0){
                head = head.next;
            
        }
        /*Checks if the node that is being removed is the last node
        if so, points the 2nd to last node to null severing the last node */
        else if(index == length - 1){
            /*Creates a variable that sets to the node of before the given index*/
            Node<E> before = getNode(index - 1);
            before.next = null;    
        }
        /*If not the first or last node, finds the node after the given index
        and makes the node previous to the given index point to the node after the given index*/
        else{
            Node<E> after = getNode(index + 1);
            Node<E> before = getNode(index - 1);
            before.next = after;
        }
        //Decrement list as an element is removed.
        length--;

        //Checks if the data of the index is null
        if(returnData == null){
            throw new NoSuchElementException();
        }
        
        return returnData;
    }

    /***************************************************************************************
     * 
     * A boolean method that return true or false if the input matches an element in the list.
     * 
     * @param element What is being checked to see if it is in the list.
     * @return True if the input matches an element in the list, else false.
     ***************************************************************************************/
    public Boolean contains(E element){
        //Creates a node reference and points to the head.
        Node<E> current = head;

        while(current != null){
            //Checks to see if the data of the node matches.
            if(current.data == element){
                return true;
            }
            //Sets the node reference to point to the next node.
            current = current.next;
        }
        //If no data of all the nodes matches the element
        return false;
    }

    public E set(int index, E element){
        if((index >= length || index < 0)){
            throw new IndexOutOfBoundsException();
        }
        //Gets the data that is being replaced.
        E returnData = get(index);

        //Creates a reference that points to the index of the node that the data is being replaced.
        Node<E> replacedNode = getNode(index);

        //Replaces the data of the node.
        replacedNode.data = element;

        if(getNode(index).data == null){
            throw new NoSuchElementException();
        }
        //Returns the original data.
        return returnData;
    }

    public Object[] toArray(){
        //Creates the list that is being returned.
        Object[] returnList = new Object[length];

        //Creates a reference that points to the first node.
        Node<E> current = head;

        //Fills in the element of the array.
        for(int i = 0; i < length; i++){
            returnList[i] = current.data;
            
            //Sets the reference to the next node.
            current = current.next;
        }

        //Returns the list.
        return returnList;
    }

    public void reverse(){
        /*Only reverses the list when size is greater than 1 
        because if the list length < 1 it would be the same.*/
        if(length > 1){
            //Holds the last node so it doesn't get lost when a "next" is no longer pointing to it.
            Node<E> holder = getLast();

            //The node whose "next" is being changed.
            Node<E> current = getLast();

            //The node previous to "current" node so that "current.next" points to it.
            Node<E> previous; 
            //Helps determine the "previous" node
            //int index = length - 2;

            for(int index = length - 1; index >= 0; --index){
                //Starts off by getting the 2nd to last node, then the node previous to current.
                previous = getNode(index);
                //Sets current.next to the node pointing it (previous).
                current.next = previous;
                //Sets current to previous since the node will need to be changed.
                current = previous;
                //Decrement to keep track of the node that needs to be changed at the given index.
                

            }
            
            //Runs until the index is out of bounds.
            //while (index >= 0){
                
                
            //}
        //Since all the nodes have something pointed to it, sets head.next to null as it is now the last element in the list.
        head.next = null;
        //Sets head to holder (now the first element of the list).
        head = holder;
        }
    }





    
    public static void main(String[] args){
        SLL<Integer> numbers = new SLL<Integer>();
        SLL<String> names = new SLL<String>();

        System.out.println("Numbers has length: " + numbers.length());
        System.out.println("Names has length: " + names.length());

        numbers.add(1);
        numbers.add(1);
        numbers.add(1);
        numbers.add(1);
        numbers.add(1);
        numbers.add(1);
        numbers.add(1);
        numbers.add(1);
        numbers.add(1);
        numbers.add(1);
        names.add("Mr. W");
        names.add("Mr. W");
        names.add("Mr. W");
        names.add("Mr. W");
        names.add("Mr. W");
        names.add("Mr. W");
        names.add("Mr. W");
        names.add("Mr. W");
        names.add("Mr. W");
        names.add("Mr. W");
        names.add("Mr. W");
        System.out.println("Numbers has length: " + numbers.length());
        System.out.println("Names has length: " + names.length());
    }
}