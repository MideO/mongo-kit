package com.example.app

import com.example.app.models._

class CardServlet extends ExamplescalatraStack {

  get("/") {
    Message("Hello, world!")
  }
}
