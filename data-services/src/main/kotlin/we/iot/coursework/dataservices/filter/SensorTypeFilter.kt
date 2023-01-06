package we.iot.coursework.dataservices.filter

import we.iot.coursework.common.model.SensorType
import we.iot.coursework.dataservices.model.SensorData

fun types(vararg types: String): SensorDataFilter {
    return types(*types.map { SensorType.valueOf(it) }.toTypedArray())
}

fun types(vararg types: SensorType): SensorDataFilter {
    return SensorTypeFilter(*types)
}

class SensorTypeFilter(
    vararg val types: SensorType
) : SensorDataFilter {
    override fun check(value: SensorData<*>): Boolean {
        return types.contains(value.type)
    }
}