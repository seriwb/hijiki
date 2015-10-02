package controllers

import play.api._
import play.api.mvc._
import play.api.libs.ws._
import play.api.libs.json._
import scala.concurrent.Future
import play.mvc.Http.Response
import javax.inject.Inject

class Application @Inject() (ws: WSClient) extends Controller {

  def index = Action {
    Ok(views.html.index("ひじき祭会場", "sm27007347"))
  }

  def about = Action {
    Ok(views.html.about("ひじき祭会場"))
  }

  implicit val context = play.api.libs.concurrent.Execution.Implicits.defaultContext
  def ranking = Action {
//    val futureInt = scala.concurrent.Future { 
    val request: WSRequest = ws.url("http://api.search.nicovideo.jp/api/snapshot/")
    val complexRequest: WSRequest =
      request.withHeaders("Content-Type" -> "application/json")
        .withRequestTimeout(10000)
    val data = Json.obj(
      "query" -> "ひじき祭",
      "service" -> Json.arr("video"),
      "search" -> Json.arr("title", "description", "tags"),
      "join" -> Json.arr(
          "cmsid", "title", "description", "tags", "start_time", "thumbnail_url",
          "view_counter", "comment_counter", "mylist_counter", "last_res_body", "length_seconds"
      ),
      "issuer" -> "ひじき祭会場"
    )
    val futureResponse: Future[WSResponse] = complexRequest.post(data)

    val futureResult: Future[String] = futureResponse.map {
      response =>
//        ("Feed title: " + (response.json \ "title").as[String])
       response.json.toString()
    }
    futureResult onSuccess {
      case msg => println(msg)
    }
    futureResult onFailure {
      case msg => println(msg)
    }
    Ok(views.html.ranking("Feed title:"))
//    }.map(i => Ok(views.html.about("ひじき祭会場")))
//      (response.json \ "person" \ "name").as[String]
//        (response.json \ "status").as[String] match {
//          case "OK" => Ok(views.html.ranking("ひじき祭会場"))
//          case "NG" => BadRequest(views.html.ranking("ひじき祭会場"))
//        }
//    }
//      .post(Json.toJson(Map("name" -> "Takako"))).map { response =>
//
//        (response.json \ "status").as[String] match {
//          case "OK" => Ok(views.html.ranking("ひじき祭会場"))
//          case "NG" => BadRequest(views.html.ranking("ひじき祭会場"))
//        }
//    }
      }

}
