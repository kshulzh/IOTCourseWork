package we.iot.coursework.dataservices.mapper

import we.iot.coursework.common.dto.SensorDataDto
import we.iot.coursework.dataservices.model.SensorData

interface SensorDataMapper<O : SensorData<*>> : Mapper<SensorDataDto, O> {
}