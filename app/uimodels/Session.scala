package uimodels

case class SessionId(id: String)
case class UserId(id:String)

class Session(val id: SessionId)
class UserSession(id:SessionId, userId:UserId) extends Session(id)
