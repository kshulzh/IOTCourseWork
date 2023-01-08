package we.iot.coursework.dataservices.hadoop

import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Reducer
import org.codehaus.jackson.map.ObjectMapper
import we.iot.coursework.common.dto.AnalyzedSensorDataDto
import we.iot.coursework.common.model.SensorType
import we.iot.coursework.dataservices.hadoop.io.SensorDataWritable
import we.iot.coursework.dataservices.mapper.SensorDataFromCSVString
import we.iot.coursework.dataservices.mapper.mapToDouble
import we.iot.coursework.dataservices.model.SensorData
import we.iot.coursework.dataservices.model.SensorDataImpl
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period


class SensorDataAvgReducer :
    Reducer<Text, Text, Text, Text>() {
    lateinit var period: Period
    lateinit var periods:List<Pair<LocalDate, LocalDate>>
    lateinit var start:LocalDateTime
    lateinit var end:LocalDateTime

    override fun setup(context: Context?) {
        period = Period.parse(context!!.configuration[Constants.PERIOD_KEY])
        start = LocalDateTime.parse(context.configuration[Constants.START_DAY_KEY])
        end = LocalDateTime.parse(context.configuration[Constants.END_DAY_KEY])
        var rawPeriods = mutableListOf<Pair<LocalDate, LocalDate>>()
        var temp = start

        while (!temp.plus(period).isAfter(end)) {
            rawPeriods.add(temp.toLocalDate() to temp.plus(period).toLocalDate())
            temp = temp.plus(period)
        }
        rawPeriods.add(temp.toLocalDate() to end.toLocalDate())

        periods = rawPeriods.toList()
    }

    override fun reduce(key: Text?, `val`: MutableIterable<Text>?, context: Context?) {
        try {
            var value = mutableListOf<SensorDataImpl>()
            `val`?.forEach {
                value.add(SensorDataFromCSVString.mapFrom(it.toString()))
            }
            val first = value.first()
            val collectedValue = collectByPeriod(value)
            val objectMapper = ObjectMapper()
            val result = when(first.type) {
                SensorType.FIRE_SENSOR ->{
                    collectedValue.map {
                        it.key to reduceDouble(it.value.map {
                            mapToDouble(it)
                        })
                    }
                }
                SensorType.SMOKE_SENSOR ->{
                    collectedValue.map {
                        it.key to reduceDouble(it.value.map {
                            mapToDouble(it)
                        })
                    }
                }
                SensorType.LOAD_SHELVES_SENSOR ->{
                    collectedValue.map {
                        it.key to reduceDouble(it.value.map {
                            mapToDouble(it)
                        })
                    }
                }
                SensorType.MOTION_SENSOR ->{
                    collectedValue.map {
                        it.key to reduceDouble(it.value.map {
                            mapToDouble(it)
                        })
                    }
                }
                else ->{
                    null
                }
            }
            var cResult = result?.map {
                AnalyzedSensorDataDto(it.first.first.toString(),it.first.second.toString(),it.second)
            }
            context?.write(key, Text(objectMapper.writeValueAsString(cResult)))
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
            if(!it.first.isAfter(time.toLocalDate()) && !it.second.isBefore(time.toLocalDate())) {
                return it;
            }
        }

        throw RuntimeException("Not found")
    }

    fun reduceDouble(list: List<Double>) : Double {
        return list.average()
    }
}