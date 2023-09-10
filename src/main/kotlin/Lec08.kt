import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    val result1: Deferred<Int> = async {
        call1()
    }

    val result2 = async {
        call2(result1.await())
    }

    printWithThread(result2.await())
}

fun call1(): Int{
    Thread.sleep(1_000L)
    return 100
}

fun call2(num: Int): Int{
    Thread.sleep(1_000L)
    return num * 2
}
