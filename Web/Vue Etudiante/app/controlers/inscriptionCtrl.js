//inscriptionCtrl

var inscriptionCtrl = angular.module('inscriptionCtrl', []);
inscriptionCtrl.controller('inscriptionCtrl', function ($scope, $http,$filter, LxNotificationService) {
    console.log("inscriptionCtrl");


    // Liste des choix pour le select du niveau d'�tude
    $scope.year = [
        {name: 'L1'},
        {name: 'L2'},
        {name: 'L3'},
        {name: 'M1'},
        {name: 'M2'},
        {name: 'Doctorat'}
    ];


    // ng-models
    $scope.inscEleve = {
        username: "",
        nom: "",
        prenom: "",
        password: "",
        mail: "",
        birthday: undefined,
        year: "",
        typeCompte: 1
    };

    // V�rification du formulaire d'inscritpion
    $scope.isCreateButtonDisabled = function () {
        return false;
    };
    /* function () {
     var isCorrect = true;
     if ($scope.inscEleve.username === "") {
     isCorrect = false;
     return true;
     }
     if ($scope.inscEleve.password === "") {
     isCorrect = false;
     return true;
     }
     if ($scope.inscEleve.confirmPassword === "") {
     isCorrect = false;
     return true;
     }
     if ($scope.inscEleve.mail === "") {
     isCorrect = false;
     return true;
     }
     if ($scope.inscEleve.confirmMail === "") {
     isCorrect = false;
     return true;
     }
     if ($scope.inscEleve.birthday === "") {
     isCorrect = false;
     return true;
     }
     if ($scope.inscEleve.school === "") {
     isCorrect = false;
     return true;
     }
     if ($scope.inscEleve.year === "") {
     isCorrect = false;
     return true;
     }
     if ($scope.inscEleve.password != $scope.inscEleve.confirmPassword) {
     isCorrect = false;
     return true;
     }
     if ($scope.inscEleve.mail != $scope.inscEleve.confirmMail) {
     isCorrect = false;
     return true;
     }
     };*/
/*
    $scope.champs = function () {
        var isCorrect = true;
        if ($scope.inscEleve.username === "") {
            isCorrect = false;
            return true;
        }
        if ($scope.inscEleve.password === "") {
            isCorrect = false;
            return true;
        }
        if ($scope.inscEleve.confirmPassword === "") {
            isCorrect = false;
            return true;
        }
        if ($scope.inscEleve.mail === "") {
            isCorrect = false;
            return true;
        }
        if ($scope.inscEleve.confirmMail === "") {
            isCorrect = false;
            return true;
        }
        if ($scope.inscEleve.birthday === "") {
            isCorrect = false;
            return true;
        }
        if ($scope.inscEleve.school === "") {
            isCorrect = false;
            return true;
        }
        if ($scope.inscEleve.year === "") {
            isCorrect = false;
            return true;
        }
        if ($scope.inscEleve.password != $scope.inscEleve.confirmPassword) {
            isCorrect = false;
            return true;
        }
        if ($scope.inscEleve.mail != $scope.inscEleve.confirmMail) {
            isCorrect = false;
            return true;
        }
    };
*/
    // V�rification mots de passe et adresses mails identiques
  /*  $scope.onMouseoverCreationButton = function () {
        var isSomethingWrong = false;
        if ($scope.inscEleve.password != $scope.inscEleve.confirmPassword && $scope.inscEleve.password != "" && $scope.inscEleve.confirmPassword != "") {
            LxNotificationService.error("Les mots de passe ne sont pas identiques.");
            isSomethingWrong = true;
        }
        if ($scope.inscEleve.mail != $scope.inscEleve.confirmMail && $scope.inscEleve.mail != "" && $scope.inscEleve.confirmMail != "") {
            LxNotificationService.error("Les adresses mails ne sont pas identiques.");
            isSomethingWrong = true;
        }
        if ($scope.isCreateButtonDisabled() === true) {
            LxNotificationService.error("Les champs ne sont pas tous remplis.");
        }
        if (isSomethingWrong === true) {
            return false;
        }
    };
*/
    /*
    var verifier = function () {
        var isSomethingWrong = false;
        if ($scope.inscEleve.password != $scope.inscEleve.confirmPassword && $scope.inscEleve.password != "" && $scope.inscEleve.confirmPassword != "") {
            LxNotificationService.error("Les mots de passe ne sont pas identiques.");
            isSomethingWrong = true;
        }
        if ($scope.inscEleve.mail != $scope.inscEleve.confirmMail && $scope.inscEleve.mail != "" && $scope.inscEleve.confirmMail != "") {
            LxNotificationService.error("Les adresses mails ne sont pas identiques.");
            isSomethingWrong = true;
        }
        if ($scope.champs() === true) {
            LxNotificationService.error("Les champs ne sont pas tous remplis.");
        }
        if (isSomethingWrong === true) {
            return false;
        }
    };
*/

    $scope.testInscripiton = {
        "username": "test1",
        "password": "test"
    };

    // Fonction appel� lors du click sur le bouton de cr�ation de compte
    $scope.accountConfirmCreation = function (quiz) {
        $http.post('http://localhost:8080/inscription', JSON.stringify($scope.inscEleve)).then(function (response) {
            $scope.inscEleve.birthday = $filter('date')($scope.inscEleve.birthday, "yyyy-MM-dd");
            LxNotificationService.success('Votre compte a bien �t� cr��. Merci de v�rifier vos mails et de cliquer sur le lien d activation');
            console.log(response);
            $scope.hasAccountCreationButtonBeenClicked = true;
        }, function () {
            LxNotificationService.error('Impossible de contacter le serveur');
        });
      /*  verifier();
        if ($scope.hasAccountCreationButtonBeenClicked != true) {
            $http.post('http://localhost:8080/signup', JSON.stringify($scope.testInscripiton)).then(function (response) {
                LxNotificationService.success('Votre compte a bien �t� cr��. Merci de v�rifier vos mails et de cliquer sur le lien d activation');
                console.log(response);
                $scope.hasAccountCreationButtonBeenClicked = true;
            }, function () {
                LxNotificationService.error('Impossible de contacter le serveur');
            });
        }*/
    };


});