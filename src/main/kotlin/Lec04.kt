import kotlinx.coroutines.*
import java.util.concurrent.ThreadPoolExecutor

fun main(): Unit = runBlocking {
    val job = launch {
        try{
            delay(1_000L)
        }catch (e: CancellationException){
            // 아무것도 안한다!
//            throw e
        }finally {
            // 다른 작업 가능 
        }
        printWithThread("delay에 의해 취소되지 않았다!")
    }
    delay(100L)
    printWithThread("취소 시작!")
    job.cancel()
}

fun example4_2(): Unit = runBlocking {
    // 다른 스레드에서 동작함 -> 취소시키기 위함
    val job = launch(Dispatchers.Default) {
        var i = 1
        var nextPrintTime = System.currentTimeMillis()

        while( i <= 5 ) {
            if( nextPrintTime <= System.currentTimeMillis() ){
                printWithThread("${i++}번째 출력!")
                nextPrintTime += 1_000L
            }

            if(!isActive){
                throw CancellationException()
            }
        }
    }
    delay(100L)
    job.cancel()
}

// 취소협조 1. suspend 메소드 사용
fun example4_1(): Unit = runBlocking {

    val job1 = launch {
        delay(1_000L)
        printWithThread("Job 1")
    }

    val job2 = launch {
        delay(1_000L)
        printWithThread("Job 2")
    }

    delay(100)
    job1.cancel()
}