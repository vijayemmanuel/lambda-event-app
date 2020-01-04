package app

case class ShoppingItem(orderDate: String, item: String)

object ApiModels {
  case class Req(item: String)
  case class Resp(message: ShoppingItem)

}
