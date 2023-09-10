import kotlinx.coroutines.*
import java.util.concurrent.Executors

fun main(){
    // 여러 element 조합
    CoroutineName("나만의 코루틴") + Dispatchers.Default

    val threadPool = Executors.newSingleThreadExecutor()
    // 해당 스레드풀 내 여러 코루틴 돌릴 수 있다
    CoroutineScope(threadPool.asCoroutineDispatcher()).launch {
        printWithThread("새로운 코루틴")
    }
}


suspend fun example7_1(){

    // CorutineScope = CorutineContext 보관하는 역할
    val job = CoroutineScope(Dispatchers.Default).launch {
        delay(1_000L)
        printWithThread("Job 1")
        coroutineContext + CoroutineName("이름")
        coroutineContext.minusKey(CoroutineName.Key)
    }

    
    // 위 스레드 종료 기다려야함
    job.join()
//    Thread.sleep(1_500L)

}

class AsyncLogic {
    private val scope = CoroutineScope(Dispatchers.Default)

    fun doSomething() {
        scope.launch {
            // 무언가 코루틴이 시작되어 작업!
        }
    }

    fun destroy() {
        scope.cancel()
    }
}