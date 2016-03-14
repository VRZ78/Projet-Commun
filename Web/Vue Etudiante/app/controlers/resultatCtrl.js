

var resultatController = angular.module('resultatCtrl', []);
resultatController.controller('resultatCtrl', function ($scope,$http,LxDialogService) {


    $http.get('app/requetes/resultat.json').then(function(response){
        $scope.resultat=response.data.resultat;
        $scope.more=function(dialogId)
        {
            LxDialogService.open(dialogId);
        };
        console.log($scope.resultat);
    },function(reason){
        console.log(reason);
    });






});