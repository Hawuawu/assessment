package com.hawuawu.assessment

import com.hawuawu.assessment.util.{File, Grouping}

case class Group(groupdId: String, addressId: String, numberOfCustomers: Int, fromDate: Int, toDate: Int)
case class AddressData( customerId: String, addressId: String, fromDate: Int, toDate: Int)

object Assessment extends Grouping with File {
  def main(args: Array[String]) =  {
    val fileName = "address_data.csv"
    val occupancyData = loadAddressesFromResource(fileName)
    val groups = createGroups(occupancyData)
    println(groups.sortBy(_.groupdId.replace("Group", "").toInt))
  }
}
