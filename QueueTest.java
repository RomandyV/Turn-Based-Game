package src;

import org.junit.Test;

import static org.junit.Assert.*;

public class QueueTest {

    @Test
    public void testQueue(){
        Queue<Integer> queue = new Queue<Integer>();
        assertTrue(queue.size() == 0);
        queue.enqueue(0);
        assertTrue(queue.size() == 1);
        assertTrue(queue.peek() == 0);
        assertTrue(!queue.isEmpty());
        assertTrue(queue.dequeue() == 0);
    }

    @Test
    public void testEnqueue(){
        Queue<Integer> queue = new Queue<Integer>();
        queue.enqueue(0);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        assertTrue(queue.peek() == 0);
        assertTrue(queue.size() == 6);
    }

    @Test
    public void testDequeue(){
        Queue<Integer> queue = new Queue<Integer>();
        queue.enqueue(0);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        assertTrue(queue.peek() == 0);
        assertTrue(queue.dequeue() == 0);
        assertTrue(queue.dequeue() == 1);
        assertTrue(queue.dequeue() == 2);
        assertTrue(queue.dequeue() == 3);
        assertTrue(queue.dequeue() == 4);
        assertTrue(queue.dequeue() == 5);
        assertTrue(queue.size() == 0);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testDequeueEmpty(){
        Queue<Integer> queue = new Queue<Integer>();
        queue.dequeue();
    }

    @Test
    public void testPeek(){
        Queue<Integer> queue = new Queue<Integer>();
        queue.enqueue(0);
        assertTrue(queue.peek() == 0);
        queue.dequeue();
        queue.enqueue(1);
        assertTrue(queue.peek() == 1);
        queue.dequeue();
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testNothingToPeek(){
        Queue<Integer> queue = new Queue<Integer>();
        queue.peek();
    }

    @Test
    public void testSize(){
        Queue<Integer> queue = new Queue<Integer>();
        assertTrue(queue.size() == 0);
        queue.enqueue(0);
        assertTrue(queue.size() == 1);
        queue.enqueue(1);
        assertTrue(queue.size() == 2);
        queue.dequeue();
        assertTrue(queue.size() == 1);
    }

    @Test
    public void testIfEmpty(){
        Queue<Integer> queue = new Queue<Integer>();
        assertTrue(queue.isEmpty());
        queue.enqueue(0);
        assertFalse(queue.isEmpty());
        queue.dequeue();
        assertTrue(queue.isEmpty());


    }






    
}
