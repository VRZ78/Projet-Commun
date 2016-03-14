var loginController = angular.module('loginCtrl', []);
loginController.controller('loginCtrl', function ($scope, $http, sharedStorageService) {
    //console.log("login");

    $scope.nom="";
    $scope.motdepasse="";

    $scope.test="toot";

    $http.get('app/requetes/matiere.json').then(function(response){
        $scope.matieres=response.data;
//        sharedStorageService.set(response.data)
        sharedStorageService.set("lfvheyfygfzygygzgdyz");
    },function(reason){
        console.log(reason);
    });

    $scope.submit = function () {
        console.log($scope.nom);
        $scope.temp = sharedStorageService.get();
        console.log($scope.temp);
    };







});