package org.jesusthecat.argonaut

import argonaut._, Argonaut._
import org.scalatest.{FlatSpec, Matchers}
import spray.http.{ ContentTypes, ContentTypeRange, HttpCharsets, HttpEntity, MediaTypes }
import spray.httpx.unmarshalling.{ Deserialized, MalformedContent, SimpleUnmarshaller, Unmarshaller }
import spray.httpx.marshalling.{ CollectingMarshallingContext, Marshaller }

class ArgonautJsonSupportSpec extends FlatSpec with Matchers {

  "ArgonautJsonSupport" should "marshall to JSON via Argonaut" in {
    import ArgonautJsonSupport._
    implicit val fc = FooCodec

    val foo = Foo("id", 123)
    val marshaller: Marshaller[Foo] = implicitly[Marshaller[Foo]]
    val ctx = new CollectingMarshallingContext
    marshaller(foo, ctx)

    ctx.error should be(None)
    val json = ctx.entity.get.asString
    json.decodeOption[Foo] should be(Some(foo))
  }

  "ArgonautJsonSupport" should "unmarshall from JSON via Argonaut" in {
    import ArgonautJsonSupport._
    implicit val fc = FooCodec

    val foo = Foo("id", 123)
    val json = foo.asJson.spaces2

    val unmarshaller: Unmarshaller[Foo] = implicitly[Unmarshaller[Foo]]
    val result = unmarshaller(HttpEntity(ContentTypes.`application/json`, json))
    result should be(Right(foo))
  }

  // ------------------------------------------------------------------------

  case class Foo(id: String, aNum: Int)

  def FooCodec: CodecJson[Foo] = casecodec2(Foo.apply, Foo.unapply)("id", "aNum")

}
