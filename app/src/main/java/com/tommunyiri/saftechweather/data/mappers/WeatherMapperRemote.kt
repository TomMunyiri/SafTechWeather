package com.tommunyiri.saftechweather.data.mappers

import com.tommunyiri.saftechweather.domain.model.CurrentWeather
import com.tommunyiri.saftechweather.domain.model.NetworkCurrentWeather

/**
 * Created by Tom Munyiri on 19/01/2024.
 * Company: Eclectics International Ltd
 * Email: munyiri.thomas@eclectics.io
 */
class WeatherMapperRemote : BaseMapper<NetworkCurrentWeather, CurrentWeather> {
    override fun transformToDomain(type: NetworkCurrentWeather): CurrentWeather =
        CurrentWeather(
            cloud = type.cloud,
            condition = type.condition,
            dewpoint_c = type.dewpoint_c,
            dewpoint_f = type.dewpoint_f,
            feelslike_c = type.feelslike_c,
            feelslike_f = type.feelslike_f,
            gust_kph = type.gust_kph,
            gust_mph = type.gust_mph,
            heatindex_c = type.heatindex_c,
            heatindex_f = type.heatindex_f,
            humidity = type.humidity,
            is_day = type.is_day,
            last_updated = type.last_updated,
            last_updated_epoch = type.last_updated_epoch,
            precip_in = type.precip_in,
            precip_mm = type.precip_mm,
            pressure_in = type.pressure_in,
            pressure_mb = type.pressure_mb,
            temp_c = type.temp_c,
            temp_f = type.temp_f,
            uv = type.uv,
            vis_km = type.vis_km,
            vis_miles = type.vis_miles,
            wind_degree = type.wind_degree,
            wind_dir = type.wind_dir,
            wind_kph = type.wind_kph,
            wind_mph = type.wind_mph,
            windchill_c = type.windchill_c,
            windchill_f = type.windchill_f,
        )

    override fun transformToDto(type: CurrentWeather): NetworkCurrentWeather =
        NetworkCurrentWeather(
            cloud = type.cloud,
            condition = type.condition,
            dewpoint_c = type.dewpoint_c,
            dewpoint_f = type.dewpoint_f,
            feelslike_c = type.feelslike_c,
            feelslike_f = type.feelslike_f,
            gust_kph = type.gust_kph,
            gust_mph = type.gust_mph,
            heatindex_c = type.heatindex_c,
            heatindex_f = type.heatindex_f,
            humidity = type.humidity,
            is_day = type.is_day,
            last_updated = type.last_updated,
            last_updated_epoch = type.last_updated_epoch,
            precip_in = type.precip_in,
            precip_mm = type.precip_mm,
            pressure_in = type.pressure_in,
            pressure_mb = type.pressure_mb,
            temp_c = type.temp_c,
            temp_f = type.temp_f,
            uv = type.uv,
            vis_km = type.vis_km,
            vis_miles = type.vis_miles,
            wind_degree = type.wind_degree,
            wind_dir = type.wind_dir,
            wind_kph = type.wind_kph,
            wind_mph = type.wind_mph,
            windchill_c = type.windchill_c,
            windchill_f = type.windchill_f,
        )
}
