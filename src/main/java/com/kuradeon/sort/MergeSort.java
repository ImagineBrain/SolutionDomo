package com.kuradeon.sort;

import com.alibaba.fastjson.JSONObject;

/**
 * @author xiezixiao
 * @date 2019-06-11 10:24
 */
public class MergeSort {

    public static void sort(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }
        int[] targets = new int[nums.length];
        sort(nums, targets, 0, nums.length - 1);
    }

    private static void sort(int[] nums, int[] targets, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            sort(nums, targets, left, mid);
            sort(nums, targets, mid + 1, right);
            merge(nums, targets, left, mid, right);
        }
    }

    private static void merge(int[] nums, int[] targets, int left, int mid, int right) {
        int i = left, j = mid + 1, index = left;
        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                targets[index++] = nums[i++];
            } else {
                targets[index++] = nums[j++];
            }
        }
        while (i <= mid) {
            targets[index++] = nums[i++];
        }
        while (j <= right) {
            targets[index++] = nums[j++];
        }
        while (left <= right) {
            nums[left] = targets[left];
            left++;
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[] {2,1,3,2,0,7,8,4,5};
        System.out.println(JSONObject.toJSONString(nums));
        MergeSort.sort(nums);
        System.out.println(JSONObject.toJSONString(nums));
    }
}
