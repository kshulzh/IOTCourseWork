package we.iot.coursework.dataservices.filter

import we.iot.coursework.dataservices.model.SensorData
import java.util.UUID

fun UUIDS(vararg uuids: String): SensorDataFilter {
    return UUIDS(*uuids.map { UUID.fromString(it) }.toTypedArray())
}

fun UUIDS(vararg uuids: UUID): SensorDataFilter {
    return SensorUUIDFilter(*uuids)
}

class SensorUUIDFilter(
    vararg val uuids: UUID
) : SensorDataFilter {
    override fun check(value: SensorData<*>): Boolean {
        return uuids.contains(value.sensorId)
    }
}