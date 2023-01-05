package we.iot.coursework.common.dto

import java.time.LocalDateTime

class AnalyzedSensorDataDto(
    val start: LocalDateTime,
    val end: LocalDateTime,
    val data: Any
) {
}