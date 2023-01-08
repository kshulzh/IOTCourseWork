package we.iot.coursework.dataservices.hadoop.jobs

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.conf.Configured
import org.apache.hadoop.fs.FSDataInputStream
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.IOUtils
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.util.Tool
import org.apache.hadoop.util.ToolRunner
import we.iot.coursework.common.model.SensorType
import we.iot.coursework.dataservices.filter.*
import we.iot.coursework.dataservices.hadoop.Constants.END_DAY_KEY
import we.iot.coursework.dataservices.hadoop.Constants.FILTER_KEY
import we.iot.coursework.dataservices.hadoop.Constants.PERIOD_KEY
import we.iot.coursework.dataservices.hadoop.Constants.START_DAY_KEY
import we.iot.coursework.dataservices.hadoop.SensorDataAvgReducer
import we.iot.coursework.dataservices.hadoop.mapper.SensorDataMapper
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URI
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
class SensorDataAvgJobRunner : Configured(), Tool{
//    lateinit var `in`:String
//    lateinit var `out`:String
//    lateinit var filter:SensorDataFilter
//    lateinit var start: LocalDateTime
//    lateinit var end: LocalDateTime
//    lateinit var period: Period

//    fun setP(`in`:String,
//            `out`:String,
//            filter:SensorDataFilter,
//            start: LocalDateTime,
//            end: LocalDateTime,
//            period: Period) {
//        this.end = end
//        this.filter = filter
//        this.`in` = `in`
//        this.out = out
//        this.period = period
//        this.start = start
//    }
//
//    fun run() {
//        val conf = Configuration()
//        val filterId = FilterStorage.add(filter)
//        conf[START_DAY_KEY] = start.toString()
//        conf[END_DAY_KEY] = end.toString()
//        conf[FILTER_KEY] = filterId
//        conf[PERIOD_KEY] = period.toString()
//
//        val job: Job = Job.getInstance(conf, "word count")
//        job.setJarByClass(this::class.java)
//        job.setMapperClass(SensorDataMapper::class.java)
//        job.mapOutputKeyClass = Text::class.java
//        job.mapOutputValueClass = Text::class.java
//
//        job.setReducerClass(SensorDataAvgReducer::class.java)
//        job.outputKeyClass = Text::class.java
//        job.outputValueClass = Text::class.java
//        FileInputFormat.addInputPath(job, Path(`in`))
//        FileOutputFormat.setOutputPath(job, Path(out))
//        println(job.waitForCompletion(true))
//    }

    override fun run(args: Array<out String>?): Int {
        val argMap = parseArgs(args!!)
        val `in` = argMap["in"]?:throw RuntimeException("in must be presented")
        val out = argMap["out"]?:throw RuntimeException("out must be presented")
        val start = LocalDateTime.parse(argMap["start"]?:LocalDateTime.of(2000,10,10,0,0).toString())
        val end = LocalDateTime.parse(argMap["end"]?:LocalDateTime.now().toString())
        val period = argMap["period"]?:Period.ofDays(1)
        val sensorTypes = argMap["types"]?.split(",")?.map {
            SensorType.valueOf(it)
        }?.toSet()
        val uuids = argMap["uuids"]?.split(",")?.map {
            UUID.fromString(it)
        }?.toSet()
        val filter = buildFilter(start, end, uuids, sensorTypes)

        val conf = Configuration()
        val filterId = FilterStorage.add(filter)
        conf[START_DAY_KEY] = start.toString()
        conf[END_DAY_KEY] = end.toString()
        conf[FILTER_KEY] = filterId
        conf[PERIOD_KEY] = period.toString()

        val job: Job = Job.getInstance(conf, "word count")
        job.setJarByClass(this::class.java)
        job.setMapperClass(SensorDataMapper::class.java)
        job.mapOutputKeyClass = Text::class.java
        job.mapOutputValueClass = Text::class.java

        job.setReducerClass(SensorDataAvgReducer::class.java)
        job.outputKeyClass = Text::class.java
        job.outputValueClass = Text::class.java
        FileInputFormat.addInputPath(job, Path(`in`))
        FileOutputFormat.setOutputPath(job, Path(out))
        return if(job.waitForCompletion(true)) 0 else 1
    }

    fun parseArgs(args: Array<out String>) : Map<String,String> {
        return args.map {
            val keyAndValue = it.split("=")
            keyAndValue[0] to keyAndValue[1]
        }.toMap()
    }
}