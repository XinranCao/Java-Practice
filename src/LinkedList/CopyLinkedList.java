package LinkedList;

import Tools.Tools;

public class CopyLinkedList {

    /**
     * 拷贝一个完整linked List, 并将其中的rand信息拷贝
     * rand信息是一个指针，随机指向链表中任意一个node
     * 
     * @param head
     * @return
     */
    public static Node copyLinkedList(Node head) {
        if (head == null) {
            return null;
        }
        // 先依次拷贝整个链表(不设置rand信息)
        // 并将其按照 原始node0-拷贝node0-原始node1-拷贝node1的顺序链接
        Node curr = head;
        Node temp = null;
        while (curr != null) {
            temp = curr.next;
            curr.next = new Node(curr.value);
            curr.next.next = temp;
            curr = temp;
        }

        // 依次设置拷贝node中的rand值
        curr = head;
        while (curr != null) {
            temp = curr.next;
            temp.rand = curr.rand == null ? null : curr.rand.next;
            curr = temp.next;
        }

        // 将原始链表与拷贝链表从合成的链表中分离
        curr = head;
        Node cloneHead = head.next; // 保存拷贝链表的头
        while (curr != null) {
            temp = curr.next;
            curr.next = temp.next;
            temp.next = temp.next == null ? null : curr.next.next;
            curr = curr.next;
        }

        return cloneHead;
    }

    public static void main(String[] args) {

        Node head = Tools.getRandomLinkedList(10, 10);

        Node temp = head;
        while (temp != null) {
            temp.rand = temp.next;
            temp = temp.next;
        }

        Tools.printLinkedList(head, "Linked List:", true);
        Node cloneHead = copyLinkedList(head);
        Tools.printLinkedList(cloneHead, "Cloned List:", true);
    }
}
