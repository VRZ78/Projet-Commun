var tableauDeBordCtrl = angular.module('tableauDeBordCtrl', []);
tableauDeBordCtrl.controller('tableauDeBordCtrl', function ($scope, $http) {
    console.log("tableauDeBordCtrl");

    $scope.labels = ["Eating", "Drinking", "Sleeping", "Designing", "Coding", "Cycling", "Running"];
    $scope.data = [
        [65, 59, 90, 81, 56, 55, 40]
    ];

    $http.get('/');


    $http.get('http://localhost:8080/statsG').then(function (response) {
        $scope.statG = response.data;
        console.log($scope.statG);


    }, function (reason) {
        console.log(reason);
    });

});