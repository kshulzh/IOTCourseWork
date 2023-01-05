package we.iot.coursework.dataservices.model

import we.iot.coursework.common.model.SensorType
import java.time.LocalDateTime
import java.util.UUID

class LoadShelvesSensorData(sensorId: UUID, value: Double, time: LocalDateTime) :
    SensorData<Double>(sensorId, SensorType.LOAD_SHELVES_SENSOR, value, time)