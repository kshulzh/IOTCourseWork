package we.iot.coursework.dataservices.mapper

import we.iot.coursework.common.dto.SensorDataDto
import we.iot.coursework.dataservices.model.FireSensorData
import we.iot.coursework.common.model.SensorType

object FireDataMapper : SensorDataMapper<FireSensorData> {
    override fun mapFrom(o: SensorDataDto): FireSensorData {
        return FireSensorData(o.sensorId, mapToDouble(o.data),o.time ?: throw NullPointerException())
    }

    override fun mapTo(o: FireSensorData): SensorDataDto {
        return SensorDataDto(o.sensorId, SensorType.FIRE_SENSOR, o.value, o.time)
    }
}