class TrainRoute {
    private val cities = listOf(
        "Гамбург", "Барнаул", "Новосибирск", "Томск", "Санкт-Петербург",
        "Москва", "Красноярск", "Тюмень", "Чита", "Хабаровск",
        "Владивосток", "Казань", "Самара", "Екатеринбург", "Пермь", "Воркута"
    )
    private var startCity = ""
    private var endCity = ""
    init {
        startCity = cities.random()
        endCity = cities.random()
        while (endCity == startCity) {
            endCity = cities.random()
        }
    }
    fun getRoute() = "$startCity - $endCity"
}

class TrainCar(val capacity: Int) //вагон поезда

class Train(private var route: TrainRoute) {
    val cars: MutableList<TrainCar> = mutableListOf() //Список вагонов
    fun sendTrain(passengerCount: Int) {
        println("Поезд ${route.getRoute()}, состоящий из ${cars.size} вагонов с $passengerCount пассажирами, отправлен.")
        var remainingPassengers = passengerCount //Кол-во оставшихся пассажиров
        for ((index, car) in cars.withIndex()) { // Проход по каждому вагону
            val passengerCountInCar = if (remainingPassengers > 0) { // Если есть оставшиеся пассажиры
                val passengersToAdd = minOf(car.capacity, remainingPassengers) // Определение количества пассажиров, которые можно разместить в текущем вагоне
                remainingPassengers -= passengersToAdd // Уменьшение количества оставшихся пассажиров
                passengersToAdd // Возвращается количество пассажиров, которые удалось разместить в вагоне
            } else 0
            println("Вагон ${index + 1}: Вместимость - ${car.capacity}, Пассажиры - $passengerCountInCar") // Вывод информации о вагоне
        }
    }
    fun generateCars(passengerCount: Int) {
        var passengersLeft = passengerCount // Количество оставшихся пассажиров
        while (passengersLeft > 0) {
            val carCapacity = (5..25).random() // Выбирается случайная вместимость вагона
            val car = TrainCar(carCapacity) // Создается вагон с заданной вместимостью
            cars.add(car) // Вагон добавляется в список вагонов поезда
            passengersLeft -= carCapacity // Уменьшение количества оставшихся пассажиров на вместимость вагона
        }
    }
}

fun main() {
    var passengerCount = 0
    var train: Train? = null
    var currentRoute: TrainRoute? = null
    while (true) {
        when (getInput("""
            Выберите действие:
            1 - Создать направление
            2 - Продать билеты
            3 - Сформировать поезд
            4 - Отправить поезд
            5 - Завершить работу
            
        """.trimIndent()).uppercase()) {
            "1" -> {
                currentRoute = TrainRoute()
                println("Направление создано: ${currentRoute.getRoute()}")
                train = Train(currentRoute)
            }
            "2" -> {
                passengerCount = (5..201).random()
                println("Продано билетов: $passengerCount")
            }
            "3" -> {
                if (train == null) {
                    println("Направление не создано")
                    continue
                }
                train.cars.clear()
                train.generateCars(passengerCount)
                println("Поезд сформирован: ${currentRoute!!.getRoute()}")
                println("Количество вагонов: ${train.cars.size}")
                for (index in train.cars.indices)
                    println("Вместимость вагона ${index + 1}: ${train.cars[index].capacity}")
            }
            "4" -> train?.sendTrain(passengerCount)
            "5" -> {
                println("Работа программы завершена.")
                break
            }
            else -> println("Некорректный ввод. Попробуйте еще раз.")
        }
    }
}

fun getInput(prompt: String): String {
    print(prompt)
    return readln()
}