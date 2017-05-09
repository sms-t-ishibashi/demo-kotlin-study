package helloworld

data class Customer(val name: String, val email: String)

fun main(args: Array<String>) {
    val customer = Customer("John Smith", "john.smith@email.com")
    println(customer)
    println("Hello World !!!")

    val numbers = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println("Even numbers are ${numbers.filter { it % 2 == 0}}")

    val countriesCities = listOf(Pair("Japan", "Tokyo"), Pair("America", "Washington"))
    for((country, city) in countriesCities) {
        println("Country: ${country} - City: ${city}")
    }
}