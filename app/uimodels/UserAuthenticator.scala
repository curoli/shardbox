package uimodels

object UserAuthenticator {
  
  val mockUsers = Set("a", "b", "c")
  
  def authenticate(creds:UserCredentials) : Boolean = {
    val userId = creds.userId
    mockUsers.contains(userId) && userId == creds.password
  }

}