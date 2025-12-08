package study.project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 정규식을 사용할 때 보통 Pattern을 활용한 방법을 사용하게됩니다.
 * Pattern의 matches와 find의 차이점은 무엇일까요?
 */
@Slf4j
@Service
public class RegexService {

    public static void main(String[] args) {
        // 사용할 패턴을 입력해줍니다.
        Pattern pattern = Pattern.compile("[A-Z]+"); // 연속된 알파벳 소문자가 1글자 이상인 부분문자열

        // 패턴 찾는 과정 로깅
        isFind(pattern, "AB가CD나E라F");
        isFind(pattern, "ABCD");
        isFind(pattern, "가나다라");

        // Match 사용
        isMatch(pattern, "ABCD나");
        isMatch(pattern, "ABCD");
        isMatch(pattern, "가나다라");
    }

    /**
     * Pattern의 Matcher의 find의 코드는 다음과 같습니다.
     * 현재 검색위치에서 시작해서, 패턴에 일치하는 부분(subsequence)를 찾는 메서드입니다.
     * public boolean find() {
     * int nextSearchIndex = last;
     * if (nextSearchIndex == first)
     * nextSearchIndex++;
     * <p>
     * if (nextSearchIndex < from)
     * nextSearchIndex = from;
     * <p>
     * if (nextSearchIndex > to) {
     * for (int i = 0; i < groups.length; i++)
     * groups[i] = -1;
     * return false;
     * }
     * return search(nextSearchIndex);
     * }
     */
    public static void isFind(Pattern pattern, String text) {
        log.info("find 검사 타겟 : {}", text);
        Matcher matcher = pattern.matcher(text);
        boolean isFound = false;
        while (matcher.find()) {
            log.info("find 부분집합 : {}", matcher.group()); // 부분집합을 발견할때마다 로깅합니다.
            isFound = true;
        }
        log.info("find 결과 : {}\n", isFound);
    }

    /**
     * Pattern Matcher의 match코드는 다음과같습니다.
     * find와는 다르게, 제공된 정규식이 전체코드에 해당하는지에 대한 여부를 확인하는 메서드입니다.
     * 즉, 현재 region(문자열)이 패턴과 완전이 일치해야만 true가 반환됩니다.
     * 내부적으로 match(from, anchor)를 호출하여 from-to 구간 전체가 패턴으로 덮히는지 확인합니다.
     *
     * <p>
     * boolean match(int from, int anchor) {
     * this.hitEnd = false;
     * this.requireEnd = false;
     * from        = from < 0 ? 0 : from;
     * this.first  = from;
     * this.oldLast = oldLast < 0 ? from : oldLast;
     * for (int i = 0; i < groups.length; i++)
     * groups[i] = -1;
     * for (int i = 0; i < localsPos.length; i++) {
     * if (localsPos[i] != null)
     * localsPos[i].clear();
     * }
     * acceptMode = anchor;
     * boolean result = parentPattern.matchRoot.match(this, from, text);
     * if (!result)
     * this.first = -1;
     * this.oldLast = this.last;
     * this.modCount++;
     * return result;
     * }
     */
    public static void isMatch(Pattern pattern, String text) {
        log.info("matcher 검사 타겟 : {}", text);
        Matcher matcher = pattern.matcher(text);
        log.info("match 결과 : {}", matcher.matches());
    }

    /**
     * 사용시 유의할점
     * 1. Pattern은 정규식을 컴파일하는 과정에서 상당한 리소스를 사용한다.
     * 따라서, 동일한 정규식을 반복적으로 사용해야한다면 Pattern객체를 재사용하는 것이 좋다.
     * 2. matches()는 전체 매치입니다.
     * 따라서, ^,$등을 중복해서 사용할 필요가 없습니다.
     * 3. 앞부분만 특정검사를 하기 위해서는 lookingAt()이 효율적입니다.
     * 4. 백슬래시를 주의해서 사용해야합니다.
     * 자바에서는 \d가 아닌 \\d로 처리해야 백슬래시를 인식합니다.
     * 5. 사용자 입력 그대로를 가지고 패턴을 만들때는 compile이 아닌 quote를 사용합니다.
     * 6. .*와 같이 큰 범주를 가지고 검사를 실시하게 될 시 성능악화로 이어질 수 있습니다.
     * 최대한 구체적인 정규식을 사용하도록 노력합시다.
     * 7. 줄바꿈 주의
     * 여러 줄을 다루고 싶다면 플래그를 명확하게 지시해야합니다.
     * . : 줄바꿈제외 전체문자
     * ^ : 문자열의시작
     * $ : 문자열의 종료
     *  Pattern.DOTALL, Pattern.MULTILINE등과 같은 플래그를 사용함으로써 여러 줄 을 다룰 수 있습니다.
     * 8. Pattern은 Thread-safe이지만, Matcher는 아니다.
     * Matcher는 내부 상태 (first, last, groups[])의 정보를 계속해서 변화시키므로, thread-safe하지 않습니다.
     */

}
