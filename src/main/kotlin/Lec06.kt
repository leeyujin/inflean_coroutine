import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {


    // A자체가 실행안됨. 예외가 부모로 전파되어, 다른 자식 코루틴에게 취소요청을 보내기 때문
    launch {
        delay(600L)
        printWithThread("A")
    }

    launch {
        delay(500L)
        throw IllegalArgumentException("코루틴 실패!")
    }
}