package y.w.study.alg.linkedlist;

public class ListNode {
     int val;
     ListNode next;
     ListNode random;
     ListNode() {}
     ListNode(int val) { this.val = val; }

     public ListNode(int val, ListNode next, ListNode random) {
          this.val = val;
          this.next = next;
          this.random = random;
     }

     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
