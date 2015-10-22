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

    function AppConfig ($stateProvider, $urlRouterProvider, $translateProvider) {
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

      // translation folder
      $translateProvider.useStaticFilesLoader({
        prefix: 'translation/locale-',
        suffix: '.json'
      });
      // translation default language
      $translateProvider.preferredLanguage('en');
      // security
      $translateProvider.useSanitizeValueStrategy('escapeParameters');
    }

    function AppCtrl ($rootScope) {
      $rootScope.GlobalParams = {
        isAuthenticated: true,
        userData: {
          avatar: 'images/avatar.jpg',
          firstName: 'John',
          lastName: 'Doe',
          company: 'TimeTrack',
          email: 'jonh.doe@timetrack.com'
        },
        navExpanded: true,
        activePage: 'timesheet'
      }
    }




})();
