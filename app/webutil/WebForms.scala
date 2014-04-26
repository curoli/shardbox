package webutil

import play.api.data._
import play.api.data.Forms._
import uimodels.UserCredentials

object WebForms {
  
  val login = Form(
      mapping(
          "user" -> nonEmptyText,
          "password" -> nonEmptyText
          )(UserCredentials.apply)(UserCredentials.unapply)
      )

}