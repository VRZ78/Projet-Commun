package vrz.com.revisator.objects;

import java.util.ArrayList;

/**
 * Created by Victor on 27/01/2016.
 * Objet Question. Doit être utilisé dans un objet Quizz
 */
public class Question {

    private String ennonceQuestion;
    private ArrayList<String> reponses;
    private ArrayList<Integer> bonnesReponses;

    /**
     * Constructeur 2
     * @param ennonceQuestion L"énnonce de la question
     */
    public Question(String ennonceQuestion){
        this.ennonceQuestion = ennonceQuestion;
        this.reponses = new ArrayList<String>();
        this.bonnesReponses = new ArrayList();
    }

    /**
     * Constructeur
     */
    public Question(){
        this.reponses = new ArrayList();
        this.bonnesReponses = new ArrayList();
    }

    /**
     * Ajoute une réponses à la question. L'ID de la réponse sera donné en fonction
     * de l'ordre de saisie, en commencant par 1.
     * @param ennonceReponse
     * @return
     */
    public boolean addReponse(String ennonceReponse){
        this.reponses.add(ennonceReponse);
        return true;
    }

    /**
     * Permet de récupérer une réponse
     * @param numeroReponse Le numéro de la réponse à récupérer
     * @return La réponse
     * @throws IndexOutOfBoundsException Si aucune question ne correspond au numéro
     */
    public String getReponse(int numeroReponse) throws IndexOutOfBoundsException{
        if(numeroReponse > this.reponses.size()){
            throw new IndexOutOfBoundsException();
        }
        else
            return this.reponses.get(numeroReponse-1);
    }

    /**
     * Permet de réupcérer les réponses à la question
     * @return Les réponses de la question
     */
    public String[] getAllReponses(){
        String[] allReponses = new String[this.reponses.size()];
        for(int i = 0; i < this.reponses.size();i++){
            allReponses[i] = this.reponses.get(i);
        }
        return allReponses;
    }

    /**
     * Ajoute une bonne réponse pour une question
     * @param numeroReponse Le numéro de la réponse
     * @return true si l'opération a réussie
     */
    public boolean addBonneReponse(int numeroReponse){
        this.bonnesReponses.add(numeroReponse);
        return true;
    }

    /**
     * Permet de récupérer les bonnes réponses de la question
     * @return
     */
    public int[] getBonneReponses(){
        int[] bonneReponses = new int[this.bonnesReponses.size()];
        for(int i = 0; i < this.bonnesReponses.size();i++){
            bonneReponses[i] = this.bonnesReponses.get(i);
        }
        return bonneReponses;
    }

    /**
     * Permet de savoir si une réponse fait partie des bonnes réponses
     * @param numeroReponse Le numéro de la réponse à tester
     * @return true si la réponse fait partie des bonnes réponses, false sinon
     */
    public boolean isBonneReponse(int numeroReponse) {
        for (int i = 0; i < bonnesReponses.size(); i++){
            if (this.bonnesReponses.get(i) == numeroReponse)
                return true;
        }
        return false;
    }

    /**
     * Donne le nombre de réponse que possède une question
     * @return le nombre de réponse que possède la question
     */
    public int getNbReponses(){
        return this.reponses.size();
    }

    /**
     * Donne le nombre de bonnes réponse que possède une question
     * @return le nombre de bonnes réponse que possède la question
     */
    public int getNbBonnesReponses(){
        return this.bonnesReponses.size();
    }

}
