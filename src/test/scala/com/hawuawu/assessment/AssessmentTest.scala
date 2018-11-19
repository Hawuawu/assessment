package com.hawuawu.assessment

import com.hawuawu.assessment.util.{FileUtils, Grouping}
import org.scalatest.FlatSpec

class AssessmentTest extends FlatSpec with Grouping with FileUtils {
  "Assessment" should "create groups in proper way" in {

    val fileName = "example.csv"
    val occupancyData = loadAddressesFromResource(fileName)
    val groups = createGroupsWhichOverlaps(occupancyData)

    assert(groups.size == 3)

    assert(
      groups
        .exists(g => g.fromDate == 1 & g.toDate == 4 & g.addressId == "ADR001" && g.numberOfCustomers == 2)
    )

    assert(
      groups
        .exists(g => g.fromDate == 5 & g.toDate == 11 & g.addressId == "ADR001" && g.numberOfCustomers == 3)
    )

    assert(
      groups
        .exists(g => g.fromDate == 5 & g.toDate == 11 & g.addressId == "ADR003" && g.numberOfCustomers == 2)
    )
  }

  "FIX: Assessment" should "merge groups if customer overlaps more values" in {

    val fileName = "example2.csv"
    val occupancyData = loadAddressesFromResource(fileName)
    val groups = createGroupsWhichOverlaps(occupancyData)

    assert(groups.size == 3)

    assert(
      groups
        .exists(g => g.fromDate == 3 & g.toDate == 11 & g.addressId == "ADR001" && g.numberOfCustomers == 5)
    )

    assert(
      groups
        .exists(g => g.fromDate == 1 & g.toDate == 9 & g.addressId == "ADR002" & g.numberOfCustomers == 3)
    )

    assert(
      groups
        .exists(g => g.fromDate == 5 & g.toDate == 11 & g.addressId == "ADR003" & g.numberOfCustomers == 3)
    )
  }

  "FIX: Assessment" should "include interval enpoints in overlapping" in {

    val fileName = "example3.csv"
    val occupancyData = loadAddressesFromResource(fileName)
    val groups = createGroupsWhichOverlaps(occupancyData)

    assert(groups.size == 3)

    assert(
      groups
        .exists(g => g.fromDate == 1 & g.toDate == 4 & g.addressId == "ADR001" && g.numberOfCustomers == 2)
    )

    assert(
      groups
        .exists(g => g.fromDate == 1 & g.toDate == 4 & g.addressId == "ADR002" & g.numberOfCustomers == 2)
    )

    assert(
      groups
        .exists(g => g.fromDate == 2 & g.toDate == 3 & g.addressId == "ADR003" & g.numberOfCustomers == 2)
    )
  }

}
