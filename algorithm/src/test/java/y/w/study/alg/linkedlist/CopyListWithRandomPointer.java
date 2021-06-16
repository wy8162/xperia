package y.w.study.alg.linkedlist;

import java.util.HashMap;
import java.util.Map;

/**
 * A linked list of length n is given such that each node contains an additional random pointer,
 * which could point to any node in the list, or null.
 */
public class CopyListWithRandomPointer {
    Map<ListNode, ListNode> nodes = new HashMap<>();

    public ListNode copyRandomList(ListNode head) {
        nodes.put(null, null);

        return copyListNode(head);
    }

    private ListNode copyListNode(ListNode node) {
        if (nodes.containsKey(node)) return nodes.get(node);

        ListNode newListNode = new ListNode(node.val, null, null);
        nodes.put(node, newListNode);

        newListNode.next = copyListNode(node.next);
        newListNode.random = copyListNode(node.random);

        return newListNode;
    }
}
