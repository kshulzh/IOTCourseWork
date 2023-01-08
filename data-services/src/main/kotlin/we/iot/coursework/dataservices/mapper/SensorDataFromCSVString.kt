package we.iot.coursework.dataservices.mapper

import we.iot.coursework.common.model.SensorType
import we.iot.coursework.dataservices.model.SensorDataImpl
import java.time.LocalDateTime
import java.util.UUID

object SensorDataFromCSVString : SensorDataFromString {
    val SEPARATOR = ","
    override fun mapFrom(o: String): SensorDataImpl {
        val rawSensorData = o.split(SEPARATOR)
        return SensorDataImpl(
            UUID.fromString(rawSensorData[0]),
            SensorType.valueOf(rawSensorData[1]),
            rawSensorData[2],
            LocalDateTime.parse(rawSensorData[3])
        )
    }

    override fun mapTo(o: SensorDataImpl): String {
        return "${o.sensorId}$SEPARATOR${o.type}$SEPARATOR${o.value}$SEPARATOR${o.time}"
    }
}