package codingTest.dp;

import java.util.*;

/**
 * 문제 : N으로 표현
 * 플랫폼 : 프로그래머스
 * 링크 : * https://school.programmers.co.kr/learn/courses/30/lessons/42895
 */
public class DpProblem01 {

    class Solution {

        /**
         * 문제분석
         * 1. dp문제이기 때문에, 발생할 수 있는 경우의 수에 대해서 고려
         * 2. 사용할 수 있는 가짓수의 제한이 있었기 때문에, 개수 - 가짓수를 고려해야할 것으로 판단.
         * 3. 2의 이유로 Set/List 사용고려 하였음.
         * 4. 여러 값을 더해서 다양한 값을 만드는 과정이기에 중복을 제거해야함. 따라서 HashSet를 사용할 수 있는 Set 사용
         * 5. 발생할 수 있는 경우의 수 맨앞에 더하기, 그외 사칙연산
         * 6. 맨앞에 더하는 값은 별도의 메서드로 처리하고, 나머지 값들은 전체 조회로 처리한다.
         * 7. 최악의 경우의수가 O(32000 * 32000) 이기에 조회에 별도의 부담은 가지지 않는다.
         * 8. 점화식은 dp[i] = dp[i-j] + dp[j]
         */

        public int solution(int N, int number) {
            List<Set<Integer>> dp = new ArrayList<>();

            for (int i = 0; i <= 8; i++) {
                dp.add(new HashSet<>());
            }

            for (int count = 1; count <= 8; count++) {
                dp.get(count).add(addFront(count, N)); // N번 붙인 수

                // 현재 가짓수에서 발생시킬 수 있는 경우의 수를 찾아서 계산
                for (int b = 1; b < count; b++) { // 이전값들로 만들 수 있는 경우의 수를 넣어준다.
                    Set<Integer> gap1 = dp.get(b);
                    Set<Integer> gap2 = dp.get(count - b);

                    for (int value1 : gap1) {
                        for (int value2 : gap2) {
                            dp.get(count).add(value1 + value2);
                            dp.get(count).add(value1 - value2);
                            dp.get(count).add(value1 * value2);
                            if (value2 != 0) {
                                dp.get(count).add(value1 / value2);
                            }
                        }
                    }
                }

                if (dp.get(count).contains(number)) return count;
            }

            return -1;
        }

        // 5, 55, 555... 등 앞에 값 붙여서 반환
        public int addFront(int count, int N) {
            int number = 0;
            for (int i = 0; i < count; i++) {
                number = number * 10 + N;
            }
            return number;
        }
    }
}
