import kotlinx.coroutines.*
import kotlinx.coroutines.future.await
import java.util.concurrent.CompletableFuture

fun main(): Unit = runBlocking {
    val result1: Int = call1_1()
    val result2 = call2_1(result1)

    printWithThread(result2)
}

suspend fun call1_1(): Int{
    return CoroutineScope(Dispatchers.Default).async {
        Thread.sleep(1_000L)
        100
    }.await()
}

suspend fun call2_1(num: Int): Int{
    return CompletableFuture.supplyAsync{
        Thread.sleep(1_000L)
        num * 2
    }.await()

}

