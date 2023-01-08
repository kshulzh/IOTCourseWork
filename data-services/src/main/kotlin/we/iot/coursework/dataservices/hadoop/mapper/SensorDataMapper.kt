package we.iot.coursework.dataservices.hadoop.mapper

import org.apache.hadoop.io.LongWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Mapper
import we.iot.coursework.dataservices.filter.FilterStorage
import we.iot.coursework.dataservices.filter.SensorDataFilter
import we.iot.coursework.dataservices.hadoop.Constants
import we.iot.coursework.dataservices.hadoop.Constants.FILTER_KEY
import we.iot.coursework.dataservices.hadoop.io.SensorDataWritable
import we.iot.coursework.dataservices.mapper.SensorDataFromCSVString
import java.time.LocalDate
import java.time.Period

class SensorDataMapper : Mapper<LongWritable, Text, Text, SensorDataWritable>() {
    lateinit var filter: SensorDataFilter
    lateinit var period: Period
    lateinit var periods:List<Pair<LocalDate,LocalDate>>
    override fun setup(context: Context?) {
        filter = FilterStorage[context!!.configuration[FILTER_KEY]] ?: throw NullPointerException()
        period = Period.parse(context?.configuration?.get(Constants.PERIOD_KEY) ?: "P1D")
        super.setup(context)
    }

    override fun map(key: LongWritable?, `val`: Text?, context: Context?) {
        try {
            val value = `val` ?: return
            val sensorData = SensorDataFromCSVString.mapFrom(value.toString())
            if(filter.check(sensorData)) {
                context?.write(Text(sensorData.sensorId.toString()),SensorDataWritable(sensorData))
            }
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }

}