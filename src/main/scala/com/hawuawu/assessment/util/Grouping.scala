package com.hawuawu.assessment.util

import java.lang.Math.{max, min}

import com.hawuawu.assessment.model.{AddressData, Group}


case class FoldIteration(groups: List[Group] = List())

class NotMergeAbleGroups(message: String) extends Exception(message) {

}

/*
  This is trait supplies detection of overlapping.
 */
trait Grouping {

  /*
  Method for overlapping detection

   How this algorithm was changed:
   - Removed indexing, because there is no requirement for indexing whatsoever, instead of that, I'll create indexes at
   the output part.
   - Instead of searching customer, creating group for customer right away.
   - Instead of method find, using method filter, so this code filters overlapping groups.
   - It merges filtered groups and creates new fold iteration.
   */
  def createGroupsWhichOverlaps(occupancyData: List[AddressData]): List[Group] = {
    occupancyData.foldLeft(FoldIteration())((previousIteration, element) => {
      val groupFromCustomer = customerToGroup(element)

      val allOverlappedGroups = previousIteration
        .groups
        .filter(group => group.addressId == groupFromCustomer.addressId && areGroupsOverlapping(group, groupFromCustomer))

      FoldIteration (mergeGroups(allOverlappedGroups, groupFromCustomer)
        :: previousIteration.groups.filterNot(group => allOverlappedGroups.contains(group)))

    }).groups.filterNot(f => f.numberOfCustomers == 1)
  }

  /*
  Converts address line to group.
   */
  def customerToGroup(element: AddressData): Group
    = Group(element.addressId, 1, element.fromDate, element.toDate)

  /*
  It iterates over groups and merge them together.
  If there is no filtered group, it returns elementGroup.
   */
  def mergeGroups(groups: List[Group], elementGroup: Group)
    = groups.foldLeft(elementGroup)((totalGroup, currentGroup) => mergeGroup(currentGroup, totalGroup))

  /*
    Merges groups and throws exception, when method is called with bad arguments (not mergeable groups)
   */
  def mergeGroup(group1: Group, group2: Group): Group = if(group1.addressId == group2.addressId) {
    group1.copy(
      group1.addressId,
      group1.numberOfCustomers + group2.numberOfCustomers,
      min(group1.fromDate, group2.fromDate),
      max(group1.toDate, group2.toDate),
    )
  } else {
    throw new NotMergeAbleGroups(s"Group - $group1 and group - $group2 cannot be merged")
  }


/*
  This methods checks if date intervals overlaps, fixed including endpoinds, because from definition of assessment,
  overlapping endpoints mean same time.
 */
  def areGroupsOverlapping(group1: Group, group2: Group): Boolean = //Changed comparations to include endpoints of intervals
    (group2.fromDate >= group1.fromDate && group2.fromDate <= group1.toDate) ||
      (group2.toDate >= group1.fromDate && group2.toDate <= group1.toDate) ||
      (group2.fromDate <= group1.fromDate && group2.toDate >= group1.toDate)
}
