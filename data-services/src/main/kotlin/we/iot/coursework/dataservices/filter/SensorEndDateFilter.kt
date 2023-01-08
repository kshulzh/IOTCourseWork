package we.iot.coursework.dataservices.filter

import we.iot.coursework.dataservices.model.SensorData
import java.time.LocalDateTime

fun endDate(date: String): SensorDataFilter {
    return endDate(LocalDateTime.parse(date))
}

fun endDate(date: LocalDateTime): SensorDataFilter {
    return SensorEndDateFilter(date)
}

class SensorEndDateFilter(
    val endDate: LocalDateTime
) : SensorDataFilter {
    override fun check(value: SensorData<*>): Boolean {
        return !endDate.isBefore(value.time)
    }
}