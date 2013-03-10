package com.papasofokli.util.security

import org.scalatest.FunSuite

class AuthenticationTest extends FunSuite {

  test("Authenticate") {
    passcheck("aaaaaaaaaaaaaaaaa")
    passcheck("password24")
    passcheck("password")
  }

  test("Failed Authenticate") {
    val password = "sdfsdfdsfds"
    val hashed = Authentication.encrypt(password)
    assert(Authentication.check(password + 3, hashed) === false)

  }

  def passcheck(password: String) {
    val hashed = Authentication.encrypt(password)
    assert(Authentication.check(password, hashed) === true)
  }

}