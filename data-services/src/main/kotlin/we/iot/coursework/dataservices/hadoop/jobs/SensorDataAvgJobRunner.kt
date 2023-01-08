package we.iot.coursework.dataservices.hadoop.jobs

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.FloatWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import we.iot.coursework.common.model.SensorType
import we.iot.coursework.dataservices.filter.*
import we.iot.coursework.dataservices.hadoop.Constants.END_DAY_KEY
import we.iot.coursework.dataservices.hadoop.Constants.FILTER_KEY
import we.iot.coursework.dataservices.hadoop.Constants.PERIOD_KEY
import we.iot.coursework.dataservices.hadoop.Constants.START_DAY_KEY
import we.iot.coursework.dataservices.hadoop.SensorDataAvgReducer
import we.iot.coursework.dataservices.hadoop.io.SensorDataWritable
import we.iot.coursework.dataservices.hadoop.mapper.SensorDataMapper
import java.io.File
import java.time.LocalDateTime
import java.time.Period
import java.util.UUID

fun buildFilter(start: LocalDateTime,
                end: LocalDateTime,
                uuids: Set<UUID>?,
                sensorTypes: Set<SensorType>?) : SensorDataFilter {
    var filters = mutableListOf<SensorDataFilter>(
        startDate(start),
        endDate(end)
    )
    if(!uuids.isNullOrEmpty()) {
        filters.add(UUIDS(*uuids.toTypedArray()))
    }
    if(!sensorTypes.isNullOrEmpty()) {
        filters.add(types(*sensorTypes.toTypedArray()))
    }
    return and(*filters.toTypedArray())
}
class SensorDataAvgJobRunner {
    fun run(
            `in`:String,
            out:String,
            filter:SensorDataFilter,
            start: LocalDateTime?,
            end: LocalDateTime?,
            period: Period) {
        val conf = Configuration()
        val filterId = FilterStorage.add(filter)
        conf[START_DAY_KEY] = start?.toString()
        conf[END_DAY_KEY] = end?.toString()
        conf[FILTER_KEY] = filterId
        conf[PERIOD_KEY] = period.toString()

        val job: Job = Job.getInstance(conf, "word count")
        job.setJarByClass(this::class.java)
        job.setMapperClass(SensorDataMapper::class.java)
        job.mapOutputKeyClass = Text::class.java
        job.mapOutputValueClass = SensorDataWritable::class.java

        job.setReducerClass(SensorDataAvgReducer::class.java)
        job.combinerClass = SensorDataAvgReducer::class.java
        job.outputKeyClass = Text::class.java
        job.outputValueClass = Text::class.java
        FileInputFormat.addInputPath(job, Path(`in`))
        FileOutputFormat.setOutputPath(job, Path(out))
        println(job.waitForCompletion(true))
    }
}