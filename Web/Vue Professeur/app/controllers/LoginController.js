/**
 * Created by Victor on 21/02/2016.
 */
angular.module('RevisatorProfApp')
    .controller('LoginController', function ($scope, $http, LxNotificationService) {


        // Fonction appelé lors du click sur le bouton de création de compte
        $scope.accountConfirmCreation = function (quiz) {
            if ($scope.hasAccountCreationButtonBeenClicked != true) {
                $http.post('http://localhost:8080/inscProf', JSON.stringify($scope.inscProf)).then(function (response) {
                    LxNotificationService.success('Votre compte a bien été créé. Merci de vérifier vos mails et de cliquer sur le lien d activation');
                    $scope.hasAccountCreationButtonBeenClicked = true;
                }, function () {
                    LxNotificationService.error('Impossible de contacter le serveur');
                });
            }
        };

        // ng-click du bouton Connexion
        $scope.startConnect = function(){
            $http.post('http://localhost:8080/connectProf', JSON.stringify($scope.connect)).then(function (response) {
                // TODO : Rediriger vers la page de selection des quiz
            }, function () {
                LxNotificationService.error('Impossible de contacter le serveur');
            });
        };

        $http.get('http://localhost:8080/inscription/etablissement').then(function (response) {
            $scope.etablissements = response.data;
            console.log($scope.reponse);
        }, function (reason) {
            console.log(reason);
        });

        $http.get('http://localhost:8080/inscription/formation').then(function (response) {
            $scope.year = response.data;
            console.log($scope.reponse);
        }, function (reason) {
            console.log(reason);
        });

        $scope.etablissements = "";

        $scope.hasAccountCreationButtonBeenClicked = false;

        // Liste des choix pour le select du niveau d'étude
        $scope.year = "";

        // ng-models
        $scope.inscProf = {
            username: "",
            password: "",
            mail: "",
            schoo1: "",
            birthday: undefined,
            year: "",
            typeCompte: 2
        };

        $scope.connect = {
            username: "",
            password: ""
        };

        // Vérification du formulaire d'inscritpion
        $scope.isCreateButtonDisabled = function () {
            var isCorrect = true;
            if ($scope.inscProf.username === "") {
                isCorrect = false;
                return true;
            }
            if ($scope.inscProf.password === "") {
                isCorrect = false;
                return true;
            }
            if ($scope.inscProf.confirmPassword === "") {
                isCorrect = false;
                return true;
            }
            if ($scope.inscProf.mail === "") {
                isCorrect = false;
                return true;
            }
            if ($scope.inscProf.confirmMail === "") {
                isCorrect = false;
                return true;
            }
            if ($scope.inscProf.birthday === "") {
                isCorrect = false;
                return true;
            }
            if ($scope.inscProf.school === "") {
                isCorrect = false;
                return true;
            }
            if ($scope.inscProf.year === "") {
                isCorrect = false;
                return true;
            }
            if ($scope.inscProf.password != $scope.inscProf.confirmPassword) {
                isCorrect = false;
                return true;
            }
            if ($scope.inscProf.mail != $scope.inscProf.confirmMail) {
                isCorrect = false;
                return true;
            }
        };

        // Vérification mots de passe et adresses mails identiques
        $scope.onMouseoverCreationButton = function () {
            var isSomethingWrong = false;
            if ($scope.inscProf.password != $scope.inscProf.confirmPassword && $scope.inscProf.password != "" && $scope.inscProf.confirmPassword != "") {
                LxNotificationService.error("Les mots de passe ne sont pas identiques.");
                isSomethingWrong = true;
            }
            if ($scope.inscProf.mail != $scope.inscProf.confirmMail && $scope.inscProf.mail != "" && $scope.inscProf.confirmMail != "") {
                LxNotificationService.error("Les adresses mails ne sont pas identiques.");
                isSomethingWrong = true;
            }
            if ($scope.isCreateButtonDisabled() === true) {
                LxNotificationService.error("Les champs ne sont pas tous remplis.");
            }
            if (isSomethingWrong === true) {
                return false;
            }
        }


    });