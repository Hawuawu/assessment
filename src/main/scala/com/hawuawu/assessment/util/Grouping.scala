package com.hawuawu.assessment.util

import java.lang.Math.{max, min}

import com.hawuawu.assessment.model.{AddressData, Group}

trait Grouping {
  def createGroups(occupancyData: List[AddressData]) =
    occupancyData.foldLeft(1, List[Group]())((previous, element) => {
      val found = previous._2.find(g => g.addressId == element.addressId && isOverlappingGroup(g, element))
      if(found.isDefined) {
        val group = found.get
        val minMax = minFromDateMaxToDate(group, element)
        if(group.numberOfCustomers == 1) {
          (
            previous._1 + 1,
            group.copy(
              groupdId = s"Group${previous._1}",
              numberOfCustomers = group.numberOfCustomers + 1,
              fromDate = minMax._1,
              toDate = minMax._2
            ) :: previous._2.filterNot(g => g == group))
        } else {
          (
            previous._1,
            group.copy(
              numberOfCustomers = group.numberOfCustomers + 1,
              fromDate = minMax._1,
              toDate = minMax._2
            ) ::  previous._2.filterNot(g => g == group)
          )
        }
      } else {
        (
          previous._1,
          Group(
            "",
            element.addressId,
            1,
            element.fromDate,
            element.toDate
          ) :: previous._2
        )
      }
    })._2.filterNot(f => f.groupdId == "")

  def minFromDateMaxToDate(group: Group, element: AddressData): (Int, Int)
  = (min(group.fromDate, element.fromDate), max(group.toDate, element.toDate))

  def isOverlappingGroup(group: Group, customer: AddressData): Boolean =
    (customer.fromDate >= group.fromDate && customer.fromDate < group.toDate) ||
      (customer.toDate > group.fromDate && customer.toDate <= group.toDate) ||
      (customer.fromDate < group.fromDate && customer.toDate > group.toDate)
}
