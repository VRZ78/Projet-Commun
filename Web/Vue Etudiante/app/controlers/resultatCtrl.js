

var resultatController = angular.module('resultatCtrl', []);
resultatController.controller('resultatCtrl', function ($scope,$http,$routeParams,LxDialogService,saveResultatQuizz) {

    var resultatFinal = {
        "resultat" : []
    };

    //saveResultatQuizz.get()


    $scope.resultat=saveResultatQuizz.get().questions;
    console.log("resultat saveResultatQuizz");
    console.log($scope.resultat);

    for(var i= 0; i<$scope.resultat.length; i++){
        var reponse = {
            "intituleQuestion": $scope.resultat[i].question,
            "estJuste": true,
            "BonneReponse": []
        };
        for(var j=0; j< $scope.resultat[i].reponse.length;j++){
            if( $scope.resultat[i].reponse[j].estChecker == true && $scope.resultat[i].reponse[j].estJuste==0 || $scope.resultat[i].reponse[j].estChecker == false && $scope.resultat[i].reponse[j].estJuste==1 ){
                reponse.estJuste = false;
            }
            if($scope.resultat[i].reponse[j].estJuste==1){
                reponse.BonneReponse.push($scope.resultat[i].reponse[j].reponse);
            }
        }
        resultatFinal.resultat.push(reponse);
    }
    console.log("Resultat final");
    console.log(resultatFinal);

    $scope.resultatPourHTML = resultatFinal.resultat;

    $scope.more=function(dialogId)
    {
        LxDialogService.open(dialogId);
    };


    /*
    $http.get('app/requetes/resultatFinal.json').then(function(response){
        $scope.resultat=response.data.questions;
        console.log($scope.resultat);

        for(var i= 0; i<$scope.resultat.length; i++){
            var reponse = {
                "intituleQuestion": $scope.resultat[i].question,
                "estJuste": true,
                "BonneReponse": []
            };
            for(var j=0; j< $scope.resultat[i].reponse.length;j++){
                if( $scope.resultat[i].reponse[j].estChecker == true && $scope.resultat[i].reponse[j].estJuste==0 || $scope.resultat[i].reponse[j].estChecker == false && $scope.resultat[i].reponse[j].estJuste==1 ){
                 reponse.estJuste = false;
                }
                if($scope.resultat[i].reponse[j].estJuste==1){
                    reponse.BonneReponse.push($scope.resultat[i].reponse[j].reponse);
                }
            }
            resultatFinal.resultat.push(reponse);
        }
        console.log("Resultat final");
        console.log(resultatFinal);

        $scope.resultatPourHTML = resultatFinal.resultat;

        $scope.more=function(dialogId)
        {
            LxDialogService.open(dialogId);
        };


    },function(reason){
        console.log(reason);
    });
*/






});