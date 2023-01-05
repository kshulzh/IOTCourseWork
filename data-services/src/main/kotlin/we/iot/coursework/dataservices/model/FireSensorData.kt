package we.iot.coursework.dataservices.model

import we.iot.coursework.common.model.SensorType
import java.time.LocalDateTime
import java.util.UUID

class FireSensorData(sensorId: UUID, value: Double, time: LocalDateTime) :
    SensorData<Double>(sensorId, SensorType.FIRE_SENSOR, value, time)