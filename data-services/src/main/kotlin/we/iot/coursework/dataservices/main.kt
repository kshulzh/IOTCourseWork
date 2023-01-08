package we.iot.coursework.dataservices

import org.apache.hadoop.util.ToolRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import we.iot.coursework.dataservices.hadoop.jobs.SensorDataAvgJobRunner

//@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    ToolRunner.run(SensorDataAvgJobRunner(), args)
    //runApplication<DemoApplication>(*args)
}