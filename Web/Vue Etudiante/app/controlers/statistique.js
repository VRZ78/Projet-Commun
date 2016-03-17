

var statistiqueController = angular.module('statistiqueCtrl', []);
statistiqueController.controller('statistiqueCtrl', function ($scope,$http) {

    $http.get('app/requetes/statistique.json').then(function(response){
        $scope.stat=response.data.statistique;
    //    console.log($scope.stat);
    },function(reason){
        console.log(reason);
    });

    $http.get('http://localhost:8080/statDetaille').then(function(response){
        console.log(response.data);
    },function(reason){
        console.log(reason);
    });



});