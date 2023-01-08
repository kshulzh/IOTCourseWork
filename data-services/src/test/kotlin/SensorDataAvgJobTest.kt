import org.junit.jupiter.api.Test
import we.iot.coursework.common.model.SensorType
import we.iot.coursework.dataservices.filter.endDate
import we.iot.coursework.dataservices.filter.startDate
import we.iot.coursework.dataservices.hadoop.jobs.SensorDataAvgJobRunner
import we.iot.coursework.dataservices.hadoop.jobs.buildFilter
import we.iot.coursework.dataservices.mapper.SensorDataFromCSVString
import java.io.File
import java.net.URI
import java.time.LocalDateTime
import java.time.Period

class SensorDataAvgJobTest {
    @Test
    fun filterTest() {
        val start = LocalDateTime.of(2020,10,10,0,0)
        val end = LocalDateTime.of(2022,10,10,0,0)
        val period = Period.parse("P2D")
        val sensorTypes = setOf(SensorType.LOAD_SHELVES_SENSOR)
        val filter = buildFilter(start, end, null, sensorTypes)
        val `in` = "8177c8e0-77a6-40c3-94fd-0ea31b1b5af9,LOAD_SHELVES_SENSOR,2.0,2020-11-10T00:00"

        val d = SensorDataFromCSVString.mapFrom(`in`)
    }
    @Test
    fun shouldGetAvg() {
        val start = LocalDateTime.of(2020,10,10,0,0)
        val end = LocalDateTime.of(2022,10,10,0,0)
        val period = Period.parse("P2D")
        val sensorTypes = setOf(SensorType.LOAD_SHELVES_SENSOR)
        val filter = buildFilter(start, end, null, sensorTypes)
        SensorDataAvgJobRunner()
            .run("C:\\Users\\kiril\\IdeaProjects\\IOTCourseWork\\data-services\\src\\test\\resources\\testFile.txt", "C:\\Users\\kiril\\IdeaProjects\\IOTCourseWork\\out1\\${LocalDateTime.now().nano}", filter, start, end, period)

    }
}