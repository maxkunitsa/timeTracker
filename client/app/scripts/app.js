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
    .run(run)
    .config(AppConfig)
    .controller('AppCtrl', AppCtrl);

    function run ($rootScope) {
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

    function AppConfig ($stateProvider, $urlRouterProvider, $translateProvider) {
      $stateProvider
        .state('login', {
          url: '/login',
          templateUrl: 'views/login.html',
          controller: 'LoginCtrl',
          data: {
            title: 'Login'
          }
        })
        .state('dashboard', {
          url: '/',
          templateUrl: 'views/dashboard/dashboard.html',
          controller: 'DashboardCtrl',
          data: {
            title: 'Dashboard'
          }
        })
        .state('timesheet', {
          url: '/timesheet',
          templateUrl: 'views/timesheet/timesheet.html',
          controller: 'TimeSheetCtrl',
          data: {
            title: 'Timesheet'
          }
        })
        .state('projects', {
          url: '/projects',
          templateUrl: 'views/projects/projects.html',
          controller: 'ProjectsCtrl',
          data: {
            title: 'Projects'
          }
        })
        .state('reports', {
          url: '/reports',
          templateUrl: 'views/reports/reports.html',
          controller: 'ReportsCtrl',
          data: {
            title: 'Reports'
          }
        });

      // if none of the above states are matched, use this as the fallback
      $urlRouterProvider.otherwise('/')

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

    function AppCtrl ($scope, $rootScope, $state) {
      $scope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
        $rootScope.GlobalParams.activePage = $state.current.name;

        // set global properties from $state configuration
        if (toState.data) {
          if (toState.data.title) {
            $rootScope.GlobalParams.PageTitle = toState.data.title;
          }
        }

      });
    }




})();
