package codingTest.map;

import java.util.*;

/**
 * 문제 : 완주하지 못한 선수
 * 플랫폼 : 프로그래머스
 * 링크 : https://school.programmers.co.kr/learn/courses/30/lessons/42576
 */
public class MapProblem01 {

    /**
     * 문제분석
     * 1. 처음에 HashSet을 사용하여 해결하려 했으나 동명이인이 존재할 수 있음 을 감안.
     * 2. 1에 따라서 HashMap을 사용
     * 3. 참여자 + 도착시 - 해서 0이 아닌값 ( 도착하지 않은 사람 )의 key를 반환한다.
     */
    class Solution {
        public String solution(String[] participant, String[] completion) {
            Map<String, Integer> map = new HashMap<>();

            for (String p : participant) {
                if (map.get(p) != null) {
                    map.replace(p, map.get(p) + 1);
                } else {
                    map.put(p, 1);
                }
            }

            for (String c : completion) {
                int count = map.get(c);
                if (count >= 1) {
                    map.replace(c, count - 1);
                }
            }

            for (String key : map.keySet()) {
                int count = map.get(key);
                if (count != 0) {
                    return key;
                }
            }

            return "";
        }
    }
}
