package com.example.app

import org.scalatra.test.scalatest._

// For more on ScalaTest, see http://www.scalatest.org/quick_start
class CardServletSpec extends ScalatraSpec {
  addServlet(classOf[CardServlet], "/*")

  describe("GET / on CardServlet") {
    it("should return status 200") {
      get("/") {
        status should be (200)
      }
    }
  }
}
