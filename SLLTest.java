package src;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

public class SLLTest {
   
    @Test
    public void testLength(){
        SLL<Integer> list = new SLL<Integer>();

        assertTrue(list.length() == 0);

        list.add(1);

        assertTrue(list.length() == 1);

        list.add(1);
        list.add(1);

        assertTrue(list.length() == 3);

    }

    @Test

    public void testAdd(){
        SLL<Integer> list = new SLL<Integer>();

        list.add(1);

        list.add(1);

        assertTrue(list.length() == 2);

        assertTrue(list.add(1));
    }

    @Test

    public void testClear(){
        SLL<Integer> list = new SLL<Integer>();
        list.add(0);
        list.add(2);
        list.add(3);
        assertTrue(list.length() == 3);
        list.clear();
        assertTrue(list.length() == 0);
        assertFalse(list.length() == 3);
    }

    @Test
    public void testAddWithIndex(){
        SLL<Integer> list = new SLL<Integer>();

        list.add(0,1);
        list.add(0,2);
        list.add(0,3);
        list.add(1,4);

        assertTrue(list.get(0) == 3);
        assertTrue(list.get(1) == 4);
        assertTrue(list.get(2) == 2);
        assertTrue(list.get(3) == 1);
    }

    @Test (expected = IndexOutOfBoundsException.class)

    public void invalidAddWithIndex(){
        SLL<Integer> list = new SLL<Integer>();
        list.add(4343, 5);
    }

    @Test

    public void testGet(){
        SLL<Integer> list = new SLL<Integer>();
        list.add(5);
        list.add(4);
        list.add(3);
        list.add(2);
        list.add(1);

        assertTrue(list.get(0)== 5);
        assertTrue(list.get(1)== 4);
        assertTrue(list.get(2)== 3);
        assertTrue(list.get(3)== 2);
        assertTrue(list.get(4)== 1);

    }

    @Test (expected = IndexOutOfBoundsException.class)

    public void OutOfBoundsGet(){
        SLL<Integer> list = new SLL<Integer>();
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.get(100);
    }

    @Test (expected = NoSuchElementException.class)

    public void NoSuchElementGet(){
        SLL<Integer> list = new SLL<Integer>();
        list.add(1);
        list.add(2);
        list.add(null);
        list.get(2);
    }

    @Test

    public void testRemove(){
        SLL<Integer> list = new SLL<Integer>();
        list.add(4);
        list.add(3);
        list.add(2);
        list.add(1);
        list.add(0,0);
        assertTrue(list.remove(0) == 0);
        assertTrue(list.get(0)== 4);
        assertTrue(list.remove(1) == 3);
        assertTrue(list.get(1)== 2);
        assertTrue(list.length() == 3);
        assertTrue(list.get(2) == 1);

        SLL<Integer> list2 = new SLL<Integer>();
        list2.add(1);
        System.out.println("Length is " + list2.length());
        System.out.println("Result is: " + list2.get(0));
        list2.remove(0);
        assertTrue(list2.length() == 0);
        list2.add(1);
        list2.add(2);
        assertTrue(list2.remove(1) == 2);
        list2.clear();
        list2.add(0);
        list2.add(1);
        list2.add(2);
        assertTrue(list2.remove(2) == 2);
        assertTrue(list2.get(1) == 1);
        assertTrue(list2.length() == 2);

        list2.clear();
        list2.add(0);
        list2.add(1);
        assertTrue(list2.remove(0) == 0);
        assertTrue(list2.get(0) == 1);


    }

    @Test (expected = IndexOutOfBoundsException.class)

    public void testRemoveIndexOutOfBounds(){

        SLL<Integer> list = new SLL<Integer>();
        list.remove(0);
    }

    @Test (expected = IndexOutOfBoundsException.class)

    public void testRemoveIndexOutOfBounds2(){
        
        SLL<Integer> list = new SLL<Integer>();
        list.remove(5);
    }

    @Test (expected = IndexOutOfBoundsException.class)

    public void testRemoveIndexOutOfBounds3(){
        
        SLL<Integer> list = new SLL<Integer>();
        list.remove(-2);
    }

    @Test (expected = NoSuchElementException.class)

    public void testRemoveIndexOutOfBounds4(){
        
        SLL<Integer> list = new SLL<Integer>();
        list.add(null);
        list.remove(0);
    }

    @Test

    public void testContains(){
        SLL<Integer> list = new SLL<Integer>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        assertTrue(list.contains(0));
        assertTrue(list.contains(1));
        assertTrue(list.contains(2));
        assertTrue(list.contains(3));
        assertTrue(list.contains(4));
        assertFalse(list.contains(5));
    }

    @Test

    public void testSet(){
        SLL<Integer> list = new SLL<Integer>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(4);
        assertTrue(list.set(3,3) == 4);
        assertTrue(list.set(2,1) == 2);
        assertTrue(list.get(3) == 3);
        assertTrue(list.get(2) == 1);
    }

    @Test (expected = IndexOutOfBoundsException.class)

    public void testSetIndexOutOfBounds(){
        SLL<Integer> list = new SLL<Integer>();
        list.set(-3,5);
    }

    @Test (expected = IndexOutOfBoundsException.class)

    public void testSetIndexOutOfBounds2(){
        SLL<Integer> list = new SLL<Integer>();
        list.set(1,53);
    }

    @Test (expected = NoSuchElementException.class)

    public void testSetNoSuchElement(){
        SLL<Integer> list = new SLL<Integer>();
        list.add(null);
        list.set(0,53);
    }

    @Test

    public void testToArray(){
        SLL<Integer> list = new SLL<Integer>();
        for(int i = 0; i < 10; i++){
            list.add(i);
        }

        Object[] array = list.toArray();

        for (int i = 0; i < array.length; i++){
            assertTrue(array[i].equals(i));
        }

    }

    @Test
    public void testReverse(){
        SLL<Integer> list = new SLL<Integer>();
        for(int i = 0; i < 10; i++){
            list.add(i);
        }
        list.reverse();

        for(int i = 0; i <list.length(); i++){
            System.out.println(list.get(i));

            //assertTrue(list.get(i) == i);
        }
    }
}







