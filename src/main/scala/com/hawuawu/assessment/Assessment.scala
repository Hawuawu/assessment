package com.hawuawu.assessment

import com.hawuawu.assessment.util.{FileUtils, Grouping}

object Assessment extends Grouping with FileUtils {
  def main(args: Array[String]) =  {
    val fileName = "address_data.csv"
    val occupancyData = loadAddressesFromResource(fileName)
    val groups = createGroups(occupancyData)
    println(s"There is ${groups.size} groups.")
    groups.sortBy(_.groupdId.replace("Group", "").toInt)
      .foreach(group =>
        println(
          s"GroupId: ${group.groupdId} | " +
            s"address: ${group.addressId} | " +
            s"from: ${group.fromDate} | " +
            s"to: ${group.toDate} | " +
            s"customers: ${group.numberOfCustomers}"
        )
      )
  }
}
