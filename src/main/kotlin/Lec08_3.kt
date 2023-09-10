import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

fun main(): Unit = runBlocking {
    val result: Int? = withTimeoutOrNull(1_000L){
        delay(1_500L)
        10 + 20
    }
//    printWithThread(result)
}