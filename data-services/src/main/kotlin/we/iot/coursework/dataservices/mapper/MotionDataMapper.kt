package we.iot.coursework.dataservices.mapper

import we.iot.coursework.common.dto.SensorDataDto
import we.iot.coursework.dataservices.model.LoadShelvesSensorData
import we.iot.coursework.dataservices.model.MotionSensorData
import we.iot.coursework.common.model.SensorType

object MotionDataMapper : SensorDataMapper<MotionSensorData> {
    override fun mapFrom(o: SensorDataDto): MotionSensorData {
        return MotionSensorData(o.sensorId, mapToDouble(o.data),o.time ?: throw NullPointerException())
    }

    override fun mapTo(o: MotionSensorData): SensorDataDto {
        return SensorDataDto(o.sensorId, SensorType.FIRE_SENSOR, o.value, o.time)
    }
}