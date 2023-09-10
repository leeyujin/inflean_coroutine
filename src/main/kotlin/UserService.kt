import kotlinx.coroutines.delay

//코루틴이 중단, 재개 되는 원리
suspend fun main() {
  val service = UserService()
  println(service.findUser(1L,null))
}

interface Continuation {
  suspend fun resumeWith(data: Any?)
}

class UserService {

  private val userProfileRepository = UserProfileRepositoryV2()
  private val userImageRepository = UserImageRepositoryV2()

  private abstract class FindUserContinuation() : Continuation {
    var label = 0
    var profile: Profile? = null
    var image: Image? = null
  }

  suspend fun findUser(userId: Long, countinuation: Continuation?): UserDto {

    val sm = countinuation as? FindUserContinuation ?: object : FindUserContinuation() {
      override suspend fun resumeWith(data: Any?) {
        when (label) {
          0 -> {
            profile = data as Profile
            label = 1
          }
          1 -> {
            image = data as Image
            label = 2
          }
        }
        findUser(userId, this)
      }
    }

    when(sm.label){
      0 -> {
        // 0단계 - 초기시작
        println("유저를 가져오겠습니다")
        userProfileRepository.findProfile(userId, sm)

      }
      1 -> {
        // 1단계 - 1차 중단 후 재시작
        println("이미지를 가져오겠습니다")
        userImageRepository.findImage(sm.profile!!, sm)
      }

    }

    // 2단계 - 2차 중단 후 재시작
    return UserDto(sm.profile!!, sm.image!!)
  }

}

data class UserDto(
  val profile: Profile,
  val image: Image,
)


class UserProfileRepositoryV2 {
  suspend fun findProfile(userId: Long, countinuation: Continuation) {
    delay(100L)
    countinuation.resumeWith(Profile())
  }
}


class UserImageRepositoryV2 {
  suspend fun findImage(profile: Profile, countinuation: Continuation){
    delay(100L)
    countinuation.resumeWith(Image())


  }
}
class Profile
class Image
