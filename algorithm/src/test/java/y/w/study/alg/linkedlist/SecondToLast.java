package y.w.study.alg.linkedlist;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class SecondToLast {
    @Test
    public void t1() {
        ListNode l1 = new ListNode(1);

        assertThat(findSecondToLast(l1)).isNull();

        l1.next = new ListNode(2);
        assertThat(findSecondToLast(l1)).isEqualTo(l1);

        l1.next.next = new ListNode(3);
        assertThat(findSecondToLast(l1)).isEqualTo(l1.next);
    }

    public static ListNode findSecondToLast(ListNode h) {
        if (h == null || h.next == null) return null;
        if (h.next.next == null) return h;

        return findSecondToLast(h.next);
    }
}
