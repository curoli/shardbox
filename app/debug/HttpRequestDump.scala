package debug

import play.api.mvc.Request
import play.api.libs.json.Json
import play.api.libs.json.Writes
import play.api.mvc.Session
import play.api.mvc.Flash
import play.api.i18n.Lang
import play.api.http.MediaType
import play.api.mvc.Headers

object HttpRequestDump {

  implicit val sessionWrites = new Writes[Session] {
    def writes(session: Session) = Json.obj(
      "data" -> session.data)
  }

  implicit val flashWrites = new Writes[Flash] {
    def writes(flash: Flash) = Json.obj(
      "data" -> flash.data)
  }

  implicit val headersWrites = new Writes[Headers] {
    def writes(headers: Headers) = Json.obj(
      "toMap" -> headers.toMap)
  }

  implicit val langWrites = new Writes[Lang] {
    def writes(lang: Lang) = Json.obj(
      "language" -> lang.language,
      "country" -> lang.country,
      "code" -> lang.code)
  }

  implicit val mediaTypeWrites = new Writes[MediaType] {
    def writes(mediaType: MediaType) = Json.obj(
      "mediaType" -> mediaType.mediaType,
      "mediaSubType" -> mediaType.mediaSubType,
      "parameters" -> ("" + mediaType.parameters)) // TODO
  }

  implicit val requestWrites = new Writes[Request[_]] {
    def writes(request: Request[_]) = Json.obj(
      "toString" -> request.toString,
      "version" -> request.version,
      "session" -> request.session,
      "domain" -> request.domain,
      "acceptedTypes" -> request.acceptedTypes,
      "acceptLanguages" -> request.acceptLanguages,
      "host" -> request.host,
      "method" -> request.method,
      "query string" -> request.queryString,
      "id" -> request.id,
      "tags" -> request.tags,
      "remoteAddress" -> request.remoteAddress,
      "charset" -> request.charset,
      "path" -> request.path,
      "uri" -> request.uri,
      "contentType" -> request.contentType,
      "cookies" -> ("" + request.cookies),
      "rawQueryString" -> request.rawQueryString,
      "flash" -> request.flash,
      "headers" -> request.headers,
      "mediaType" -> request.mediaType)
  }

  def dump(request: Request[_]): String = {
    val requestJson = Json.toJson(request)
    Json.prettyPrint(requestJson)
  }

}