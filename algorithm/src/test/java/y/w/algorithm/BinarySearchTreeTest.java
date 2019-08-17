package y.w.algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test Binary Search Tree
 *
 * @author ywang
 * Date: 5/20/2019
 */
public class BinarySearchTreeTest
{
    int[] nums = new int[] {
        15, 6, 18, 3, 7, 2, 13, 4, 9, 17, 20
    };

    @Test
    public void testDefaultConstruction()
    {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        assertTrue(bst.size() == 0);
        assertEquals(bst.root(), BinarySearchTree.NIL);
    }

    @Test
    public void testInsertDelete()
    {
        Node<Integer> node = new Node<>(15);

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        bst.insert(node);

        assertTrue(bst.size() == 1);
        assertEquals(bst.root(), node);

        bst.delete(node);

        assertTrue(bst.size() == 0);
        assertEquals(bst.root(), BinarySearchTree.NIL);
    }

    @Test
    public void testDeleteRootNode()
    {
        Node<Integer> node1 = new Node<>(15);
        Node<Integer> node2 = new Node<>(6);

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

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
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        for (int i=0; i<nums.length; i++)
            bst.insert(nums[i]);

        assertTrue(bst.size() == nums.length);

        Iterator<Node<Integer>> iter = bst.iterator();

        Node<Integer> x = iter.next();
        for (; iter.hasNext();)
        {
            Node<Integer> y = iter.next();
            assertTrue( x.compareTo(y) <= 0);
            x = y;
        }
    }

    @Test
    public void testBSTProperties()
    {
        ArrayList<Node<Integer>> nodes = new ArrayList<>();

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        for (int i=0; i<nums.length; i++)
        {
            Node<Integer> node = new Node<>(nums[i]);
            bst.insert(node);
            nodes.add(node);
        }

        for (Node<Integer> node : nodes)
        {
            if (node.p != BinarySearchTree.NIL)
            {
                if (node.p.left == node)
                    assertTrue(node.value.compareTo((Integer) (node.p.value)) <= 0);
                if (node.p.right == node)
                    assertTrue(node.value.compareTo((Integer) (node.p.value)) >= 0);
            }
            if (node.left != BinarySearchTree.NIL)
                assertTrue(node.value.compareTo((Integer)(node.left.value)) >= 0);
            if (node.right != BinarySearchTree.NIL)
                assertTrue(node.value.compareTo((Integer)(node.right.value)) <= 0);
        }
    }

    @Test
    public void testSuccessor()
    {
        ArrayList<Node<Integer>> nodes = new ArrayList<>();

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

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
}
