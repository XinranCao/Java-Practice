package LinkedList;

public class BinaryTreeProblem {

    public static class ReturnDataFull {
        public int count;
        public int height;

        public ReturnDataFull(int n, int h) {
            count = n;
            height = h;
        }
    }

    public static boolean isFull(TreeNode root) {
        if (root == null) {
            return true;
        }
        ReturnDataFull res = checkFull(root);

        return 1 << res.height == res.count + 1;
    }

    public static ReturnDataFull checkFull(TreeNode root) {
        if (root == null) {
            return new ReturnDataFull(0, 0);
        }

        ReturnDataFull leftRes = checkFull(root.left);
        ReturnDataFull rightRes = checkFull(root.right);

        int count = leftRes.count + rightRes.count + 1;
        int height = Math.max(leftRes.height, rightRes.height) + 1;

        return new ReturnDataFull(count, height);
    }

    public static class ReturnDataBTS {
        public boolean isBTS;
        public int min;
        public int max;

        public ReturnDataBTS(boolean is, int mn, int mx) {
            isBTS = is;
            min = mn;
            max = mx;
        }
    }

    public static boolean isBTS(TreeNode root) {
        if (root == null) {
            return true;
        }
        return checkBTS(root).isBTS;
    }

    public static ReturnDataBTS checkBTS(TreeNode root) {
        if (root == null) {
            return null;
        }
        int min = root.value;
        int max = root.value;

        ReturnDataBTS leftRes = checkBTS(root.left);
        ReturnDataBTS rightRes = checkBTS(root.right);

        if (leftRes != null && (!leftRes.isBTS || leftRes.max >= min)) {
            return new ReturnDataBTS(false, min, max);
        }
        if (rightRes != null && (!rightRes.isBTS || rightRes.min <= max)) {
            return new ReturnDataBTS(false, min, max);
        }

        if (leftRes != null) {
            min = leftRes.min;
        }
        if (rightRes != null) {
            max = rightRes.max;
        }

        return new ReturnDataBTS(true, min, max);
    }

    public static boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        return checkBalance(root) < 0 ? false : true;
    }

    public static int checkBalance(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftRes = checkBalance(root.left);
        int rightRes = checkBalance(root.right);

        if (leftRes < 0 || rightRes < 0) {
            return -1;
        }

        if (Math.abs(leftRes - rightRes) < 2) {
            return Math.max(leftRes, rightRes) + 1;
        }

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
        System.out.println(isBTS(root));
        System.out.println(isFull(root));
    }
}
