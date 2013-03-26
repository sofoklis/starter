package com.papasofokli.model.shipmanagement
import org.squeryl.PrimitiveTypeMode._
import org.squeryl._
import com.papasofokli.model.common.Person
import com.papasofokli.model.common.CommonSchema

class ImportSampleData {

  def addSeaman(implicit schema: ShipmanagementSchema) {

    lazy val persons = Person.getAll
    lazy val ranks = Rank.getAll.toVector
    val numberOfRanks = ranks.size

    // create seamen from the person table and give random ranks
    persons.foreach(p => {
      val r = ranks((math.random * numberOfRanks).toInt)
      schema.seaman.insert(Seaman(p.id, r.id))
    })

  }

  def addRanks(implicit schema: ShipmanagementSchema) {

    val top4 = schema.rankGroup.insert(RankGroup("Top 4"))
    val officers = schema.rankGroup.insert(RankGroup("Officers"))
    val deck = schema.rankGroup.insert(RankGroup("Deck"))
    val engine = schema.rankGroup.insert(RankGroup("Engine"))
    val ratings = schema.rankGroup.insert(RankGroup("Ratings"))
    val cadets = schema.rankGroup.insert(RankGroup("Cadets"))
    val all = schema.rankGroup.insert(RankGroup("All"))

    // Deck
    var r = schema.rank.insert(Rank("Captain"))
    all.ranks(schema).associate(r)
    officers.ranks(schema).associate(r)
    deck.ranks(schema).associate(r)
    top4.ranks(schema).associate(r)

    r = schema.rank.insert(Rank("Chief Officer"))
    all.ranks(schema).associate(r)
    officers.ranks(schema).associate(r)
    deck.ranks(schema).associate(r)
    top4.ranks(schema).associate(r)

    r = schema.rank.insert(Rank("2nd Officer"))
    all.ranks(schema).associate(r)
    officers.ranks(schema).associate(r)
    deck.ranks(schema).associate(r)

    r = schema.rank.insert(Rank("3rd Officer"))
    all.ranks(schema).associate(r)
    officers.ranks(schema).associate(r)
    deck.ranks(schema).associate(r)

    r = schema.rank.insert(Rank("Deck Cadet"))
    all.ranks(schema).associate(r)
    cadets.ranks(schema).associate(r)
    deck.ranks(schema).associate(r)

    r = schema.rank.insert(Rank("Boatswain"))
    all.ranks(schema).associate(r)
    ratings.ranks(schema).associate(r)
    deck.ranks(schema).associate(r)

    r = schema.rank.insert(Rank("Able Seaman"))
    all.ranks(schema).associate(r)
    ratings.ranks(schema).associate(r)
    deck.ranks(schema).associate(r)

    r = schema.rank.insert(Rank("Ordinary Seaman"))
    all.ranks(schema).associate(r)
    ratings.ranks(schema).associate(r)
    deck.ranks(schema).associate(r)

    // Engine
    r = schema.rank.insert(Rank("Chief Engineer"))
    all.ranks(schema).associate(r)
    officers.ranks(schema).associate(r)
    engine.ranks(schema).associate(r)
    top4.ranks(schema).associate(r)

    r = schema.rank.insert(Rank("2nd Engineer"))
    all.ranks(schema).associate(r)
    officers.ranks(schema).associate(r)
    engine.ranks(schema).associate(r)
    top4.ranks(schema).associate(r)

    r = schema.rank.insert(Rank("3rd Engineer"))
    all.ranks(schema).associate(r)
    officers.ranks(schema).associate(r)
    engine.ranks(schema).associate(r)

    r = schema.rank.insert(Rank("4th Engineer"))
    all.ranks(schema).associate(r)
    officers.ranks(schema).associate(r)
    engine.ranks(schema).associate(r)

    r = schema.rank.insert(Rank("Engineering Cadet"))
    all.ranks(schema).associate(r)
    engine.ranks(schema).associate(r)
    cadets.ranks(schema).associate(r)

    r = schema.rank.insert(Rank("Oiler"))
    all.ranks(schema).associate(r)
    engine.ranks(schema).associate(r)
    ratings.ranks(schema).associate(r)

    r = schema.rank.insert(Rank("Pumpman"))
    all.ranks(schema).associate(r)
    engine.ranks(schema).associate(r)
    ratings.ranks(schema).associate(r)

    r = schema.rank.insert(Rank("Electrician"))
    all.ranks(schema).associate(r)
    engine.ranks(schema).associate(r)
    ratings.ranks(schema).associate(r)

    r = schema.rank.insert(Rank("Wiper"))
    all.ranks(schema).associate(r)
    engine.ranks(schema).associate(r)
    ratings.ranks(schema).associate(r)

    // Steward
    r = schema.rank.insert(Rank("Chief Steward"))
    all.ranks(schema).associate(r)
    ratings.ranks(schema).associate(r)

    r = schema.rank.insert(Rank("Chief Cook"))
    all.ranks(schema).associate(r)
    ratings.ranks(schema).associate(r)

    r = schema.rank.insert(Rank("Steward"))
    all.ranks(schema).associate(r)
    ratings.ranks(schema).associate(r)

    r = schema.rank.insert(Rank("Cook"))
    all.ranks(schema).associate(r)
    ratings.ranks(schema).associate(r)
  }
}