(function () {

  'use strict';

  angular
    .module('app')
    .controller('NavCtrl', NavCtrl);

  NavCtrl.$inject = [
    '$scope',
    '$filter'
  ];

  function NavCtrl($scope, $filter) {
    $scope.DataNav = {
      MainNavList: [
        {id: 0, title: $filter('translate')('menu_dashboard'), href: '#/', name: 'dashboard'},
        {id: 1, title: $filter('translate')('menu_timesheet'), href: '#/timesheet', name: 'timesheet'},
        {id: 2, title: $filter('translate')('menu_projects'), href: '#/projects', name: 'projects'},
        {id: 3, title: $filter('translate')('menu_reports'), href: '#/reports', name: 'reports'}
      ]
    };
  }




})();
