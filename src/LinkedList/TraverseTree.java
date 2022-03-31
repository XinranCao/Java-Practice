package LinkedList;

import java.util.*;

public class TraverseTree {

    /**
     * 不用hashmap，只用常数个额外空间找出树的最大宽度
     * 
     * @param root
     * @return
     */
    public static int maxWidth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        // BFS遍历树，所以用queue
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode lastNode = root; // 当前层最后一个节点
        TreeNode newestNode = root; // 下一层最后一个节点
        int currCount = 0; // 当前层数中节点个数
        int max = -1; // 最宽层中节点总个数

        queue.add(root);
        TreeNode curr = null;
        while (!queue.isEmpty()) {
            // 每取出一个节点，当前层数节点个数+1
            curr = queue.poll();
            currCount++;

            // 将左子节点加入queue
            if (curr.left != null) {
                queue.add(curr.left);
                newestNode = curr.left; // 下一层的最后一个节点更新为左子节点
            }
            // 将右子节点加入queue
            if (curr.right != null) {
                queue.add(curr.right);
                newestNode = curr.right;// 下一层的最后一个节点更新为右子节点
            }

            // 如果当前节点是本层最后一个节点，那么更新统计数据
            if (curr == lastNode) {
                max = Math.max(max, currCount); // 更新max值
                currCount = 0; // 因为当前层已经统计完毕，下个节点开始要统计下一层的节点个数，count归零
                lastNode = newestNode; // 下移一层，当前层最后一个节点为刚才“下一层最后一个节点”
                newestNode = null; // 下一层最后一个节点更新为null
            }
        }
        return max;
    }

    /**
     * 利用hashmap找出树的最大宽度（即：拥有最多节点的层数，有多少节点）
     * 
     * @param root
     * @return
     */
    public static int maxWidthHashMap(TreeNode root) {
        if (root == null) {
            return 0;
        }

        // BFS遍历树，所以用queue
        Queue<TreeNode> queue = new LinkedList<>();
        // hashmap用于记录Node与其所在的层数
        HashMap<TreeNode, Integer> map = new HashMap<>();
        int currDepth = 1; // 当前层数
        int currCount = 0; // 当前层数中，目前有多少个节点
        int max = -1; // 拥有最多节点层数中，总共有多少节点

        // 先将头节点的信息加入queue和hashmap中
        queue.add(root);
        map.put(root, currDepth);
        TreeNode curr = null;
        while (!queue.isEmpty()) {
            curr = queue.poll();
            // 每次取出一个节点，然后查看这个节点是否在当前层
            if (map.get(curr) == currDepth) { // 如果在当前层，那么当前层数的总结点数+1
                currCount++;
            } else { // 如果不在当前层，说明上一层所有节点已经统计完毕，需要更新统计信息
                max = Math.max(max, currCount); // max更新为max与上一层总结点数两者中的最大值
                currCount = 1; // 本层刚开始统计，遇到一个新节点，所以统计总数为1
                currDepth++; // 当前层数+1
            }

            // 将左子节点的信息加入queue和hashmap中
            if (curr.left != null) {
                queue.add(curr.left);
                map.put(curr.left, currDepth + 1); // 子节点的所在层数为当前层数+1
            }
            if (curr.right != null) {
                queue.add(curr.right);
                map.put(curr.right, currDepth + 1);// 子节点的所在层数为当前层数+1
            }
        }
        // 上面的loop结束时，并没有将最后一层的统计信息做汇总，所以这里取Math.max
        return Math.max(max, currCount);
    }

    /**
     * breadth first search 宽度遍历
     * 按层数一层一层遍历树
     * 
     * @param root
     */
    public static void BFS(TreeNode root) {
        if (root == null) {
            return;
        }
        // 用queue遍历，先进先出。保证先把一层的节点遍历完后再遍历下一层的节点
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        TreeNode curr = null;
        while (!queue.isEmpty()) {
            // 最先处理自己（先处理本层，然后将下一层节点放入queue中）
            curr = queue.poll();
            System.out.print(curr.value + " ");

            // 先左
            if (curr.left != null) {
                queue.add(curr.left);
            }
            // 后右
            if (curr.right != null) {
                queue.add(curr.right);
            }
        }

    }

    /**
     * 先序遍历
     * 
     * @param root
     */
    public static void preOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        // 用于遍历的栈
        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        TreeNode curr = null;
        // 先打印自己，然后遍历左子树，再遍历右子树
        while (!stack.isEmpty()) {
            curr = stack.pop();
            System.out.print(curr.value + " ");
            if (curr.right != null) {
                stack.add(curr.right);
            }
            if (curr.left != null) {
                stack.add(curr.left);
            }
        }
    }

    /**
     * 中序遍历
     * 
     * @param root
     */
    public static void inOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        // 用于遍历的栈
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = null;
        // 先将左子树打印完，再打印自己，再打印右子树。
        while (!stack.isEmpty() || root != null) {
            if (root != null) { // 不断取左子节点，直到null
                stack.add(root);
                root = root.left;
            } else { // 如果没有左子节点了，从栈中取出一个节点，并打印
                curr = stack.pop();
                System.out.print(curr.value + " ");
                // 然后开始遍历右子树
                root = curr.right;
            }
        }
    }

    /**
     * 后序遍历
     * 
     * @param root
     */
    public static void postOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        // 用于遍历的栈
        Stack<TreeNode> stack = new Stack<>();
        // 用于反向取出所有节点并打印，记录的是后序遍历的相反顺序
        Stack<TreeNode> reverse = new Stack<>();

        // 将头放入栈中
        stack.add(root);

        TreeNode curr = null;
        while (!stack.isEmpty()) {
            curr = stack.pop();
            // 从栈中取出的节点立即放入reverse栈中
            reverse.add(curr);
            // 将左子节点入栈
            if (curr.left != null) {
                stack.add(curr.left);
            }
            // 将右子节点入栈
            if (curr.right != null) {
                stack.add(curr.right);
            }
        }
        // 将所有节点从reverse中依次拿出并打印
        while (!reverse.isEmpty()) {
            System.out.print(reverse.pop().value + " ");
        }
    }

    /**
     * 利用递归中序遍历二叉树
     * 
     * @param root
     */
    public static void preOrderRecur(TreeNode root) {
        if (root == null) {
            return;
        }
        // 打印自己
        System.out.print(root.value + " ");
        // 递归遍历左子树
        if (root.left != null) {
            preOrderRecur(root.left);
        }
        // 递归遍历右子树
        if (root.right != null) {
            preOrderRecur(root.right);
        }
        return;
    }

    /**
     * 利用递归中序遍历二叉树
     * 
     * @param root
     */
    public static void inOrderRecur(TreeNode root) {
        if (root == null) {
            return;
        }
        // 递归遍历左子树
        if (root.left != null) {
            inOrderRecur(root.left);
        }
        // 打印自己
        System.out.print(root.value + " ");
        // 递归遍历右子树
        if (root.right != null) {
            inOrderRecur(root.right);
        }
        return;
    }

    /**
     * 利用递归后序遍历二叉树
     * 
     * @param root
     */
    public static void postOrderRecur(TreeNode root) {
        if (root == null) {
            return;
        }
        // 递归遍历左子树
        if (root.left != null) {
            postOrderRecur(root.left);
        }
        // 递归遍历左子树
        if (root.right != null) {
            postOrderRecur(root.right);
        }
        // 打印自己
        System.out.print(root.value + " ");
        return;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(0);
        TreeNode node_1 = new TreeNode(1);
        TreeNode node_2 = new TreeNode(2);
        TreeNode node_3 = new TreeNode(3);
        TreeNode node_4 = new TreeNode(4);
        TreeNode node_5 = new TreeNode(5);
        TreeNode node_6 = new TreeNode(6);
        TreeNode node_7 = new TreeNode(7);
        TreeNode node_8 = new TreeNode(8);

        root.left = node_1;
        root.right = node_2;
        node_1.left = node_3;
        node_1.right = node_4;
        node_2.left = node_5;
        node_2.right = node_6;
        node_3.right = node_7;
        node_6.left = node_8;

        preOrderRecur(root);
        System.out.println(" ");
        preOrder(root);
        System.out.println(" ");

        inOrderRecur(root);
        System.out.println(" ");
        inOrder(root);
        System.out.println(" ");

        postOrderRecur(root);
        System.out.println(" ");
        postOrder(root);
        System.out.println(" ");

        BFS(root);
        System.out.println(" ");

        System.out.println("max: " + maxWidthHashMap(root));
        System.out.println("max: " + maxWidth(root));
    }
}
