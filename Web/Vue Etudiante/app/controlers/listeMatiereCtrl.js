

var listeMatiereCtrl = angular.module('listeMatiereCtrl', []);
listeMatiereCtrl.controller('listeMatiereCtrl', function ($scope,$http) {
    console.log("listeQuizzCtrl");

    $http.get('app/requetes/matiere.json').then(function(response){
        $scope.matieres=response.data;
        console.log($scope.matieres);
    },function(reason){
        console.log(reason);
    });

});