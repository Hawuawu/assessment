package com.hawuawu.assessment.util

import com.hawuawu.assessment.model.AddressData

import scala.io.Source

/*
  This is trait supplies loading of address lines from file.
 */
trait FileUtils {
  def loadAddressesFromResource(resource: String): List[AddressData] =  {
    val addressLines = Source.fromResource(resource).getLines().drop(1)
    addressLines.map({ line =>
      val split = line.split(',')
      AddressData(split(0), split(1), split(2).toInt, split(3).toInt)
    }).toList.sortBy(r => (r.addressId, r.customerId))
  }
}
