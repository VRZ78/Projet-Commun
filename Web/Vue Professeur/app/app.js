/**
 * Created by Victor on 20/02/2016.
 */
var app = angular.module('RevisatorProfApp', ['lumx', 'ngRoute']);

app.config(function($routeProvider) {

    $routeProvider
        .when('/welcome', {
            controller:'MainPageController',
            templateUrl:'app/views/MainPage.html'
        })
        .when('/newQuiz', {
            controller:'NewEditQuizController',
            templateUrl:'app/views/NewEditQuiz.html'
        })
        .when('/', {
            controller:'LoginController',
            templateUrl:'app/views/LoginPage.html'
        })
        .otherwise({
            redirectTo: '/'
        });

});