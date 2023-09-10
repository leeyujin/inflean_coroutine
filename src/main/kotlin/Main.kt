import kotlinx.coroutines.*

fun main(): Unit = runBlocking{
    printWithThread("START")
    launch {
        newRoutine()
    }
    yield()
    printWithThread("END")

}

suspend fun newRoutine() {
    val num1 = 1
    val num2 = 2
    yield()
    printWithThread("${num1 + num2}")

}

fun printWithThread(str: Any) {
    println("[${Thread.currentThread().name}] $str")
}
