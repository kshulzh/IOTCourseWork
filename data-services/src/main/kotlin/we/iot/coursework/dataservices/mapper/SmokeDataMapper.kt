package we.iot.coursework.dataservices.mapper

import we.iot.coursework.common.dto.SensorDataDto
import we.iot.coursework.common.model.SensorType
import we.iot.coursework.dataservices.model.SmokeSensorData

object SmokeDataMapper : SensorDataMapper<SmokeSensorData> {
    override fun mapFrom(o: SensorDataDto): SmokeSensorData {
        return SmokeSensorData(o.sensorId, mapToDouble(o.data),o.time ?: throw NullPointerException())
    }

    override fun mapTo(o: SmokeSensorData): SensorDataDto {
        return SensorDataDto(o.sensorId, SensorType.SMOKE_SENSOR, o.value, o.time)
    }
}