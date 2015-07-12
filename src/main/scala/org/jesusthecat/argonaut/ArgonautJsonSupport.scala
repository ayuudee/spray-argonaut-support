package org.jesusthecat.argonaut

import argonaut._
import spray.http.{ ContentTypes, ContentTypeRange, HttpCharsets, HttpEntity, MediaTypes }
import spray.httpx.marshalling.Marshaller
import spray.httpx.unmarshalling.{ Deserialized, MalformedContent, SimpleUnmarshaller, Unmarshaller }
import MediaTypes._

trait ArgonautJsonSupport {

  // --------------------------------------------------------------------------
  // Marshaller.

  implicit def argonautJsonMarshaller[A : EncodeJson]: Marshaller[A] = {
    import Argonaut._
    Marshaller.of[A](`application/json`) { (value, contentType, ctx) =>
      val enc = implicitly[EncodeJson[A]]
      ctx.marshalTo(HttpEntity(contentType, value.asJson.spaces2))
    }
  }

  // --------------------------------------------------------------------------
  // Unmarshaller.

  implicit def argonautJsonUnmarshaller[A : DecodeJson]: Unmarshaller[A] = {
    val dec = implicitly[DecodeJson[A]]
    new SimpleUnmarshaller[A] {
      override val canUnmarshalFrom = ContentTypeRange(`application/json`) :: Nil
      override def unmarshal(entity: HttpEntity) = {
        UTF8StringUnmarshaller(entity).right.flatMap { str =>
          Parse.decodeEither(str).leftMap(e => MalformedContent(e)).toEither
        }
      }
    }

  }

  private val UTF8StringUnmarshaller = new Unmarshaller[String] {
    def apply(entity: HttpEntity) = Right(entity.asString(defaultCharset = HttpCharsets.`UTF-8`))
  }

}

object ArgonautJsonSupport extends ArgonautJsonSupport
