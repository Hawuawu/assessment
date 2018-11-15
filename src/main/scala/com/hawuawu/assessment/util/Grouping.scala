package com.hawuawu.assessment.util

import java.lang.Math.{max, min}

import com.hawuawu.assessment.model.{AddressData, Group}

case class FoldIteration(groupIndex: Int = 1, groups: List[Group] = List())

trait Grouping {
  def createGroups(occupancyData: List[AddressData]): List[Group] =
    occupancyData.foldLeft(FoldIteration())((previousIteration, element) => {
      val found = previousIteration.groups.find(g => g.addressId == element.addressId && isOverlappingGroup(g, element))
      if(found.isDefined) {
        val group = found.get
        val minMax = minFromDateMaxToDate(group, element)
        if(group.numberOfCustomers == 1) {
          FoldIteration (
            previousIteration.groupIndex + 1,
            group.copy(
              groupdId = s"Group${previousIteration.groupIndex}",
              numberOfCustomers = group.numberOfCustomers + 1,
              fromDate = minMax._1,
              toDate = minMax._2
            ) :: previousIteration.groups.filterNot(g => g == group))
        } else {
          FoldIteration (
            previousIteration.groupIndex,
            group.copy(
              numberOfCustomers = group.numberOfCustomers + 1,
              fromDate = minMax._1,
              toDate = minMax._2
            ) ::  previousIteration.groups.filterNot(g => g == group)
          )
        }
      } else {
        FoldIteration (
          previousIteration.groupIndex,
          Group(
            "",
            element.addressId,
            1,
            element.fromDate,
            element.toDate
          ) :: previousIteration.groups
        )
      }
    }).groups.filterNot(f => f.groupdId == "")

  def minFromDateMaxToDate(group: Group, element: AddressData): (Int, Int)
  = (min(group.fromDate, element.fromDate), max(group.toDate, element.toDate))

  def isOverlappingGroup(group: Group, customer: AddressData): Boolean =
    (customer.fromDate >= group.fromDate && customer.fromDate < group.toDate) ||
      (customer.toDate > group.fromDate && customer.toDate <= group.toDate) ||
      (customer.fromDate < group.fromDate && customer.toDate > group.toDate)
}
