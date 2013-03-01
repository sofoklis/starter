package com.papasofokli.util

import com.lambdaworks.crypto.SCryptUtil
import org.scalatest.FunSuite
import org.scalatest.{ WordSpec, BeforeAndAfterAll }
import org.scalatest.matchers.MustMatchers

class AuthenticationTest extends FunSuite {

  test("Authenticate") {
    val password = "aaaaaaaaaaaaaaaa"
    val hashed = Authentication.encrypt(password)
    assert(Authentication.check(password, hashed))
  }
}