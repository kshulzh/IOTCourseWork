package we.iot.coursework.dataservices.model

import we.iot.coursework.common.model.SensorType
import java.time.LocalDateTime
import java.util.UUID

open class SensorData<T>(
    val sensorId: UUID,
    val type: SensorType,
    val value: T,
    val time: LocalDateTime
) {
}