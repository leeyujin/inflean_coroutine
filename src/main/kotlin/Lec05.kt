import kotlinx.coroutines.*


fun main(): Unit = runBlocking {
    val exceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        printWithThread("예외")
        throw throwable
    }

    // Coroutine ExceptionHandler는 부모코루틴만 동작함
    val job = CoroutineScope(Dispatchers.Default).launch(exceptionHandler){
        throw IllegalArgumentException()
    }

    delay(1_000L)
}


// 예외를 다루는방법 1 - try-catch-finally
fun example5_6(): Unit = runBlocking {

    val job = launch {
        try{
            throw IllegalArgumentException()
        }catch (e: IllegalArgumentException) {
            printWithThread("정상 종료")
        }
    }

}


fun example5_5(): Unit = runBlocking {

    // SupervisorJob : 자식 코루틴의 예외가 부모에게 전파되지않음 -> await쓰면 전파됨
    val job = async(SupervisorJob()) {
        throw IllegalArgumentException()
    }

    delay(1_000L)
//    job.await()

}


fun example5_4(): Unit = runBlocking {

    // 자식의 경우 예외를 던짐 -> 부모에게 예외 전파됨
    val job = async {
        throw IllegalArgumentException()
    }

    delay(1_000L)
//    job.await()

}


fun example5_3(): Unit = runBlocking {

    // job.awiat을 쓰지않으면 예외를 출력하지않고 종료됨
    val job = CoroutineScope(Dispatchers.Default).async {
        throw IllegalArgumentException()
    }

    delay(1_000L)
    // 이걸 써야만 잡게됨
    job.await()

}



fun example5_2(): Unit = runBlocking {

    // 예외를 출력하고 종료됨
    val job = CoroutineScope(Dispatchers.Default).launch {
        throw IllegalArgumentException()
    }

    delay(1_000L)

}



fun example5_1(): Unit = runBlocking {

    // 새로운 영역(root corutine으로 생성), 메인스레드가 아닌 스레드에서
    val job1 = CoroutineScope(Dispatchers.Default).launch {
        delay(1_000L)
        printWithThread("Job 1")
    }

    val job2 = CoroutineScope(Dispatchers.Default).launch {
        delay(1_000L)
        printWithThread("Job 2")
    }
}