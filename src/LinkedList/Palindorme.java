package LinkedList;

import Tools.Tools;

public class Palindorme {

    /**
     * 判断一个列表是否为回文结构
     * 将后半段链表反转，与前半段对比，最后再将链表还原
     * 
     * @param head
     * @return
     */
    public static boolean isPalindorme(Node head) {
        boolean res = true;
        if (head == null || head.next == null) {
            return res;
        }
        Node right = head; // 慢指针
        Node left = head; // 快指针
        while (right.next != null && right.next.next != null) {
            left = left.next;
            right = right.next.next;
        }

        Node helper = null; // 用来暂存下一个需要调整的node
        right = left.next; // left是前半段最后一个node，将后半段链表的头传给right
        left.next = null; // 将前半段链表最后一个的next设置为null，断开前半段与后半段的联系
        // left = null; // 本来应该设置为null，但是为了留有前半段链表尾巴的信息，后半段链表的尾部多加一个node
        while (right != null) {
            helper = right.next; // 暂存下一个node
            right.next = left; // next指针反向，指向上一个
            left = right; // 上一个向下移
            right = helper; // 这一个向下移
        }

        helper = left; // 保存后半段链表的头部
        right = head; // 找到前半段链表的头部
        // 虽然后半段链表尾部多了一个node，但是前半段后半段同时往下走
        // 全链个数为偶数时，前半段的长度限制确保两个链表比较时不会考虑多出来的node
        // 全链个数为奇数时，多出来的node是前半段最后一个node，前后仍然一致
        // 偶数如：[1,2,2,1] 前半段为[1,2] 后半段为[1,2,2] 但是前半段的长度限制，不会比较后半段多出来的node
        // 奇数如：[1,2,3,2,1] 前半段为[1,2,3] 后半段也为[1,2,3]
        while (left != null && right != null && res) {
            if (left.value != right.value) { // 依次比较每一位
                res = false;
            }
            right = right.next;
            left = left.next;
        }

        left = null;
        right = null;
        while (helper != null) { // 恢复链表
            left = helper.next;
            helper.next = right;
            right = helper;
            helper = left;
        }

        // Tools.printLinkedList(head, "Linked List:", false);

        return res;

    }

    public static boolean isPalindrome_1(Node head) {
        if (head == null) {
            return true;
        }

        // 找到前半部分链表的尾节点并反转后半部分链表
        Node firstHalfEnd = endOfFirstHalf(head);
        Node secondHalfStart = reverseList(firstHalfEnd.next);

        // 判断是否回文
        Node p1 = head;
        Node p2 = secondHalfStart;
        boolean result = true;
        while (result && p2 != null) {
            if (p1.value != p2.value) {
                result = false;
            }
            p1 = p1.next;
            p2 = p2.next;
        }

        // 还原链表并返回结果
        firstHalfEnd.next = reverseList(secondHalfStart);
        return result;
    }

    private static Node reverseList(Node head) {
        Node prev = null;
        Node curr = head;
        while (curr != null) {
            Node nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    private static Node endOfFirstHalf(Node head) {
        Node fast = head;
        Node slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    public static void main(String[] args) {

        // Tools.printLinkedList(head, "Linked List:", false);

        for (int i = 0; i < 100000; i++) {

            Node head = Tools.getRandomLinkedList(10, 1);

            if (isPalindrome_1(head) != isPalindorme(head)) {
                System.out.println("WRONG!!!!!!");
                break;
            }
        }
        System.out.println("WIN");

        // System.out.println(isPalindorme(head) ? "This IS a palindorme" : "This is Not
        // a palindorme");
    }

}
