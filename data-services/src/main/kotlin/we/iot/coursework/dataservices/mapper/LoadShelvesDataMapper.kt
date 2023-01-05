package we.iot.coursework.dataservices.mapper

import we.iot.coursework.common.dto.SensorDataDto
import we.iot.coursework.dataservices.model.LoadShelvesSensorData
import we.iot.coursework.common.model.SensorType

object LoadShelvesDataMapper : SensorDataMapper<LoadShelvesSensorData> {
    override fun mapFrom(o: SensorDataDto): LoadShelvesSensorData {
        return LoadShelvesSensorData(o.sensorId, mapToDouble(o.data),o.time ?: throw NullPointerException())
    }

    override fun mapTo(o: LoadShelvesSensorData): SensorDataDto {
        return SensorDataDto(o.sensorId, SensorType.LOAD_SHELVES_SENSOR, o.value, o.time)
    }
}