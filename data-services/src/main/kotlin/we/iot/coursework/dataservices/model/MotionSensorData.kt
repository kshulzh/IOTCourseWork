package we.iot.coursework.dataservices.model

import we.iot.coursework.common.model.SensorType
import java.time.LocalDateTime
import java.util.UUID

class MotionSensorData(sensorId: UUID, value: Double, time: LocalDateTime) :
    SensorData<Double>(sensorId, SensorType.MOTION_SENSOR, value, time)