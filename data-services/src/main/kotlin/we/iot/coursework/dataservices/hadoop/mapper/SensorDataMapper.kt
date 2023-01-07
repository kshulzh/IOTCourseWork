package we.iot.coursework.dataservices.hadoop.mapper

import org.apache.hadoop.io.LongWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Mapper
import we.iot.coursework.dataservices.filter.FilterStorage
import we.iot.coursework.dataservices.filter.SensorDataFilter
import we.iot.coursework.dataservices.hadoop.io.SensorDataWritable
import we.iot.coursework.dataservices.mapper.SensorDataFromCSVString

class SensorDataMapper : Mapper<LongWritable, Text, Text, SensorDataWritable>() {
    companion object {
        val FILTER_KEY = "we.iot.coursework.filter"
    }
    lateinit var filter: SensorDataFilter
    override fun setup(context: Context?) {
        filter = FilterStorage[context!!.configuration[FILTER_KEY]] ?: throw NullPointerException()
        super.setup(context)
    }

    override fun map(key: LongWritable?, `val`: Text?, context: Context?) {
        val value = `val` ?: return
        val sensorData = SensorDataFromCSVString.mapFrom(value.toString())
        if(filter.check(sensorData)) {
            context?.write(Text(sensorData.sensorId.toString()),SensorDataWritable(sensorData))
        }
    }
}