package com.papasofokli.model.full
import com.papasofokli.model.common.CommonSchema

object ImplicitVal {
  //implicit val sr24Schema: SR24Schema = FullDatabaseSchema
  implicit val commonSchema: CommonSchema = FullDatabaseSchema
  //implicit val dietitianSchema: DietitianSchema = FullDatabaseSchema
}