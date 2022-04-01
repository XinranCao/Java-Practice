package LinkedList;

public class BinaryTreeProblem {

    /**
     * 包含两个信息：节点总个数，最大深度
     * 在判断是否为满二叉树的递归函数中使用
     */
    public static class ReturnDataFull {
        public int count;
        public int height;

        public ReturnDataFull(int n, int h) {
            count = n;
            height = h;
        }
    }

    /**
     * 判断以二叉树是否为满二叉树
     * 满二叉树： 树的节点个数n，最大深度为k，满足 2 ^ k - 1 = n
     * 
     * @param root
     * @return
     */
    public static boolean isFull(TreeNode root) {
        if (root == null) {
            return true;
        }
        ReturnDataFull res = checkFull(root);

        // 1 << h 等于 2 ^ h
        return 1 << res.height == res.count + 1;
    }

    /**
     * 判断以root为根的子树是否为满二叉树
     * 
     * @param root
     * @return ReturnDataFull (count: 子树节点数, height：子树最大深度)
     */
    public static ReturnDataFull checkFull(TreeNode root) {
        if (root == null) {
            return new ReturnDataFull(0, 0); // 空节点的最大深度为0，节点个数也为0
        }

        // 分别得到左右两个子树的信息
        ReturnDataFull leftRes = checkFull(root.left);
        ReturnDataFull rightRes = checkFull(root.right);

        int count = leftRes.count + rightRes.count + 1; // root为根的子树中包含的节点个数为左右两个子树节点总个数之和再+1
        int height = Math.max(leftRes.height, rightRes.height) + 1; // root为根的子树最大深度为左右两个子树最大深度再+1

        return new ReturnDataFull(count, height);
    }

    /**
     * 包含三个信息：是否为搜索二叉树、子树最大值、子树最小值
     * 在判断是否为搜索二叉树的递归函数中使用
     */
    public static class ReturnDataBST {
        public boolean isBTS;
        public int min;
        public int max;

        public ReturnDataBST(boolean is, int mn, int mx) {
            isBTS = is;
            min = mn;
            max = mx;
        }
    }

    /**
     * 判断以二叉树是否为搜索二叉树 binary search tree
     * BST: 对于任意一个节点，左子树所有节点均小于这一节点，右子树所有及诶单均大于这一节点
     * 
     * @param root
     * @return
     */
    public static boolean isBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        // 递归方法判断
        return checkBST(root).isBTS;
    }

    /**
     * 判断以root为根的子树是否为搜索二叉树
     * 
     * @param root
     * @return ReturnDataBTS(isBTS：是否为搜索二叉树，min：子树的最小值，max：子树的最大值)
     */
    public static ReturnDataBST checkBST(TreeNode root) {
        // 如果节点为空，返回null
        if (root == null) {
            return null;
        }
        int min = root.value; // 以root为根的子树的最小值
        int max = root.value; // 以root为根的子树的最大值

        // 分别得到左右两个子树的具体信息
        ReturnDataBST leftRes = checkBST(root.left);
        ReturnDataBST rightRes = checkBST(root.right);

        // 如果左子树有信息，并且：左子树不是索索二叉树 或者 左子树的最大值大于root值
        // 则以root为根的子树不是平衡二叉树，判断为false，返回。
        if (leftRes != null && (!leftRes.isBTS || leftRes.max >= min)) {
            return new ReturnDataBST(false, min, max);
        }
        // 如果右子树有信息，并且：右子树不是索索二叉树 或者 右子树的最大值大于root值
        // 则以root为根的子树不是平衡二叉树，判断为false，返回。
        if (rightRes != null && (!rightRes.isBTS || rightRes.min <= max)) {
            return new ReturnDataBST(false, min, max);
        }

        // 上面两个判断结束后，左右两个子树都一定为平衡二叉树
        // 接下来更新以root为根子树的最大值和最小值
        // 如果有左子树，那么最小值为左子树的最小值（左子树的最小值不可能等于或大于root的值，上面的判断已排除过）
        if (leftRes != null) {
            min = leftRes.min;
        }
        // 如果有右子树，那么最大值为右子树的最大值（右子树的最大值不可能等于或小于root的值，上面的判断已排除过）
        if (rightRes != null) {
            max = rightRes.max;
        }
        // 返回判断为true，以及以root为根的子树的最大值和最小值
        return new ReturnDataBST(true, min, max);
    }

    /**
     * 判断二叉树是否是平衡二叉树
     * 
     * @param root
     * @return
     */
    public static boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        // 具体判断的递归函数
        return checkBalance(root) < 0 ? false : true;
    }

    /**
     * 通过递归方式判断以root为根的二叉树是否是平衡二叉树
     * 平衡二叉树：任意一个节点，左右两个子树的最大深度，差值小于2
     * 
     * @param root
     * @return -1 代表不是平衡二叉树，<= 0 代表子树的最大深度
     */
    public static int checkBalance(TreeNode root) {
        if (root == null) { // 如果为空节点，返回深度为0
            return 0;
        }

        // 分别找出左右子树的最大深度
        int leftRes = checkBalance(root.left);
        int rightRes = checkBalance(root.right);

        // 如果任意一个子树不是平衡二叉树，那么当前节点的子树也不是
        if (leftRes < 0 || rightRes < 0) {
            return -1;
        }

        // 如果左右两个子树的最大深度，差值小于2，返回最大深度+1
        if (Math.abs(leftRes - rightRes) < 2) {
            return Math.max(leftRes, rightRes) + 1;
        }

        // 如果两个子树最大深度，差值大于等于2，则当前节点的额子树不是平衡二叉树
        return -1;

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

        System.out.println(isBalanced(root));
        System.out.println(isBST(root));
        System.out.println(isFull(root));
    }
}
