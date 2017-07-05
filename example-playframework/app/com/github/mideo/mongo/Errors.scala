package com.github.mideo.mongo
import play.api.data.validation.ValidationError
import play.api.libs.json.{JsObject, JsPath, Json}

object Errors {
  def showJsonErrors(errors: Seq[(JsPath, Seq[ValidationError])]): JsObject = {
    Json.obj(
      "Error" -> errors.map {
                    case (path, e) => path.toString() + " : " + e.map(_.toString).mkString(" ")
                }.mkString("\n")
    )
  }

}
