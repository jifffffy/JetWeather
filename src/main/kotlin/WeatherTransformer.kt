class WeatherTransformer {
    private fun extractCurrentWeatherFrom(response: WeatherResponse) = WeatherCard(
        condition = response.current.condition.text,
        iconUrl = "https:" + response.current.condition.icon.replace("64x64", "128x128"),
        temperature = response.current.tempC,
        feelsLike = response.current.feelslikeC
    )

    private fun extractForecastWeatherFrom(response: WeatherResponse): List<WeatherCard> {
        return response.forecast.forecastday.map { forecastDay ->
            WeatherCard(
                condition = forecastDay.day.condition.text,
                iconUrl = "https:" + forecastDay.day.condition.icon,
                temperature = forecastDay.day.avgtempC,
                feelsLike = avgFeelsLike(forecastDay),
                chanceOfRain = avgChanceOfRain(forecastDay),
            )
        }
    }

    fun transform(response: WeatherResponse): WeatherResults = with(response) {
        val current = extractCurrentWeatherFrom(this)
        val forecast = extractForecastWeatherFrom(this)

        WeatherResults(
            currentWeather = current,
            forecast = forecast,
        )
    }

    private fun avgChanceOfRain(forecastDay: Forecastday): Double? = forecastDay.hour.map(Hour::chanceOfRain).average()

    private fun avgFeelsLike(forecastDay: Forecastday): Double = forecastDay.hour.map(Hour::feelslikeC).average()
}

