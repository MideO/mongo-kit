import com.example.app._
import org.scalatra._
import javax.servlet.ServletContext

import com.example.app.db.CardRepo


class ScalatraBootstrap extends LifeCycle {

  def initConf(context: ServletContext): Unit = {
    context.initParameters("org.scalatra.environment") = AppConfig.config.getString("app.environment")
  }


  override def init(context: ServletContext) {
    val cardRepo:CardRepo = AppConfig.injector.getInstance(classOf[CardRepo])
    context.mount(new CardServlet(cardRepo), "/*")
  }
}
