package Tree;

public class TrieTree {

    public static class TrieNode {
        public int pass; // 记录有几个字符串通过个节点
        public int end; // 记录有几个字符串以这个字母结尾
        public TrieNode[] nexts; // 记录从这一节点往下的所有通路

        public TrieNode() {
            pass = 0;
            end = 0;
            nexts = new TrieNode[26]; // 26个字母（如果有更多字符，可以用HashMap
        }
    }

    /**
     * 前缀树（字典树）
     * 
     */
    public static class Trie {

        // 记录前缀树的根节点
        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        /**
         * 将一个字符串插入前缀树中
         * 
         * @param word
         */
        public void insert(String word) {
            if (word == null) {
                return;
            }

            TrieNode node = root; // 从根节点开始处理
            int index = 0; // 用来找出字符所在的index
            char[] letters = word.toCharArray(); // 将字符串转换为字符数组

            for (char letter : letters) {
                index = letter - 'a'; // a-z 0-26
                if (node.nexts[index] == null) { // 如果没有这条路，从这条路加入一个新节点
                    node.nexts[index] = new TrieNode();
                }
                node = node.nexts[index]; // 从这条路继续向下走
                node.pass++; // 更新pass值，从这条路通过的字符串数量+1
            }
            node.end++; // 字符串全部加入后，将最后一个字母的end值+1，代表以这个字母为结尾的字符串数量+1
        }

        /**
         * 将一个字符串从前缀树中删除
         * 
         * @param word
         */
        public void delete(String word) {
            if (search(word) == 0) { // 先检查前缀树中是否有这个字符串，如果没有则直接返回
                return;
            }

            TrieNode node = root; // 从根节点开始处理
            root.pass--; // 字符串必经过root节点，将根节点pass值-1
            int index = 0; // 用来找出字符所在的index
            char[] letters = word.toCharArray(); // 将字符串转换为字符数组

            for (char letter : letters) {
                index = letter - 'a'; // a-z 0-26
                // 先将这一节点的pass值-1，如果结果为0，则代表没有通路走向这个节点，所以直接设置为null
                if (--node.nexts[index].pass == 0) {
                    node.nexts[index] = null;
                    return;
                }
                // 继续向下删除字符通路与节点
                node = node.nexts[index];
            }
            // 走到最后一个字符通路连接的节点，将end值-1，代表以这个字符为结尾的字符串数量-1
            node.end--;
        }

        /**
         * 查找在前缀树中，某一字符串被加入了几次
         * 
         * @param word
         * @return
         */
        public int search(String word) {
            if (word == null) {
                return 0;
            }

            TrieNode node = root;
            int index = 0;
            char[] letters = word.toCharArray();

            for (char letter : letters) {
                index = letter - 'a';
                // 如果字符串里的某一字符没有被加入前缀树过，说明这个字符串从未被加入过，直接返回0
                if (node.nexts[index] == null) {
                    return 0;
                }
                // 继续向下查找字符
                node = node.nexts[index];
            }
            // 当找到字符串中最后一个字母，将以这个字母结尾的字符串数量返回
            return node.end;
        }

        /**
         * 查抄在前缀树中，有多少以某一字符串为前缀的字符串
         * 
         * @param word
         * @return
         */
        public int searchPrefix(String word) {
            if (word == null) {
                return 0;
            }

            TrieNode node = root;
            int index = 0;
            char[] letters = word.toCharArray();

            for (char letter : letters) {
                index = letter - 'a';
                // 如果字符串里的某一字符没有被加入前缀树过，说明以这个字符串为前缀的字符串从未被加入过，直接返回0
                if (node.nexts[index] == null) {
                    return 0;
                }
                // 继续向下查找字符
                node = node.nexts[index];
            }
            // 当找到字符串中最后一个字母，将通过这个字符的字符串数量返回
            return node.pass;
        }
    }

    public static void main(String[] args) {
        Trie tree = new Trie();
        tree.insert("waad");
        tree.insert("wad");
        tree.insert("waa");
        tree.insert("waa");
        tree.insert("aad");
        tree.insert("ad");

        System.out.print("search:[ ");
        System.out.print(tree.search("wa") + " ");
        System.out.print(tree.search("waa") + " ");
        System.out.print(tree.search("waad") + " ");
        System.out.print(tree.search("a") + " ");
        System.out.print(tree.search("w") + " ");
        System.out.println(" ]");

        System.out.print("prefix:[ ");
        System.out.print(tree.searchPrefix("wa") + " ");
        System.out.print(tree.searchPrefix("waa") + " ");
        System.out.print(tree.searchPrefix("waad") + " ");
        System.out.print(tree.searchPrefix("a") + " ");
        System.out.print(tree.searchPrefix("w") + " ");
        System.out.println(" ]");

        tree.delete("waa");
        tree.delete("waa");
        // tree.delete("wad");
        tree.delete("w");
        tree.delete("waad");
        System.out.println("-----after deleting-----");

        System.out.print("search:[ ");
        System.out.print(tree.search("wa") + " ");
        System.out.print(tree.search("waa") + " ");
        System.out.print(tree.search("waad") + " ");
        System.out.print(tree.search("a") + " ");
        System.out.print(tree.search("w") + " ");
        System.out.println(" ]");

        System.out.print("prefix:[ ");
        System.out.print(tree.searchPrefix("wa") + " ");
        System.out.print(tree.searchPrefix("waa") + " ");
        System.out.print(tree.searchPrefix("waad") + " ");
        System.out.print(tree.searchPrefix("a") + " ");
        System.out.print(tree.searchPrefix("w") + " ");
        System.out.println(" ]");
    }
}
