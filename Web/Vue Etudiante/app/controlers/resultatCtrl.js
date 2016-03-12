

var resultatController = angular.module('resultatCtrl', []);
resultatController.controller('resultatCtrl', function ($scope,$http) {


    $http.get('app/requetes/resultat.json').then(function(response){
        $scope.resultat=response.data.resultat;
        console.log($scope.resultat);
    },function(reason){
        console.log(reason);
    });


});