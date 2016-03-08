var listeQuizzCtrl = angular.module('listeQuizzCtrl', []);
listeQuizzCtrl.controller('listeQuizzCtrl', function ($scope,$http) {
    console.log("listeQuizzCtrl");

    $http.get('app/requetes/listequizz.json').then(function(response){
        $scope.listeQuizz=response.data;
        console.log($scope.listeQuizz);
    },function(reason){
        console.log(reason);
    });

});