package click.clearline.todoapi.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TodoSummaryResponseDto {
    private String summary;

    public static TodoSummaryResponseDto from(String text) {
        return TodoSummaryResponseDto.builder()
                .summary(text)
                .build();
    }
}
