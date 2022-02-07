package com.edu.spoot.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data   //왠만하면 사용 지양
@Builder(toBuilder = true)
public class SampleDTO {
    private Long sno;
    private String first;
    private String last;
    private LocalDateTime regTime;

}
