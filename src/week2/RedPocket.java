
//*------------------------------------------------------
//* 题目要求：
//* 设计一个简单的微信群“拼手气”红包程序。
//* 要求可以根据输入的总金额、红包数量，输出每个红包的随机金额，
//* 其中，金额以“元”为单位，保留两位小数点。
//* ---------------------------------------------------*/

package week2;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class RedPocket {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean nextRound = true;

        while (nextRound) {
            double money = getValidMoney(input);
            int amount = getValidAmount(input);

            double[] parts = splitAmount(money, amount);

            System.out.println("红包金额依次为:");
            for (int i = 0; i < parts.length; i++) {
                System.out.printf("%.2f元 ", parts[i]);
                if ((i + 1) % 5 == 0) {
                    System.out.println();
                }
            }
            if (parts.length % 5 != 0) {
                System.out.println(); // 确保每输出 5个结果换一次行
            }

            // 是否继续发红包
            boolean retype = true;
            while (retype) {
                System.out.print("是否要继续发红包？ 继续(输入 Y/y），退出(输入 N/n): ");
                String answer = input.next();
                if (answer.equalsIgnoreCase("y")) {
                    retype = false;
                } else if (answer.equalsIgnoreCase("n")) {
                    nextRound = false;
                    retype = false;
                } else {
                    System.out.println("输入不合法，请输入 Y/y 以继续，输入 N/n 以退出:");
                }
            }
        }

        input.close();
    }

    // 获取合法金额值
    public static double getValidMoney(Scanner input) {
        System.out.print("输入红包金额(大于0元): ");
        double money = 0;

        while (true) {
            try {
                money = input.nextDouble();
                if (money > 0) {
                    break;
                } else {
                    System.out.println("红包金额必须大于0元，请重新输入：");
                }
            } catch (InputMismatchException e) {
                System.out.println("非法的红包金额，请重新输入：");
                input.next(); // 清除无用输入
            }
        }
        return money;
    }

    // 获取合法红包数目
    public static int getValidAmount(Scanner input) {
        System.out.print("输入红包个数: ");
        int amount = 0;

        while (true) {
            try {
                amount = input.nextInt();
                if (amount > 0) {
                    break;
                } else {
                    System.out.println("红包个数至少为1个，请重新输入：");
                }
            } catch (InputMismatchException e) {
                System.out.println("非法的红包个数，请重新输入：");
                input.next(); // 清除无用输入
            }
        }
        return amount;
    }

    // 划分红包金额
    public static double[] splitAmount(double totalAmount, int numParts) {
        Random random = new Random();
        double[] parts = new double[numParts];
        double sum = 0;

        // 生成随机数
        for (int i = 0; i < numParts; i++) {
            parts[i] = (random.nextDouble() * totalAmount) / numParts;
            sum += parts[i];
        }

        // 确保总金额正确
        double adjustment = totalAmount - sum;
        for (int i = 0; i < adjustment; i++) {
            parts[random.nextInt(numParts)] += 1.0 / numParts;
        }

        return parts;
    }
}