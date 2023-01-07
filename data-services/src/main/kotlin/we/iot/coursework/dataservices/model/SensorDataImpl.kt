package we.iot.coursework.dataservices.model

import we.iot.coursework.common.model.SensorType
import java.time.LocalDateTime
import java.util.UUID

class SensorDataImpl(
    sensorId: UUID,
    type: SensorType,
    data: Any,
    time: LocalDateTime) : SensorData<Any>(sensorId, type, data, time)
