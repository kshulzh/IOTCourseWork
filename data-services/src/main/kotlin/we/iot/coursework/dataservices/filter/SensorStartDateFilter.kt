package we.iot.coursework.dataservices.filter

import we.iot.coursework.dataservices.model.SensorData
import java.time.LocalDateTime

fun startDate(date: String): SensorDataFilter {
    return endDate(LocalDateTime.parse(date))
}

fun startDate(date: LocalDateTime): SensorDataFilter {
    return SensorStartDateFilter(date)
}

class SensorStartDateFilter(
    val startDate: LocalDateTime
) : SensorDataFilter {
    override fun check(value: SensorData<*>): Boolean {
        return !startDate.isBefore(value.time)
    }
}