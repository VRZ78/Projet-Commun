package vrz.com.revisator.objects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Victor on 27/01/2016.
 * Objet Quizz
 */
public class Quizz implements Serializable {

    private ArrayList<Question> questionList;
    private int id;

    /**
     * Constructeur
     * @param id L'ID à donner au quizz
     */
    public Quizz(int id){
        this.id = id;
        this.questionList = new ArrayList<Question>();
    }

    /**
     * Ajoute une question au quizz
     * @param qu Un objet Question à ajouter au quizz
     * @return true si l'opération a réussie
     */
    public boolean addQuestion(Question qu){
        questionList.add(qu);
        return true;
    }


    /**
     * Permet de récupérer une question.
     * @param quNumber le numéro de la question dans le quizz
     * @return la question associée
     */
    public Question getQuestion(int quNumber){
        return this.questionList.get(quNumber);
    }

    /**
     * Permet de récupérer toutes les questions d'un quizz
     * @return Les Question du quizz
     */
    public Question[] getAllQuestions(){
        Question[] allQuestions = new Question[this.questionList.size()];
        for(int i = 0;i < questionList.size(); i++){
            allQuestions[i] = questionList.get(i);
        }
        return allQuestions;
    }


    /**
     * Permet de récupérer le nombre de question que comporte le quizz
     * @return Le nombre de questions
     */
    public int getNombreQuestion(){
        return this.questionList.size();
    }

    /**
     * Retourne l'ID du quizz
     * @return
     */
    public int getId(){
        return this.id;
    }

}
