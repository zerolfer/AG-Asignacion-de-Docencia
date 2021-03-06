package test.java.genetico;

import main.java.genetico.Individuo;
import main.java.genetico.operadores.decodificacion.AlgoritmoDecodificacion;
import main.java.genetico.operadores.decodificacion.Decodificacion;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Sergio Florez on 06/03/2018.
 */
public class FitnessTest {

    @Test
    public void tetst1() {
        Individuo v = new Individuo(new int[]{2, 52, 0, 36, 24, 1, 12, 80, 4, 82, 59, 64, 39, 41, 45, 3, 15, 43, 63, 28, 66, 11, 21, 81, 8, 14, 33, 58, 34, 35, 61, 18, 60, 42, 50, 20, 79, 16, 48, 22, 74, 26, 71, 19, 49, 75, 40, 5, 7, 65, 51, 70, 30, 55, 54, 10, 9, 56, 73, 6, 76, 68, 67, 44, 29, 25, 69, 78, 77, 37, 27, 32, 17, 38, 23, 31, 57, 62, 72, 53, 47, 13, 46});
        AlgoritmoDecodificacion decoder = new Decodificacion();
        decoder.aplicar(v);
        Assert.assertEquals("{" +
                        "0=[0, 80, 1, 2, 52, 36, 24, 26, 12], " +
                        "1=[64, 82, 3, 4, 39, 23, 41, 45, 15, 63], " +
                        "2=[33, 65, 66, 34, 35, 67, 42, 74, 43, 14, 16, 18, 51, 20, 21, 22, 58, 59, 61], " +
                        "3=[81, 17, 50, 19, 8, 11, 28, 60, 79], " +
                        "4=[48, 49, 70, 54, 71, 7, 55, 40, 75], " +
                        "5=[5, 6, 56, 9, 73, 25, 10, 27, 76, 44, 77, 30], " +
                        "6=[32, 68, 69, 37, 53, 38, 57, 29, 78, 31], " +
                        "7=[72, 13, 62, 46, 47]}",
                v.getFenotipo().toString());
        /* // Version anterior: lista de asignaturas = profesor
        Assert.assertEquals("{0=0, 1=0, 2=0, 3=1, 4=1, 5=5, 6=5, 7=4, 8=3, 9=5, 10=5, 11=3, 12=0, 13=7, 14=2," +
                " 15=1, 16=2, 17=3, 18=2, 19=3, 20=2, 21=2, 22=2, 23=1, 24=0, 25=5, 26=0, 27=5, 28=2, 29=6," +
                " 30=5, 31=6, 32=6, 33=2, 34=2, 35=2, 36=0, 37=6, 38=6, 39=1, 40=4, 41=1, 42=2, 43=2, 44=5," +
                " 45=1, 46=7, 47=7, 48=4, 49=4, 50=3, 51=3, 52=0, 53=6, 54=4, 55=4, 56=5, 57=6, 58=2, 59=1," +
                " 60=3, 61=2, 62=7, 63=2, 64=1, 65=2, 66=2, 67=2, 68=6, 69=6, 70=4, 71=4, 72=7, 73=5, 74=2," +
                " 75=4, 76=5, 77=5, 78=6, 79=3, 80=0, 81=3, 82=1}", v.getFenotipo().toString());
        */
        Assert.assertEquals("Individuo:{ \n" +
                        "\tfitness: (9, 0.0), Codigo cromosoma: 2,52,0,36,24,1,12,80,4,82,59,64,39,41,45,3,15,43,63,28,66,11,21,81,8,14,33,58,34,35,61,18,60,42,50,20,79,16,48,22,74,26,71,19,49,75,40,5,7,65,51,70,30,55,54,10,9,56,73,6,76,68,67,44,29,25,69,78,77,37,27,32,17,38,23,31,57,62,72,53,47,13,46\n" +
                        "Profesor{id='0', nombre='Prof1', capacidad=240.0, bilingue=true, area='CCIA'} asignadas: [ 0, 80, 1, 2, 52, 36, 24, 26, 12 ]\n" +
                        "Profesor{id='1', nombre='Prof2', capacidad=240.0, bilingue=true, area='CCIA'} asignadas: [ 64, 82, 3, 4, 39, 23, 41, 45, 15, 63 ]\n" +
                        "Profesor{id='2', nombre='Prof3', capacidad=240.0, bilingue=false, area='LSI'} asignadas: [ 33, 65, 66, 34, 35, 67, 42, 74, 43, 14, 16, 18, 51, 20, 21, 22, 58, 59, 61 ]\n" +
                        "Profesor{id='3', nombre='Prof4', capacidad=240.0, bilingue=true, area='LSI'} asignadas: [ 81, 17, 50, 19, 8, 11, 28, 60, 79 ]\n" +
                        "Profesor{id='4', nombre='Prof5', capacidad=240.0, bilingue=false, area='CCIA'} asignadas: [ 48, 49, 70, 54, 71, 7, 55, 40, 75 ]\n" +
                        "Profesor{id='5', nombre='Prof6', capacidad=240.0, bilingue=false, area='LSI'} asignadas: [ 5, 6, 56, 9, 73, 25, 10, 27, 76, 44, 77, 30 ]\n" +
                        "Profesor{id='6', nombre='Prof7', capacidad=240.0, bilingue=false, area='LSI'} asignadas: [ 32, 68, 69, 37, 53, 38, 57, 29, 78, 31 ]\n" +
                        "Profesor{id='7', nombre='Prof8', capacidad=240.0, bilingue=false, area='CCIA'} asignadas: [ 72, 13, 62, 46, 47 ]\n}",
                v.toStringFull());

    }

    @Test
    public void checkRepetidos() {
        int[] array = new int[]{49, 43, 76, 80, 63, 27, 15, 24, 61, 41, 77, 39, 3, 65, 23, 71, 21, 82, 66, 47, 12, 48, 22, 32, 56, 4, 34, 50, 46, 59, 31, 74, 29, 68, 38, 33, 72, 75, 44, 17, 26, 28, 0, 19, 78, 64, 55, 11, 9, 2, 30, 51, 25, 36, 8, 53, 14, 79, 1, 16, 20, 69, 62, 67, 37, 52, 54, 58, 73, 5, 7, 13, 57, 6, 40, 81, 42, 70, 60, 35, 43, 63, 47};
        for (int i = 0; i < array.length; i++)
            for (int j = 0; j < array.length; j++)
                if (i != j)
                    Assert.assertNotEquals(i, j);
    }
}
