package com.szs.algo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

/**
 * 基尼系数
 * 数组差值总和/（2*人数*财富总和）
 * ===================================
 * 实验
 * 一开始有100个人，每个人都有100元
 * 每一轮要做如下事情：
 * 每个人拿出1元给别人，给谁完全随机
 * 如何这个人这一轮没钱了，可以不给，可以接收
 * 发生很多轮之后，观察财富分布情况
 */
public class Basic01 {
    public static void main(String[] args) {
        experiment(100, 1000);
    }

    /**
     * @param people 实验人数
     * @param round  轮数
     */
    public static void experiment(int people, int round) {
        int[] money = new int[people];
        Arrays.fill(money, 100);
        boolean[] hasMoney = new boolean[people];
        //初始化数据，每人100元
        for (int i = 0; i < round; i++) {
            for (int r = 0; r < money.length; r++) {
                if (money[r] > 0) {
                    hasMoney[r] = true;
                }
                //每轮开始时要统计当前所有人的财富情况，防止并发时某个人没钱，而且别人给他钱后，他还需要分出去钱的情况
            }
            for (int j = 0; j < money.length; j++) {
                if (!hasMoney[j]) {
                    continue;
                }
                money[j]--;
                //接收人编号
                int receiveIndex;
                do {
                    receiveIndex = (int) (Math.random() * people);
                } while (receiveIndex == j);
                money[receiveIndex]++;
            }
        }
        Arrays.sort(money);
        for (int i = 0; i < money.length; i++) {
            System.out.print(money[i] + " ");
            if (i % 10 == 9) {
                System.out.println();
            }
        }
        //美观输出
        gini(money);
    }


    public static void gini(int[] array) {
        int sum = 0;
        int all = 0;
        for (int i = 0; i < array.length; i++) {
            all += array[i];
            for (int j = 0; j < array.length; j++) {
                sum += Math.abs(array[i] - array[j]);
            }
        }
        System.out.println("财富总和:" + all);
        BigDecimal sum1 = new BigDecimal(sum);
        BigDecimal sum2 = new BigDecimal(2 * array.length * all);
        System.out.println("gini系数:" + sum1.divide(sum2, 4, RoundingMode.HALF_UP));
    }

}
