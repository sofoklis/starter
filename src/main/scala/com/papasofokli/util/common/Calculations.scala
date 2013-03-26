package com.papasofokli.util.common
import java.util.Date
import java.util.Calendar

object Calculations {

  def calculateAge(date: Date): Int = {
    val cal = Calendar.getInstance
    val thisYear = cal.get(Calendar.YEAR)
    val thisMonth = cal.get(Calendar.MONTH)
    val thisDay = cal.get(Calendar.DAY_OF_MONTH)

    cal.setTime(date)
    val year = cal.get(Calendar.YEAR)
    val month = cal.get(Calendar.MONTH)
    val day = cal.get(Calendar.DAY_OF_MONTH)

    thisYear - year - 1 + (
      if (thisMonth > month || (thisMonth == month && thisDay >= day))
        1
      else 0)
  }

  def aniversary(date: Date): Boolean =
    {
      val cal = Calendar.getInstance
      val thisYear = cal.get(Calendar.YEAR)
      val thisMonth = cal.get(Calendar.MONTH)
      val thisDay = cal.get(Calendar.DAY_OF_MONTH)

      cal.setTime(date)
      val year = cal.get(Calendar.YEAR)
      val month = cal.get(Calendar.MONTH)
      val day = cal.get(Calendar.DAY_OF_MONTH)

      if (month == thisMonth && day == thisDay) true else false
    }

}