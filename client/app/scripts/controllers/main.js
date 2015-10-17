'use strict';

/**
 * @ngdoc function
 * @name timeTrackerApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the timeTrackerApp
 */
angular.module('timeTrackerApp')
  .controller('MainCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
