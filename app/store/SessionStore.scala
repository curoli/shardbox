package store

import play.api.Play.current
import play.api.db.DB
import anorm.SQL
import uimodels.SessionId
import anorm.SqlRow

object SessionStore {

  trait Entry {
    def id: SessionId
    def isAuthenticated: Boolean
    def userIdOpt: Option[String]
  }

  case class RowEntry(val row: SqlRow) extends Entry {
    lazy val id: SessionId = SessionId(row[Long]("idTime"), row[Long]("idRandom"))
    lazy val isAuthenticated: Boolean = row[Boolean]("isAuthenticated")
    lazy val userIdOpt: Option[String] = row.get[String]("userId").toOptionLoggingError
  }

  def insert(id: SessionId): Boolean = insert(id, false)

  def insert(id: SessionId, isAuthenticated: Boolean): Boolean = {
    DB.withConnection("session") { implicit conn =>
      SQL(
        """INSERT INTO Session (idTime, idRandom, isAuthenticated) 
          VALUES ({idTime}, {idRandom}, {isAuthenticated})""").
        on('idTime -> id.time, 'idRandom -> id.random, 'isAuthenticated -> isAuthenticated).
        execute()
    }
  }

  def insert(id: SessionId, isAuthenticated: Boolean, userId: String): Boolean = {
    DB.withConnection("session") { implicit conn =>
      SQL(
        """INSERT INTO Session (idTime, idRandom, isAuthenticated, userId) 
          VALUES ({idTime}, {idRandom}, {isAuthenticated}, {userId})""").
        on('idTime -> id.time, 'idRandom -> id.random, 'isAuthenticated -> isAuthenticated,
          'userId -> userId).
          execute()
    }
  }

  def insert(id: SessionId, isAuthenticated: Boolean, userIdOpt: Option[String]): Boolean = {
    userIdOpt match {
      case Some(userId) => insert(id, isAuthenticated, userId)
      case None => insert(id, isAuthenticated)
    }
  }

  def update(id: SessionId, isAuthenticated: Boolean, userId: String): Int = {
    DB.withConnection("session") { implicit conn =>
      SQL("""UPDATE Session SET isAuthenticated={isAuthenticated}, userId={userId}
          WHERE idTime={idTime} AND idRandom={isRandom}""").
        on('idTime -> id.time, 'idRandom -> id.random, 'isAuthenticated -> isAuthenticated,
          'userId -> userId).
          executeUpdate()
    }
  }

  def update(id: SessionId, isAuthenticated: Boolean): Int = {
    DB.withConnection("session") { implicit conn =>
      SQL("""UPDATE Session SET isAuthenticated={isAuthenticated}, userId={userId}
          WHERE idTime={idTime} AND idRandom={isRandom}""").
        on('idTime -> id.time, 'idRandom -> id.random, 'isAuthenticated -> isAuthenticated).
        executeUpdate()
    }
  }

  def update(id: SessionId, isAuthenticated: Boolean, userIdOpt: Option[String]): Int = {
    userIdOpt match {
      case Some(userId) => update(id, isAuthenticated, userId)
      case None => update(id, isAuthenticated)
    }
  }

  def get(id: SessionId): Option[Entry] = {
    DB.withConnection("session") { implicit conn =>
      SQL("SELECT * FROM Session WHERE idTime={idTime} AND idRandom={idRandom}").
        on('idTime -> id.time, 'idRandom -> id.random)().headOption.map(RowEntry(_))
    }
  }

  def insertOrUpdate(id: SessionId, isAuthenticated: Boolean, userIdOpt: Option[String]): Boolean =
    {
      get(id) match {
        case Some(_) =>
          update(id, isAuthenticated, userIdOpt); true
        case None => insert(id, isAuthenticated, userIdOpt)
      }
    }

}