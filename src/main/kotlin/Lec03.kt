import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main(): Unit = runBlocking {
    // 동기식 코드, job1의 결과값을 job2 파라미터로 사용할때

    val time = measureTimeMillis {
        val job1 = async { apiCall1() }
        val job2 = async { apiCall3(job1.await()) }
        printWithThread(  job2.await())
    }
    printWithThread("소요시간 : $time ms")
}

fun example6(): Unit = runBlocking {

    val time = measureTimeMillis {
        // 앞 job의 계산결과 기다리고 수행됨
        val job1 = async(start = CoroutineStart.LAZY) { apiCall1() }
        val job2 = async(start = CoroutineStart.LAZY) { apiCall2() }
//        val job1 = async { apiCall1() }
//        val job2 = async { apiCall2() }

        // 위 LAZY 안탐 
        job1.start()
        job2.start()

        printWithThread( job1.await() + job2.await())
    }
    printWithThread("소요시간 : $time ms")


}

suspend fun apiCall1(): Int {
    delay(1_000L)
    return 1
}

suspend fun apiCall2(): Int {
    delay(1_000L)
    return 1
}

suspend fun apiCall3(num: Int): Int {
    delay(1_000L)
    return num + 2
}



fun example5(): Unit = runBlocking {
    val job = async {
        3 + 5
    }


    val eight = job.await()
    printWithThread(eight)

}

fun example4(): Unit = runBlocking {
    val job1 = launch {
        delay(1_000)
        printWithThread("Job 1")
    }
    job1.join()

    val job2 = launch {
        delay(1_000)
        printWithThread("Job 2")
    }
}

fun example3(): Unit = runBlocking {
    val job = launch{
        (1..5).forEach{
            printWithThread(it)
            delay(500)
        }
    }
    delay(1_000L)
    // 1,2만 출력됨
    job.cancel()
}

fun example2() : Unit = runBlocking {
    val job = launch(start = CoroutineStart.LAZY) {
        printWithThread("Hello launch")
    }
    delay(1_000L)
    job.start()
}

fun example1() {
    runBlocking {
        printWithThread("START")
        launch {
            delay(2_000L) // cf) yield - 아무것도안하고 양보, 특정시간만큼 멈추고 다른 스레드로 넘김
            printWithThread("LAUNCH END")
        }
    }

    // runBlocking이 끝난 후 호출됨
    printWithThread("END")
}