package study.project.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class XmlParser {
    public static String xmlData = """
            보안으로인한 데이터 가리기
            """;


    private static final Pattern DSDATA_BLOCK = Pattern.compile("<secDNS;dsData>(.*?)</secDNS;dsData>", Pattern.DOTALL);

    public static void main(String[] args) {
        List<DsData> dsDataList = extractAllDsData(xmlData);
        int index = 1;
        for (DsData dsData : dsDataList) {
            log.info(index + " 번째, alg : {}", dsData.alg);
            log.info(index + " 번째, digestType : {}", dsData.digestType);
            log.info(index + " 번째, digest : {}", dsData.digest);
            log.info(index + " 번째, keyTag : {}", dsData.keyTag);
        }
    }

    // 태그 텍스트 추출 (블록 안에서)
    private static Optional<String> extractText(String xml, String tag) {
        String re = "<" + Pattern.quote(tag) + ">(.*?)</" + Pattern.quote(tag) + ">";
        Matcher m = Pattern.compile(re, Pattern.DOTALL).matcher(xml);
        return m.find() ? Optional.of(m.group(1).trim()) : Optional.empty();
    }

    public static List<DsData> extractAllDsData(String xml) {
        List<DsData> result = new ArrayList<>();

        Matcher m = DSDATA_BLOCK.matcher(xml);
        while (m.find()) {
            String block = m.group(1);
            DsData dsData = new DsData(
                    extractText(block, "secDNS;keyTag").orElse(null),
                    extractText(block, "secDNS;alg").orElse(null),
                    extractText(block, "secDNS;digestType").orElse(null),
                    extractText(block, "secDNS;digest").orElse(null)
            );
            result.add(dsData);
        }

        return result;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class DsData {
        private final String keyTag;
        private final String alg;
        private final String digestType;
        private final String digest;
    }
}
