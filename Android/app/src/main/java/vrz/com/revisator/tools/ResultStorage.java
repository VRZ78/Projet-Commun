package vrz.com.revisator.tools;

import vrz.com.revisator.objects.Quizz;
import vrz.com.revisator.objects.Resultat;

/**
 * Created by Victor on 22/03/2016.
 */
public class ResultStorage {

    private static Quizz qu;
    private static Resultat result;

    public static void setQu(Quizz qu){
        qu = qu;
    }

    public static void setresult(Resultat result){
        result = result;
    }

    public static Quizz getQu(){
        return qu;
    }

    public static Resultat result(){
        return result;
    }

}
