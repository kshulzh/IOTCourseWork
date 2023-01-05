package we.iot.coursework.common.api

import we.iot.coursework.common.dto.AnalyzedSensorDataDto
import we.iot.coursework.common.model.SensorType
import java.time.LocalDateTime
import java.util.UUID

interface SensorDataApi {
    fun analyzeData(
        start: LocalDateTime?,
        end: LocalDateTime?,
        interval: String,
        uuids: Set<UUID>?,
        sensorTypes: Set<SensorType>
    ): AnalyzedSensorDataDto
}