import kotlinx.coroutines.*

fun main(): Unit = runBlocking {
    // START - END - 30 (X)
    // START - 30 - END (O) -> coroutineScope으로 인해 즉시 호출
    printWithThread("START")
    printWithThread(calculateResult())
    printWithThread("END")
}

// 또다른 함수로 분리하고자 할때
suspend fun calculateResult(): Int = coroutineScope {
    val num1 = async{
        delay(1_000L)
        10
    }

    val num2 = async {
        delay(1_000L)
        20
    }
    num1.await() + num2.await()
}

suspend fun calculateResult2(): Int = withContext(Dispatchers.Default) {
    val num1 = async{
        delay(1_000L)
        10
    }

    val num2 = async {
        delay(1_000L)
        20
    }
    num1.await() + num2.await()
}


