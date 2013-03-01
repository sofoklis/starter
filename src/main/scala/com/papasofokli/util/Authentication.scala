package com.papasofokli.util

import com.lambdaworks.crypto.SCryptUtil

object Authentication {
  // Higher values make the password encryption more secure
  val n = 512
  val r = 128
  val p = 128

  def encrypt(passwd: String) = SCryptUtil.scrypt(passwd, n, r, p)

  def check(passwd: String, hashed: String) = SCryptUtil.check(passwd, hashed)

}