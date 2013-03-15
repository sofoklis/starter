package com.papasofokli.model.common

import java.util.Date
/**
 * this trait should have all the required information for records in terms of autiting
 * I need to somehow provide a "user" for the updatedBy and createdBy, recVersion should
 * be automatically incremented, when saving a record this version has to be checked
 * and if its not equal to the record we are trying to save we should put an error
 */
trait Record {
  def recVersion: Long
  def createdBy: Long // user Id
  def creationDate: Date
  def updatedBy: Long // user Id
  def updateDate: Date

  def save
  def checkBeforeSave

  def insert

  def delete
}