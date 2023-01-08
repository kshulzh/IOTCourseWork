import org.junit.jupiter.api.Test
import we.iot.coursework.dataservices.mapper.SensorDataFromCSVString

class SensorDataToCVSConvertorTest {
    @Test
    fun shouldParse() {
        val `in` = "8177c8e0-77a6-40c3-94fd-0ea31b1b5af9,LOAD_SHELVES_SENSOR,2.0,2020-10-10T00:00"
        SensorDataFromCSVString.mapFrom(`in`)
    }
}