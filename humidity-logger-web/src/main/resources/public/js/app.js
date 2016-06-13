var app = angular.module('app', ['ngRoute', 'ngResource', 'chart.js']);
app.config(function($routeProvider){
    $routeProvider
        .when('/devices',{
            templateUrl: 'views/devices.html',
            controller: 'devicesController'
        })
        .otherwise(
            { redirectTo: '/devices'}
        );
});