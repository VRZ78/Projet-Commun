var loginController = angular.module('loginCtrl', []);
loginController.controller('loginCtrl', function ($scope, $http, sharedStorageService, LxNotificationService) {
    //console.log("login");

    $scope.username = "";
    $scope.password = "";

    $scope.test = "toot";
    /*
     $http.get('app/requetes/matiere.json').then(function(response){
     $scope.matieres=response.data;
     //        sharedStorageService.set(response.data)
     sharedStorageService.set("lfvheyfygfzygygzgdyz");
     },function(reason){
     console.log(reason);
     });
     */
    $scope.login = {
        "username": "boutouik",
        "password": "BLq8z93z"
    };

    $http.post('http://localhost:8080/login', JSON.stringify($scope.login)).then(function (response) {
        LxNotificationService.success('connexion');
    }, function () {
        console.log('http://localhost:8080/login', JSON.stringify($scope.login));
        LxNotificationService.error('Impossible de contacter le serveur');
    });


    $scope.submit = function () {
        console.log($scope.nom);
        $scope.temp = sharedStorageService.get();
        console.log($scope.temp);
    };


});