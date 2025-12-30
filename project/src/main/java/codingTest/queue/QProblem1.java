package codingTest.queue;

import java.util.*;

/**
 문제 : 기능개발 (프로그래머스)
 링크 : * https://school.programmers.co.kr/learn/courses/30/lessons/42586
*/
public class QProblem1 {

    class Solution {
        /*
         뒤에 있는 기능이 먼저 개발될 수 있지만, 앞에있는 기능이 배포될 때 함께 배포된다.
         progresses - 작업 진도
         speeds - 개발 속도
         작업의 개수는 100개 이하이다.
        */
        public int[] solution(int[] progresses, int[] speeds) {
            List<Integer> answerList = new ArrayList<>();

            Queue<Integer> workQ = new LinkedList<>();
            Queue<Integer> speedQ = new LinkedList<>();

            for(int idx=0; idx<progresses.length; idx++){
                int restWork = 100 - progresses[idx];
                workQ.add(restWork);
            }

            for(int idx=0; idx<speeds.length; idx++){
                speedQ.add(speeds[idx]);
            }

            int time = 0;
            while(!workQ.isEmpty()) {
                time++;
                int count = 0;

                while(
                        !workQ.isEmpty()
                                && (workQ.peek() <= speedQ.peek() * time)
                ) {
                    workQ.poll();
                    speedQ.poll();
                    count++;
                }

                if(count > 0) {
                    answerList.add(count);
                }
            }

            int[] answer = answerList.stream()
                    .mapToInt(Integer::intValue)
                    .toArray();

            return answer;
        }
    }
}
