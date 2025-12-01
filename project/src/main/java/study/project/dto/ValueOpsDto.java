package study.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class ValueOpsDto {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class SavedData {
        private final String username;
        private final String password;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class SaveRequest {
        private final String username;
        private final String password;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class SearchResponse {
        private final boolean isEqual;
        private final SavedData saveData;
    }
}
