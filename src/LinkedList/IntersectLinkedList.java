package LinkedList;

public class IntersectLinkedList {

    public static Node getIntersection(Node head_1, Node head_2) {

        if (head_1 == null || head_2 == null) {
            return null;
        }
        Node loopNode_1 = getLoopNode(head_1);
        Node loopNode_2 = getLoopNode(head_2);

        if (loopNode_1 != null && loopNode_2 != null) {
            return bothLoop(head_1, loopNode_1, head_2, loopNode_2);
        }

        if (loopNode_1 == null && loopNode_2 == null) {
            return bothNoLoop(head_1, head_2);
        }

        return null;
    }

    public static Node bothNoLoop(Node head_1, Node head_2) {

        Node curr_1 = head_1;
        int count = 0;
        while (curr_1.next != null) {
            curr_1 = curr_1.next;
            count++;
        }

        Node curr_2 = head_2;
        while (curr_2.next != null) {
            curr_2 = curr_2.next;
            count--;
        }

        if (curr_1 != curr_2) {
            return null;
        }

        curr_1 = head_1;
        curr_2 = head_2;
        if (count > 0) {
            while (count > 0) {
                curr_1 = curr_1.next;
                count--;
            }
        } else if (count < 0) {
            while (count < 0) {
                curr_2 = curr_2.next;
                count++;
            }
        }

        while (curr_1 != curr_2) {
            curr_1 = curr_1.next;
            curr_2 = curr_2.next;
        }

        return curr_1;
    }

    public static Node bothLoop(Node head_1, Node loopNode_1, Node head_2, Node loopNode_2) {

        if (loopNode_1 == loopNode_2) {
            Node curr_1 = head_1;
            int count = 0;
            while (curr_1.next != loopNode_1) {
                curr_1 = curr_1.next;
                count++;
            }

            Node curr_2 = head_2;
            while (curr_2.next != loopNode_2) {
                curr_2 = curr_2.next;
                count--;
            }

            curr_1 = head_1;
            curr_2 = head_2;
            if (count > 0) {
                while (count > 0) {
                    curr_1 = curr_1.next;
                    count--;
                }
            } else if (count < 0) {
                while (count < 0) {
                    curr_2 = curr_2.next;
                    count++;
                }
            }

            while (curr_1 != curr_2) {
                curr_1 = curr_1.next;
                curr_2 = curr_2.next;
            }

            return curr_1;
        } else {
            Node curr = loopNode_1.next;
            while (curr != loopNode_1) {
                if (curr == loopNode_2) {
                    return curr;
                }
                curr = curr.next;
            }
            return null;
        }
    }

    public static Node getLoopNode(Node head) {

        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node fast = head.next.next;
        Node slow = head.next;

        while (fast != slow) {
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            fast = fast.next.next;
            slow = slow.next;
        }

        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }

        return fast;
    }

    public static void main(String[] args) {

        Node head_1 = new Node(0);
        Node head_2 = new Node(100);
        Node one = new Node(1);
        Node two = new Node(2);
        Node three = new Node(3);
        Node four = new Node(4);
        Node five = new Node(5);
        Node six = new Node(6);
        Node seven = new Node(7);
        Node eight = new Node(8);
        Node nine = new Node(9);
        Node ten = new Node(10);
        head_1.next = one;
        head_2.next = seven;
        one.next = two;
        two.next = three;
        three.next = four;
        four.next = five;
        five.next = six;
        six.next = seven;
        seven.next = eight;
        eight.next = nine;
        nine.next = ten;
        ten.next = null;

        Node intersection = getIntersection(head_1, head_2);
        String message = intersection == null ? "null" : Integer.toString(intersection.value);
        System.out.println("intersection: " + message);
    }
}
