package we.iot.coursework.common.dto

import java.time.LocalDate
import java.time.LocalDateTime

class AnalyzedSensorDataDto(
    val start: String,
    val end: String,
    val data: Any
) {
}