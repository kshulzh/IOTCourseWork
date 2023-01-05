package we.iot.coursework.common.dto

import we.iot.coursework.common.model.SensorType
import java.time.LocalDateTime
import java.util.*

class SensorDataDto(
    val sensorId: UUID,
    val type: SensorType,
    val data: Any,
    val time: LocalDateTime?
)
