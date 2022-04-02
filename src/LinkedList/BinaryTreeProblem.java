package LinkedList;

public class BinaryTreeProblem {

    /**
     * 寻找一棵树中，中序遍历时，一给定节点的下一个遍历节点。
     * 
     * @param node
     * @return
     */
    public static TreeNode findSuccessor(TreeNode node) {
        if (node == null) {
            return node;
        }

        // 记录本节点的父节点
        TreeNode parent = node.parent;
        if (node.right != null) { // 如果本节点有右子树，那么右子树中最左边的节点就是下一个遍历节点
            node = node.right;
            // 寻找右子树中最左节点
            while (node.left != null) {
                node = node.left;
            }
            return node;
        } else {
            // 如果没有右子树，本节点所在的子树是某个节点的左子树，或者本节点是树的最右节点
            // 这两种情况下，都向上寻找，找到第一个node是parent左子树的情况，如果没有，则为null
            while (parent != null && parent.left != node) {
                node = parent;
                parent = parent.parent;
            }
            return parent;
        }
    }

    /**
     * 寻找两个node最低的公共祖先
     * 默认给定的两个node都在同一颗数中
     * 
     * @param root
     * @param node_1
     * @param node_2
     * @return 递归时：返回null代表无信息，返回某节点，代表此节点可能是此子树中两个节点的公共祖先
     */
    public static TreeNode findLeastCommonAncestor(TreeNode root, TreeNode node_1, TreeNode node_2) {

        if (root == null || root == node_1 || root == node_2) {
            // 如果走到null，返回null。如果找到node_1或者node_2，返回此节点
            return root;
        }

        // 从左右两个子树分别继续寻找
        TreeNode leftRes = findLeastCommonAncestor(root.left, node_1, node_2);
        TreeNode rightRes = findLeastCommonAncestor(root.right, node_1, node_2);

        if (leftRes != null && rightRes != null) {
            // 如果左右两个子树分别都有信息，代表本节点就是两个node的最低公共祖先
            return root;
        }

        // 如果以上情况不成立，则左右两个子树有一个有信息，返回这个信息
        return leftRes != null ? leftRes : rightRes;
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
        TreeNode node_9 = new TreeNode(9);
        TreeNode node_10 = new TreeNode(10);
        TreeNode node_11 = new TreeNode(11);
        TreeNode node_12 = new TreeNode(12);
        TreeNode node_13 = new TreeNode(13);
        TreeNode node_14 = new TreeNode(14);
        TreeNode node_15 = new TreeNode(15);
        TreeNode node_16 = new TreeNode(16);
        TreeNode node_17 = new TreeNode(17);

        root.left = node_1;
        root.right = node_2;
        node_1.left = node_3;
        node_1.right = node_4;
        node_2.left = node_5;
        node_2.right = node_6;
        node_3.left = node_7;
        node_3.right = node_8;
        node_4.left = node_9;
        node_4.right = node_10;
        node_5.left = node_11;
        node_5.right = node_12;
        node_6.left = node_13;
        node_6.right = node_14;
        node_10.right = node_15;
        node_14.left = node_16;
        node_15.left = node_17;

        node_1.parent = root;
        node_2.parent = root;
        node_3.parent = node_1;
        node_4.parent = node_1;
        node_5.parent = node_2;
        node_6.parent = node_2;
        node_7.parent = node_3;
        node_8.parent = node_3;
        node_9.parent = node_4;
        node_10.parent = node_4;
        node_11.parent = node_5;
        node_12.parent = node_5;
        node_13.parent = node_6;
        node_14.parent = node_6;
        node_15.parent = node_10;
        node_16.parent = node_14;
        node_17.parent = node_15;

        System.out.println("least common panrent is: " + findLeastCommonAncestor(root, node_14, node_11).value);

        TreeNode successor = findSuccessor(node_14);
        if (successor == null) {
            System.out.println(node_14.value + " has no successor");
        } else {
            System.out.println("successor of " + node_14.value + " is : " + successor.value);
        }
    }
}
