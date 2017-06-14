package com.github.mideo.mongo
import play.api.libs.json.{JsObject, JsPath, Json, JsonValidationError}

object Errors {
  def showJsonErrors(errors: Seq[(JsPath, Seq[JsonValidationError])]): JsObject = {
    Json.obj(
      "Error" -> errors.map {
                    case (path, e) => path.toString() + " : " + e.map(_.toString).mkString(" ")
                }.mkString("\n")
    )
  }

}
