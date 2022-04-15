package Hash;

import java.util.HashMap;

public class HashProblem {

    /**
     * 随机给出一个元素
     */
    public static class Pool<K> {

        private HashMap<K, Integer> keyIndexMap; // 用来记录元素和其对应的index
        private HashMap<Integer, K> indexKeyMap; // 使得可以用index找到对应元素
        private int size; // 用来记录元素个数

        public Pool() {
            this.keyIndexMap = new HashMap<K, Integer>();
            this.indexKeyMap = new HashMap<Integer, K>();
            this.size = 0;
        }

        /**
         * 加入一个新元素
         * 
         * @param key
         */
        public void insert(K key) {
            // 如果已有这个元素，则不操作
            if (!this.keyIndexMap.containsKey(key)) {
                // 更改信息，存放新元素
                this.keyIndexMap.put(key, this.size);
                this.indexKeyMap.put(this.size++, key);
            }
        }

        /**
         * 删除一个已有元素
         * 
         * @param key
         */
        public void delete(K key) {
            // 如果没有这个元素，则不操作
            if (this.keyIndexMap.containsKey(key)) {
                // 找到这个元素对应的位置
                int deleteIndex = this.keyIndexMap.get(key);
                int lastIndex = --this.size; // 找到最后一位index
                K lastKey = this.indexKeyMap.get(lastIndex); // 找到最后一个元素
                this.keyIndexMap.put(lastKey, deleteIndex); // 将最后一个元素放到要删除的元素位置上
                this.indexKeyMap.put(deleteIndex, lastKey); // 将最后一个元素放到要删除的元素位置上
                this.keyIndexMap.remove(key); // 删除元素
                this.indexKeyMap.remove(lastIndex); // 删除元素
            }
        }

        /**
         * 随机返回一个元素
         * 
         * @return
         */
        public K getRandom() {
            // 如果为空，返回null
            if (this.size == 0) {
                return null;
            }
            // 随机一个位置，返回这个位置上的元素
            int randomIndex = (int) (Math.random() * this.size);
            return this.indexKeyMap.get(randomIndex);
        }
    }

    public static void main(String[] args) {
        Pool<String> pool = new Pool<String>();
        pool.insert("lalala");
        pool.insert("mamama");
        pool.insert("hahaha");
        pool.insert("wawawa");
        pool.insert("bababa");
        pool.insert("kakaka");
        pool.insert("tatata");
        System.out.println("random: " + pool.getRandom());
    }

}
