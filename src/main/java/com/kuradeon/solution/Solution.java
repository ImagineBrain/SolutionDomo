package com.kuradeon.solution;

import java.util.*;

/**
 * @author xiezixiao
 * @date 2019-05-24 15:25
 */
public class Solution {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        Map<Character, Integer> map = new HashMap<>();
        int max = 0; int start = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                start = Math.max(start, map.get(c) + 1);
            }
            max = Math.max(max, i - start + 1);
            map.put(c, i);
        }
        return max;
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n1 = nums1.length;
        int n2 = nums2.length;
        if (n1 > n2)
            return findMedianSortedArrays(nums2, nums1);
        int k = (n1 + n2 + 1) / 2;
        int left = 0;
        int right = n1;
        while (left < right) {
            int m1 = left + (right - left) / 2;
            int m2 = k - m1;
            if (nums1[m1] < nums2[m2 - 1])
                left = m1 + 1;
            else
                right = m1;
        }
        int m1 = left;
        int m2 = k - left;
        int c1 = Math.max(m1 <= 0 ? Integer.MIN_VALUE : nums1[m1 - 1],
                m2 <= 0 ? Integer.MIN_VALUE : nums2[m2 - 1]);
        if ((n1 + n2) % 2 == 1)
            return c1;
        int c2 = Math.min(m1 >= n1 ? Integer.MAX_VALUE : nums1[m1],
                m2 >= n2 ? Integer.MAX_VALUE : nums2[m2]);
        return (c1 + c2) * 0.5;

    }

    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length <= 0) {
            return "";
        }
        if (strs.length == 1) {
            return strs[0];
        }
        Character c = null;
        StringBuilder sb = new StringBuilder();
        boolean done = false;
        for (int index = 0; index < strs[0].length(); index++) {
            c = strs[0].charAt(index);
            for (int i = 1; i < strs.length; i++) {
                if (strs[i] == null || strs[i].isEmpty()) {
                    return "";
                }
                if (index >= strs[i].length()) {
                    done = true;
                    break;
                }
                if (!c.equals(strs[i].charAt(index))) {
                    done = true;
                    break;
                }
                if (i == strs.length - 1) {
                    sb.append(c);
                }
            }
            if (done) {
                break;
            }
        }
        return sb.toString();
    }

    public List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length <= 0) {
            return new ArrayList<>();
        }
        List<List<Integer>> results = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) {
                break;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1, right = nums.length - 1;
           do {
                int sum = nums[left] + nums[i] + nums[right];
                if (sum == 0) {
                    List<Integer> result = new ArrayList<>(3);
                    result.add(nums[left]); result.add(nums[i]); result.add(nums[right]);
                    results.add(result);
                    while (left < right) {
                        if (nums[left] != nums[++left]) {
                            break;
                        }
                    }
                    while (left < right) {
                        if (nums[right] != nums[--right]) {
                            break;
                        }
                    }
                } else if (sum > 0) {
                    right--;
                } else {
                    left++;
                }
            } while (left < right);
        }
        return results;
    }

    public int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length <= 0) {
            return 0;
        }
        int sum = 0;
        if (nums.length <= 3) {
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
            }
            return sum;
        }
        Arrays.sort(nums);
        int minAbs = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1, right = nums.length - 1;
            do {
                int temp = nums[left] + nums[right] + nums[i];
                if (temp == target) {
                    return target;
                }
                int abs = Math.abs(temp - target);
                if (abs < minAbs) {
                    sum = temp;
                    minAbs = abs;
                }
                if (temp > target) {
                    right--;
                } else {
                    left++;
                }
            } while (left < right);
        }
        return sum;
    }

    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.isEmpty()) {
            return new ArrayList<>();
        }
        final Map<Character, char[]> map = new HashMap<>(8);
        map.put('2', new char[]{'a', 'b', 'c'});
        map.put('3', new char[]{'d', 'e', 'f'});
        map.put('4', new char[]{'g', 'h', 'i'});
        map.put('5', new char[]{'j', 'k', 'l'});
        map.put('6', new char[]{'m', 'o', 'n'});
        map.put('7', new char[]{'p', 'q', 'r', 's'});
        map.put('8', new char[]{'t', 'u', 'v'});
        map.put('9', new char[]{'w', 'x', 'y', 'z'});
        List<String> result = new LinkedList<>();
        doAdd(result, digits, map, 0, "");
        return result;
    }

    private void doAdd(List<String> result, String digits, Map<Character, char[]> map, int index, String prefix) {
        for (char i : map.get(digits.charAt(index))) {
            if (index >= digits.length() - 1) {
                result.add(prefix + i);
            } else {
                doAdd(result, digits, map, index + 1, prefix + i);
            }
        }
    }

    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length <= 0) {
            return 0;
        }
        int sum = 0, result = Integer.MIN_VALUE;
        for (int num : nums) {
            if (sum > 0) {
                sum += num;
            } else {
                sum = num;
            }
            if (sum > result) {
                result = sum;
            }
        }
        return result;
    }

    public int uniquePaths(int m, int n) {
        if (m <= 1 || n <= 1) {
            return 1;
        }
        int[] p = new int[n];
        Arrays.fill(p, 1);
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                p[j] += p[j - 1];
            }
        }
        return p[n - 1];
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> results = new ArrayList<>();
        if (nums == null || nums.length < 4) {
            return results;
        }
        int sum, sum1, left, right;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 3; i++) {
            if (i > 0 && nums[i - 1] == nums[i]) {
                continue;
            }
            for (int j = i + 1; j < nums.length - 2; j++) {
                if (j > i + 1 && nums[j - 1] == nums[j]) {
                    continue;
                }
                sum = nums[i] + nums[j];
                left = j + 1;
                right = nums.length - 1;
                while (left < right) {
                    sum1 = sum + nums[left] + nums[right];
                    if (sum1 == target) {
                        List<Integer> result = new ArrayList<>(4);
                        result.add(nums[i]);
                        result.add(nums[j]);
                        result.add(nums[left]);
                        result.add(nums[right]);
                        results.add(result);
                        do {
                            left++;
                        } while (nums[left] == nums[left - 1] && left < right);
                        do {
                            right--;
                        } while (nums[right] == nums[right + 1] && left < right);
                    } else if (sum1 > target) {
                        right--;
                    } else {
                        left++;
                    }
                }
            }
        }
        return results;
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (wordList == null || wordList.isEmpty() || !wordList.contains(endWord)
                || beginWord.length() != endWord.length()) {
            return 0;
        }
        List<String> list = new LinkedList<>();
        list.add(beginWord);
        int step = 1;
        while (list.size() > 0) {
            step++;
            List<String> tempList = new LinkedList<>();
            List<String> tempList1 = new ArrayList<>(wordList);
            for (String start : list) {
                for (String word : wordList) {
                    int sameCharCount = 0;
                    for (int i = 0; i < word.length(); i++) {
                        if (start.charAt(i) == word.charAt(i)) {
                            sameCharCount++;
                        }
                    }
                    if (sameCharCount == endWord.length() - 1) {
                        if (word.equals(endWord)) {
                            return step;
                        }
                        tempList.add(word);
                        tempList1.remove(word);
                    }
                }
            }
            list = tempList;
            wordList = tempList1;
        }
        return 0;
    }

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> results = new LinkedList<>();
        if (k <= 0 || n <= 0) {
            return results;
        }
        for (int i = 1; i <= n; i++) {
            List<Integer> result = new LinkedList<>();
            result.add(i);
            doCombine(n, k - 1, i + 1, result, results);
        }
        return results;
    }

    private void doCombine(int n, int k, int start, List<Integer> list, List<List<Integer>> results) {
        if (k <= 0) {
            results.add(list);
            return;
        }
        for (int i = start; i <= n; i++) {
            List<Integer> result = new LinkedList<>(list);
            result.add(i);
            doCombine(n, k - 1, i + 1, result, results);
        }
    }

    public List<List<Integer>> verticalTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        Points points = new Points(0, 0, root.val);
        List<Points> pointsList = new LinkedList<>();
        traverseTree(root, points, pointsList);
        pointsList.sort(new Comparator<Points>() {
            @Override
            public int compare(Points o1, Points o2) {
                if (o1.x < o2.x) {
                    return -1;
                }
                if (o1.x > o2.x) {
                    return 1;
                }
                if (o1.y > o2.y) {
                    return -1;
                }
                if (o1.y < o2.y) {
                    return 1;
                }
                return 0;
            }
        });
        Map<Integer, List<Integer>> resultMap = new HashMap<>();
        for (Points curPoints : pointsList) {
            if (resultMap.containsKey(curPoints.x)) {
                List<Integer> result = resultMap.get(curPoints.x);
                result.add(curPoints.val);
            } else {
                List<Integer> result = new ArrayList<>();
                result.add(curPoints.val);
                resultMap.put(curPoints.x, result);
            }
        }
        return new ArrayList<>(resultMap.values());
    }

    class Points {
        int x;
        int y;
        int val;
        public Points(int x, int y, int val) {
            this.x = x;
            this.y = y;
            this.val = val;
        }
    }

    private void traverseTree(TreeNode node, Points points, List<Points> pointList) {
        if (node == null) {
            return;
        }
        if (node.left != null) {
            Points leftPoints = new Points(points.x - 1, points.y - 1, node.left.val);
            traverseTree(node.left, leftPoints, pointList);
        }
        pointList.add(points);
        if (node.right != null) {
            Points rightPoints = new Points(points.x + 1, points.y - 1, node.right.val);
            traverseTree(node.right, rightPoints, pointList);
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.combine(4, 2));
    }
}
