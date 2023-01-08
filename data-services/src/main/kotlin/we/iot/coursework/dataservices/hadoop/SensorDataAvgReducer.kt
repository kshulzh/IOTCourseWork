package we.iot.coursework.dataservices.hadoop

import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Reducer
import org.codehaus.jackson.map.ObjectMapper
import we.iot.coursework.common.model.SensorType
import we.iot.coursework.dataservices.hadoop.io.SensorDataWritable
import we.iot.coursework.dataservices.mapper.mapToDouble
import we.iot.coursework.dataservices.model.SensorData
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period


class SensorDataAvgReducer :
    Reducer<Text, SensorDataWritable, Text, Text>() {
    lateinit var period: Period
    lateinit var periods:List<Pair<LocalDate, LocalDate>>
    lateinit var start:LocalDateTime
    lateinit var end:LocalDateTime

    override fun setup(context: Context?) {
        period = Period.parse(context!!.configuration[Constants.PERIOD_KEY])
        start = LocalDateTime.parse(context.configuration[Constants.START_DAY_KEY])
        end = LocalDateTime.parse(context.configuration[Constants.START_DAY_KEY])
        var rawPeriods = mutableListOf<Pair<LocalDate, LocalDate>>()
        var temp = start

        while (!temp.plus(period).isAfter(end)) {
            rawPeriods.add(temp.toLocalDate() to end.toLocalDate())
            temp = temp.plus(period)
        }
        rawPeriods.add(temp.toLocalDate() to end.toLocalDate())

        super.setup(context)
    }

    override fun reduce(key: Text?, `val`: MutableIterable<SensorDataWritable>?, context: Context?) {
        val value = `val`?.toList()?.map { it.value } ?: return
        val first = value.first()
        val collectedValue = collectByPeriod(value)
        val objectMapper = ObjectMapper()
        val result = when(first.type) {
            SensorType.FIRE_SENSOR ->{
                objectMapper.writeValueAsString(collectedValue.map {
                    it.key to reduceDouble(it.value.map {
                        mapToDouble(it)
                    })
                })
            }
            SensorType.SMOKE_SENSOR ->{
                objectMapper.writeValueAsString(collectedValue.map {
                    it.key to reduceDouble(it.value.map {
                        mapToDouble(it)
                    })
                })
            }
            SensorType.LOAD_SHELVES_SENSOR ->{
                objectMapper.writeValueAsString(collectedValue.map {
                    it.key to reduceDouble(it.value.map {
                        mapToDouble(it)
                    })
                })
            }
            SensorType.MOTION_SENSOR ->{
                objectMapper.writeValueAsString(collectedValue.map {
                    it.key to reduceDouble(it.value.map {
                        mapToDouble(it)
                    })
                })
            }
            else ->{
                "Not found"
            }
        }
        println(result)

        context?.write(key, Text(result))
    }

    fun <T : SensorData<Any>> collectByPeriod(
        value: List<T>,
    ): MutableMap<Pair<LocalDate, LocalDate>, MutableList<Any>>
    {
        var map = mutableMapOf<Pair<LocalDate, LocalDate>, MutableList<Any>>()
        value.forEach {
            val tempPeriod = getPeriodByTime(it.time)
            if (map[tempPeriod] != null) {
                map[tempPeriod]!!.add(it.value)
            } else {
                map[tempPeriod] = mutableListOf(it.value)
            }
        }

        return map;
    }

    fun getPeriodByTime(time: LocalDateTime) : Pair<LocalDate, LocalDate>{
        periods.forEach {
            if(!it.first.isBefore(time.toLocalDate()) && !it.second.isAfter(time.toLocalDate())) {
                return it;
            }
        }

        throw RuntimeException("Not found")
    }

    fun reduceDouble(list: List<Double>) : Double {
        return list.average()
    }
}