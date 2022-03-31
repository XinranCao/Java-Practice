package Tools;

import LinkedList.Node;

public class Tools {

    /**
     * 打印链表信息
     * 
     * @param head
     * @param message  数组前打印一段话
     * @param randFlag 是否打印rand信息
     */
    public static void printLinkedList(Node head, String message, boolean randFlag) {
        System.out.print(message + " [ ");
        while (head != null) {
            String rand = head.rand == null ? null : Integer.toString(head.rand.value);
            String extralContent = randFlag ? " (" + rand + ") " : "";
            System.out.print(head.value + extralContent);
            head = head.next;
        }
        System.out.println("]");
    }

    /**
     * 打印整数数组
     * 
     * @param arr
     * @param message 在数组前打印一段话
     */
    public static void printIntArr(int[] arr, String message) {
        System.out.print(message + " [");
        for (int curr : arr) {
            System.out.print(" " + curr);
        }
        System.out.println(" ]");
    }

    /**
     * 交换数组内arr[i]和arr[j]的位置
     * 
     * @param arr
     * @param i
     * @param j
     */
    public static void swap(int[] arr, int i, int j) {
        if (i == j) {
            return;
        }
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    public static Node getRandomLinkedList(int maxSize, int maxValue) {
        int[] arr = Tools.genRandomArr(maxSize, maxValue);
        Node head = new Node(arr[0]);
        Node temp = head;
        for (int j = 1; j < arr.length; j++) {
            Node newNode = new Node(arr[j]);
            temp.next = newNode;
            temp = newNode;
        }
        return head;
    }

    /**
     * 生成长度随即的随机整数array
     * 
     * @param maxSize  最大长度
     * @param maxValue 最大数值
     * @return
     */
    public static int[] genRandomArr(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        if (arr.length == 0) {
            return new int[] { (int) (10 * Math.random()) };
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Math.abs((int) ((maxValue + 1) * Math.random()) - (int) ((maxValue) * Math.random()));
        }
        return arr;
    }
}
