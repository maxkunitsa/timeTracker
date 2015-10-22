(function () {

  'use strict';

  angular
    .module('app')
    .controller('TimeSheetCtrl', TimeSheetCtrl);

  TimeSheetCtrl.$inject = [
    '$scope'
  ];

  function TimeSheetCtrl($scope) {
    $scope.PageData = {
      DayData: [
        {id: 0, dayName: 'M', dayTime: '0:00'},
        {id: 1, dayName: 'T', dayTime: '0:35'},
        {id: 2, dayName: 'W', dayTime: '0:10'},
        {id: 3, dayName: 'Th', dayTime: '0:00'},
        {id: 4, dayName: 'F', dayTime: '4:00'},
        {id: 5, dayName: 'S', dayTime: '0:00'},
        {id: 6, dayName: 'Su', dayTime: '0:00'}
      ],
      activeDay: 3
    }
  }




})();
