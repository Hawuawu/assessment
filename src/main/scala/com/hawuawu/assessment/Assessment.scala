package com.hawuawu.assessment

import com.hawuawu.assessment.util.{FileUtils, Grouping}

object Assessment extends Grouping with FileUtils {
  def main(args: Array[String]) =  {
    val fileName = "address_data.csv"
    val occupancyData = loadAddressesFromResource(fileName)
    val groups = createGroupsWhichOverlaps(occupancyData)
    println(s"There is ${groups.size} groups.")

    for((group, index) <- groups.reverse.zipWithIndex) {
      println(
        s"Group${index+1} | ${group.addressId} | ${group.fromDate} | ${group.toDate} | ${group.numberOfCustomers}"
      )
    }
  }
}
