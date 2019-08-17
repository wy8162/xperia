package y.w.algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test Red Black Binary Search Tree
 *
 * @author ywang
 * Date: 5/21/2019
 */
public class RedBlackBSTTest
{
    int[] nums = new int[] {
            15, 6, 18, 3, 7, 2, 13, 4, 9, 17, 20,
    };

    int[] nums2 = new int[] {
            26, 17, 41, 14, 21, 30, 47, 10, 16, 19, 23, 28, 38, 7, 12, 15, 20, 35, 39, 3,
    };

    @Test
    public void testDefaultConstruction()
    {
        RedBlackBST<Integer> bst = new RedBlackBST<>();

        assertTrue(bst.size() == 0);
        assertEquals(bst.root(), RedBlackBST.NIL);
    }

    @Test
    public void testInsertDelete()
    {
        Node<Integer> node = new Node<>(15);

        RedBlackBST<Integer> bst = new RedBlackBST<>();

        bst.insert(node);

        assertTrue(bst.size() == 1);
        assertEquals(bst.root(), node);

        bst.delete(node);

        assertTrue(bst.size() == 0);
        assertEquals(bst.root(), RedBlackBST.NIL);
    }

    @Test
    public void testDeleteRootNode()
    {
        Node<Integer> node1 = new Node<>(15);
        Node<Integer> node2 = new Node<>(6);

        RedBlackBST<Integer> bst = new RedBlackBST<>();

        bst.insert(node1);
        bst.insert(node2);

        assertTrue(bst.size() == 2);
        assertEquals(bst.root(), node1);

        assertTrue(node1.left == node2);
        assertTrue(node1.right == Node.NIL);
        assertTrue(node2.right == Node.NIL);
        assertTrue(node2.left == Node.NIL);
        assertTrue(node2.p == node1);

        bst.delete(node1);

        assertTrue(bst.size() == 1);
        assertEquals(bst.root(), node2);

        Iterator<Node<Integer>> iter = bst.iterator();

        Node<Integer> x = iter.next();
        for (; iter.hasNext();)
        {
            Node<Integer> y = iter.next();
            assertTrue( x.compareTo(y) <= 0);
        }
    }

    @Test
    public void testSortedData()
    {
        RedBlackBST<Integer> bst = new RedBlackBST<>();

        for (int i=0; i<nums.length; i++)
            bst.insert(nums[i]);

        assertTrue(bst.size() == nums.length);

        Iterator<Node<Integer>> iter = bst.iterator();

        Node<Integer> x = iter.next();
        for (; iter.hasNext();)
        {
            Node<Integer> y = (Node<Integer>)(iter.next());
            assertTrue( x.compareTo(y) <= 0);
            x = y;
        }
    }

    @Test
    public void testBSTProperties()
    {
        ArrayList<Node<Integer>> nodes = new ArrayList<>();

        RedBlackBST<Integer> bst = new RedBlackBST<>();

        for (int i=0; i<nums.length; i++)
        {
            Node<Integer> node = new Node<>(nums[i]);
            bst.insert(node);
            nodes.add(node);
        }

        for (Node<Integer> node : nodes)
        {
            if (node.p != RedBlackBST.NIL)
            {
                if (node.p.left == node)
                    assertTrue(node.value.compareTo((Integer) (node.p.value)) <= 0);
                if (node.p.right == node)
                    assertTrue(node.value.compareTo((Integer) (node.p.value)) >= 0);
            }
            if (node.left != RedBlackBST.NIL)
                assertTrue(node.value.compareTo((Integer)(node.left.value)) >= 0);
            if (node.right != RedBlackBST.NIL)
                assertTrue(node.value.compareTo((Integer)(node.right.value)) <= 0);
        }
    }

    @Test
    public void testSuccessor()
    {
        ArrayList<Node<Integer>> nodes = new ArrayList<>();

        RedBlackBST<Integer> bst = new RedBlackBST<>();

        for (int i=0; i<nums.length; i++)
        {
            Node<Integer> node = new Node<>(nums[i]);
            bst.insert(node);
            nodes.add(node);
        }
        //        Original:
        //        15, 6, 18, 3, 7, 2, 13, 4, 9, 17, 20
        //        0   1  2   3  4  5  6   7  8  9   10
        //
        //        Sorted:
        //        2, 3, 4, 6, 7, 9, 13, 15, 17, 18, 20
        //        5  3  7  1  4  8  6*  0*  9*  2   10 <- list of successors

        assertTrue(bst.successorOf(nodes.get(6)) == nodes.get(0));
        bst.delete(nodes.get(0));
        assertTrue(bst.successorOf(nodes.get(6)) == nodes.get(9));
    }

    @Test
    public void testReBlackBSTProperties()
    {
        RedBlackBST<Integer> bst = new RedBlackBST<>();

        for (int i=0; i<nums2.length; i++)
            bst.insert(nums2[i]);
        verifyRedBlackTreeProperties(bst);
    }

    @Test
    public void testRandomNumbers()
    {
        List<Integer> range = IntStream.range(0, 500).boxed()
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(range);

        RedBlackBST<Integer> bst = new RedBlackBST<>();

        for (Integer n : range)
            bst.insert(n);

        verifyRedBlackTreeProperties(bst);
    }

    private void verifyRedBlackTreeProperties(RedBlackBST<Integer> bst)
    {
        boolean BLACK = false, RED = true;

        // 1. Every node is either red or black - assured by design, color is either red / true or black / false.
        // 2. The root is black
        assertTrue(bst.root().color == BLACK);

        // 3. Every leaf is black - assured by design, all leaf is NIL which is black.

        // 4. For each node, all simple paths from the node to descendant leaves contain the same number
        // of black nodes.
        Iterator<Node<Integer>> iter = bst.iterator();
        while (iter.hasNext())
        {
            int leftCountOfBlacks = 0, rightCountOfBlacks = 0;

            Node<Integer> currentNode = iter.next();

            // Count the black nodes along the left path nodes
            Iterator<Node<Integer>> it = bst.leftPathIterator(currentNode);
            while (it.hasNext())
            {
                Node<Integer> node = it.next();
                leftCountOfBlacks += node.color == BLACK ? 1 : 0;
            }

            // Count the black nodes along the right path nodes
            it = bst.rightPathIterator(currentNode);
            while (it.hasNext())
            {
                Node<Integer> node = it.next();
                rightCountOfBlacks += node.color == BLACK ? 1 : 0;
            }

            // They must be equal.
            assertTrue(leftCountOfBlacks == rightCountOfBlacks);

            //System.out.println(String.format("%d -> %d", currentNode.value, leftCountOfBlacks));
        }
    }
}
