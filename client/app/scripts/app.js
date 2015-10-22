(function () {

  'use strict';

  angular
    .module('app', [
      'ngAnimate',
      'ngCookies',
      'ngResource',
      'ngRoute',
      'ngSanitize',
      'ngTouch',
      'ui.router',
      'pascalprecht.translate'
    ])
    .config(AppConfig)
    .controller('AppCtrl', AppCtrl);

    function AppConfig ($stateProvider, $urlRouterProvider) {
      $stateProvider
        .state('login', {
          url: '/',
          templateUrl: 'views/login.html',
          controller: 'LoginCtrl'
        })
        .state('timesheet', {
          url: '/timesheet',
          templateUrl: 'views/timesheet/timesheet.html',
          controller: 'TimeSheetCtrl'
        });

      // if none of the above states are matched, use this as the fallback
      $urlRouterProvider.otherwise('/timesheet')
    }

    function AppCtrl ($rootScope) {
      $rootScope.GlobalParams = {
        isAuthenticated: true,
        navExpanded: true
      }
    }




})();
