package we.iot.coursework.common.api

import we.iot.coursework.common.dto.SensorDataDto
import we.iot.coursework.common.dto.SensorDto
import java.util.UUID

interface SensorApi {
    fun postSensorData(data: SensorDataDto)

    fun addSensor(sensor: SensorDto)

    fun getSensor(sensorId: UUID) : SensorDto

    fun removeSensor(sensorId: UUID)

    fun editSensor(sensorId: UUID, sensor: SensorDto)
}