package vrz.com.revisator.objects;

import android.provider.Settings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Contient les réponses que l'utilisateur a donnée à un quizz
 * Created by Victor on 27/01/2016.
 */
public class Resultat implements Serializable {

    private int quizzID;
    private HashMap<Integer, ArrayList<Integer>> reponsesDeLutilisateur;

    /**
     * Constructeur
     * @param id L'ID du quizz auquel a répondu l'utilisateur
     */
    public Resultat(int id){
        this.quizzID = id;
        this.reponsesDeLutilisateur = new HashMap<Integer,ArrayList<Integer>>();
    }

    /**
     * Utile pour les question n'acceptant qu'une seule réponse
     * Retourne la réponse que l'utilisateur a donné pour la question.
     * Si utilisé avec une question à plusieur réponse, la première sera retournée
     * @param numeroQuestion Le numéro de la question
     * @return
     */
    public int getReponse(int numeroQuestion){
        ArrayList<Integer> reponses = this.reponsesDeLutilisateur.get(numeroQuestion);
        return reponses.get(0);
    }

    /**
     * Ajoute une réponse utilisateur à une question
     * @param numeroQuestion Le numéro de la question
     * @param numeroReponse La réponse de l'utilisateur
     */
    public void addReponse(int numeroQuestion, int numeroReponse){
        // Vérifie si une réponse a déjà été ajoutée pour cette question
        if(reponsesDeLutilisateur.containsKey(numeroQuestion)) {
            // Si oui récupère les réponses
            ArrayList<Integer> reponsesDejaEnregistrees = reponsesDeLutilisateur.get(numeroQuestion);
            // Ajoute la réponse
            reponsesDejaEnregistrees.add(numeroReponse);
            // Remplace dans le HashMap
            reponsesDeLutilisateur.put(numeroQuestion, reponsesDejaEnregistrees);
        }
        // Si pas de réponse enrégistrés
        else{
            // Création d'une ArrayList, ajout de la réponse et put
            ArrayList<Integer> reponses = new ArrayList<Integer>();
            reponses.add(numeroReponse);
            reponsesDeLutilisateur.put(numeroQuestion, reponses);
        }

    }

    /**
     * Retourne le nombre de réponses que l'utilisateur a donné pour une réponse
     * @param numeroQuestion Le numéro de la question
     * @return
     */
    public int getNbReponses(int numeroQuestion){
        return reponsesDeLutilisateur.get(numeroQuestion).size();
    }

    /**
     * Retourne l'ensemble des réponses que l'utilisateur a donné à une question.
     * Utile pour les questions contenant plusieurs réponses
     * @param numeroQuestion
     * @return
     */
    public ArrayList<Integer> getReponses(int numeroQuestion){
        // Crée une ArraList de la taille des réponses
        ArrayList<Integer> reponses = new ArrayList<Integer>(reponsesDeLutilisateur.get(numeroQuestion).size());
        // Copie l'Array dans le HashMap dans reponses
        for(int i = 0; i < reponsesDeLutilisateur.get(numeroQuestion).size(); i++) {
            reponses.add(reponsesDeLutilisateur.get(numeroQuestion).get(i));
        }
        return reponses;
    }


    /**
     * Calcule le score d'un utilisateur à un quizz.
     * Ne doit être utilisé qu'une fois que toutes les réponses du quizz ont été ajouté à cet objet
     * @param quizz Le quizz à vérifier
     * @return Le score de l'utilisateur au quizz
     */
    public double getScore(Quizz quizz){
        int score = 0;
        int nbBonnesRep = 0; // Nb de bonnes réponses qu'une question accpete

        // Vérifie si l'ID du quizz fourni correspond bien à ce résultat
        if(quizz.getId() == this.quizzID){
            // Si c'est le cas on calcule le score
            // Pour chaque question :
            for(int i = 0; i < quizz.getNombreQuestion(); i++){
                // On commence par regarde le nb de bonnes réponse qu'elle accnnbRepte
                nbBonnesRep = quizz.getQuestion(i).getNbBonnesReponses();
                // Si la question n'a que une réponse :
                if(nbBonnesRep == 1) {
                    // On regarde si l'utilisateur a bien répondu :
                    if (quizz.getQuestion(i).isBonneReponse(this.getReponse(i))) {
                        // Si oui on augmente son score
                        score++;
                    }
                }
                // Si elle en contient plusieurs
                else{
                    // On regarde si l'utilisateur à indiqué autant de réponse que la question en accepte
                    if(this.getNbReponses(i) == quizz.getQuestion(i).getNbBonnesReponses()){
                        // Si c'est le cas on récupère les réponses de l'utilisateur
                        ArrayList<Integer> reponsesDeLutilisateur = this.getReponses(i);
                        int nbReponsesCorrectes = 0;
                        // Puis pour chaque reponse donnée on vérifie si elle est juste
                        for(int j = 0; j < reponsesDeLutilisateur.size();j++){
                            if(quizz.getQuestion(i).isBonneReponse(reponsesDeLutilisateur.get(j))){
                                // Si elle est juste on incrémente le nb de bonnes réponse de l'util.
                                nbReponsesCorrectes++;
                            }
                        }
                        // Si toues les réponses sont correctes
                        if(nbReponsesCorrectes == quizz.getQuestion(i).getNbBonnesReponses()){
                            // On incrémente le score
                            score++;
                        }
                    }
                }
            }
        }
        return score;
    }

}
