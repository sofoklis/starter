package com.papasofokli.util.security

import com.lambdaworks.crypto.SCryptUtil

object Authentication {
  // Higher values make the password encryption more secure
  val n = 16 //512
  val r = 8 //128
  val p = 8 //128

  def encrypt(passwd: String) = SCryptUtil.scrypt(passwd, n, r, p)

  def check(passwd: String, hashed: String) = SCryptUtil.check(passwd, hashed)

}