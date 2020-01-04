package app

import io.circe.generic.auto._
import io.github.mkotsur.aws.handler.Lambda._
import io.github.mkotsur.aws.handler.Lambda
import com.amazonaws.services.lambda.runtime.Context
import io.github.mkotsur.aws.proxy
import io.github.mkotsur.aws.proxy.ProxyResponse
import org.joda.time.DateTime
import org.apache.logging.log4j.{LogManager, Logger}
import ApiModels._


class ScalaHandler extends Lambda[Req, Resp] {

  //val logger: Logger = LogManager.getLogger(getClass)

  override def handle(req: Req, context: Context): Either[Throwable, Resp] = {
    Right(Resp(ShoppingItem(DateTime.now().toString(),req.item)))
  }
}

class ApiGatewayScalaHandler extends Proxy[Req, Resp] {

  val logger: Logger = LogManager.getLogger(getClass)

  override def handle(input: proxy.ProxyRequest[Req], c: Context): Either[Throwable, ProxyResponse[Resp]] = {

    // Print info from the context object
    logger.info("Function name: " + c.getFunctionName)
    logger.info("Function body: " + input.body)

    val headers = Map("x-custom-response-header" -> "my custom response header value")
    val responseBodyOption = input.body.map(req => Resp(ShoppingItem(DateTime.now().toString(),req.item)))
    Right(ProxyResponse(200,Some(headers),responseBodyOption))

  }
}



