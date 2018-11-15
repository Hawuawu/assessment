package com.hawuawu.assessment

import com.hawuawu.assessment.util.{File, Grouping}
import org.scalatest.FlatSpec

class AssessmentTest extends FlatSpec with Grouping with File {
 "Assessment" should "create groups in proper way" in {

    val fileName = "example.csv"
    val occupancyData = loadAddressesFromResource(fileName)
    val groups = createGroups(occupancyData)

   println(groups)
    assert(groups.size == 3)

   assert(
     groups
       .exists(g => g.fromDate == 5 & g.toDate == 12 & g.addressId == "ADR001" && g.numberOfCustomers == 3)
   )

   assert(
     groups
       .exists(g => g.fromDate == 1 & g.toDate == 5 & g.addressId == "ADR001" & g.numberOfCustomers == 2)
   )

   assert(
     groups
       .exists(g => g.fromDate == 7 & g.toDate == 11 & g.addressId == "ADR003" & g.numberOfCustomers == 2)
   )
  }
}
