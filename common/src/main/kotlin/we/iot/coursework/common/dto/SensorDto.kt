package we.iot.coursework.common.dto

import we.iot.coursework.common.model.SensorType
import java.util.*

class SensorDto(
    val id: UUID?,
    val type: SensorType,
    val name: String
)