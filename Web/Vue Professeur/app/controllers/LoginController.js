/**
 * Created by Victor on 21/02/2016.
 */
angular.module('RevisatorProfApp')
    .controller('LoginController', function($scope, $http, LxNotificationService){


/*        $http.get('http://localhost:3000/qetQuiz').then(function(response){
            $scope.lareponse = response.data;
        },function(reason){
            console.log(reason);
        });*/


        $scope.accountConfirmCreation = function(quiz)
        {
            LxNotificationService.success('Votre compte a bien été créé. Merci de vérifier vos mails et de cliquer sur le lien d activation');
            $scope.isCreateButtonDisabled = true;
        };

        $scope.isCreateButtonDisabled = false;


    });