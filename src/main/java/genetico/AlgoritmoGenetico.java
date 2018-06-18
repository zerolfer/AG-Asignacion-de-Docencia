package main.java.genetico;


import main.java.busqueda.BusquedaIntercambioGrupo;
import main.java.busqueda.BusquedaLocal;
import main.java.genetico.algoritmos.creacion.AlgoritmoCreacion;
import main.java.genetico.algoritmos.creacion.CreacionAleatoria;
import main.java.genetico.algoritmos.cruce.AlgoritmoCruce;
import main.java.genetico.algoritmos.cruce.CruceOrderBased;
import main.java.genetico.algoritmos.mutacion.AlgoritmoMutacion;
import main.java.genetico.algoritmos.mutacion.MutacionIntercambio;
import main.java.genetico.algoritmos.reemplazo.AlgoritmoReemplazo;
import main.java.genetico.algoritmos.reemplazo.ReemplazoGeneracional;
import main.java.genetico.algoritmos.seleccion.AlgoritmoSeleccion;
import main.java.genetico.algoritmos.seleccion.SeleccionParesAleatorios;
import main.java.model.BD;
import main.java.util.RandomManager;
import main.java.util.Stopwatch;
import main.java.util.writer.CSVWriter;
import main.java.util.writer.DatosDetalladosEjecuciones;
import main.java.util.writer.DatosFenotipoEjecuciones;
import main.java.util.writer.DatosGlobalesEjecuciones;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlgoritmoGenetico {

    // VARIABLES DE ENTRADA DEL GENÉTICO

    /**
     * Ver {@link #getPopulationSize()}
     */
    private static Integer populationSize = 100;

    /**
     * Variable estatica accesible por todo el sistema.<br/>
     * Representa la probabilidad <b>POR DEFECTO</b> de que
     * el operador de cruce sea utilizado sobre un individuo concreo
     * <br/> <code>NO UTILIZAR ESTA VARIABLE PARA IMPRIMIR U EMPLEAR DATOS DE
     * INSTANCIA, ES MERAMENTE UNA VARIABLE POR DEFECTO POR SI
     * NO SE ESPECIFICAN DATOS DE INSTANCIA CONCRETOS</code>
     */
    public static final Float probabilidadCruce = 0.8f;

    /**
     * Variable estatica accesible por todo el sistema.<br/>
     * Representa la probabilidad <b>POR DEFECTO</b> de que el operador de
     * mutacion sea utilizado sobre un individuo concreto
     * <br/> <code>NO UTILIZAR ESTA VARIABLE PARA IMPRIMIR U EMPLEAR DATOS DE
     * * INSTANCIA, ES MERAMENTE UNA VARIABLE POR DEFECTO POR SI
     * * NO SE ESPECIFICAN DATOS DE INSTANCIA CONCRETOS</code>
     */
    public static final Float probabilidadMutacion = 0.10f;

    /**
     *
     * Representa el numero de generaciones que sean generadas
     * en total durante la ejecucion del algoritmo
     * @see #getNumeroMaximoGeneraciones()
     *
     */
    private Integer numeroMaximoGeneraciones = 500;

    /**
     * @see #getNumMaxGeneracionesSinMejora()
     */
    private Integer numMaxGeneracionesSinMejora = 80;

    /**
     * Variable estatica accesible por todo el sistema.<br/>
     * Representa la probabilidad <b>POR DEFECTO<b/> de que se realice
     * búsqueda local sobre un individuo concreto
     * resultate de un cruce
     * <br/> <code>NO UTILIZAR ESTA VARIABLE PARA IMPRIMIR U EMPLEAR DATOS DE
     * * INSTANCIA, ES MERAMENTE UNA VARIABLE POR DEFECTO POR SI
     * * NO SE ESPECIFICAN DATOS DE INSTANCIA CONCRETOS</code>
     */
    public static final Float probabilidadBusqueda = 1f; //0.7f;

    // ALGORITMOS
    private AlgoritmoCreacion creacion;
    private AlgoritmoSeleccion seleccion;
    private AlgoritmoCruce cruce;
    private AlgoritmoMutacion mutacion;
    private AlgoritmoReemplazo reemplazo;

    BusquedaLocal busqueda;

    // ESTRUCTURAS AUXILIARES
    private Individuo mejorIndividuo;
    private static boolean debug = true;
    private Stopwatch timer = new Stopwatch();
    private static int NUM_EJECUCIONES=1;

    /**
     * Indica si el output ya ha sido impreso,
     * evita que todas las ejecuciones del
     * algoritmo sean impresas, y que solamente
     * se imprima la primera.
     *
     * Es una variable auxiliar para el writer,
     * no se deberia cambiar su valor
     *
     */
    private boolean printed = false;

    private static CSVWriter printer1;

    /**
     * <p>
     * Realiza las operaciones oportunas de inicializacion que
     * permiten ofrecer la salida del algoritmo.
     * </p><p>
     * en caso de no ejecutarse este método previamente a la
     * ejecucion del método {@link #lanzarAlgoritmo(String)},
     * {@link #lanzarAlgoritmo(String, int)}, o
     * {@link #iniciar(String, int)}, no se obtendrá salida alguna
     * </p>
     * <br/>
     * <p>Si no se especifica, el numero de ejeciones configurado
     * por defecto es de 1</p>
     * @see #open(int)
     */
    public static void open() {
        open(1);
    }

    /**
     * Realiza las operaciones oportunas de inicializacion que
     * permiten ofrecer la salida del algoritmo.
     * <br/>
     * en caso de no ejecutarse este método previamente a la
     * ejecucion del método {@link #lanzarAlgoritmo(String)},
     * {@link #lanzarAlgoritmo(String, int)}, o
     * {@link #iniciar(String, int)}, no se obtendrá un
     * fichero con el histótico de todas las ejecuciones
     *
     * @param numEjecuciones numero de ejecuciones que se van a
     *                       realizar en todas las instancias que
     *                       se ejecuten a partir de ese momento
     */
    public static void open(int numEjecuciones) {
        AlgoritmoGenetico.NUM_EJECUCIONES = numEjecuciones;
            printer1 =
                new DatosGlobalesEjecuciones("files/DatosGlobalesEjecuciones.csv");
    }

    /**
     *
     * @param id
     */
    public void lanzarAlgoritmo(String id) {
        lanzarAlgoritmo(id, NUM_EJECUCIONES); // por defecto se lanza el numero de veces indicadas
    }

    public void lanzarAlgoritmo(String id, int NUM_EJECUCIONES) {

        for (int i = 1; i <= NUM_EJECUCIONES; i++) {
            if (debug) {
                System.out.println("ALGORITMO GENÉTICO - EJECUCIÓN " + id + ":");
                System.out.print("\tIteracion " + i + "...");
            }
            this.iniciar(id, i);

            if (printer1 != null) {
                List<String> configuracion = new ArrayList<>();

                configuracion.add(id);
                configuracion.add(this.populationSize.toString());
                configuracion.add(this.cruce.getProbabilidad().toString());
                configuracion.add(this.mutacion.getProbabilidad().toString());
                configuracion.add(getNumeroMaximoGeneraciones().toString());
                configuracion.add(Integer.toString(getNumMaxGeneracionesSinMejora()));
                configuracion.add(Integer.toString(this.numGeneraciones-1));
                configuracion.addAll(Arrays.asList(this.getAlgoritmos()));

                printer1.csvWriteData(this, configuracion);
            }
            if (debug)
                System.out.println("Hecho!");
        }
    }

    /**
     * Metodo estatico a invocar tras ejecutar los algoritmos
     * requeridos empleando el metodo
     * {@link #lanzarAlgoritmo(String, int)}
     * <br/>
     * Cierra el {@link CSVWriter} y finaliza el resto de
     * variables en caso de ser necesario
     *
     * @see #lanzarAlgoritmo(String)
     */
    public static void close() {
        if(printer1!=null)
            printer1.close();
    }


    public void setParameters(int tamPob, int numGen, int maxSinMejora) {
        this.populationSize = tamPob;
        this.numeroMaximoGeneraciones = numGen;
        this.numMaxGeneracionesSinMejora=maxSinMejora;
    }

    public AlgoritmoGenetico(AlgoritmoCreacion creator, AlgoritmoSeleccion seleccion, AlgoritmoCruce cruce,
                             AlgoritmoMutacion mutacion, AlgoritmoReemplazo reemplazo, BusquedaLocal busqueda) {
        inicializar(creator, seleccion, cruce, mutacion, reemplazo);

        this.busqueda = busqueda;

    }

    public AlgoritmoGenetico(AlgoritmoCreacion creator, AlgoritmoSeleccion seleccion, AlgoritmoCruce cruce,
                             AlgoritmoMutacion mutacion, AlgoritmoReemplazo reemplazo) {
        inicializar(creator, seleccion, cruce, mutacion, reemplazo);
        this.busqueda = new BusquedaIntercambioGrupo(probabilidadBusqueda);
    }

    private void inicializar(AlgoritmoCreacion creator, AlgoritmoSeleccion seleccion, AlgoritmoCruce cruce,
                             AlgoritmoMutacion mutacion, AlgoritmoReemplazo reemplazo) {
        this.creacion = creator;
        this.seleccion = seleccion;
        this.cruce = cruce;
        this.mutacion = mutacion;
        this.reemplazo = reemplazo;
        // this.decodificacion = new Decodificacion();
        // ordenarAsignaturas();
        ordenarProfesores();
    }

    public AlgoritmoGenetico() {
        this(new CreacionAleatoria(), new SeleccionParesAleatorios(), new CruceOrderBased(probabilidadCruce),
                new MutacionIntercambio(probabilidadMutacion), new ReemplazoGeneracional());
    }

    public void iniciar(String ejecucion, int seed) {
        RandomManager.seed = seed;
        genetico(ejecucion);
        RandomManager.destroyInstance();
    }

    /**
     * Este método permite al genético ejecutarse correctamente,
     * pues al estar ordenados los profesores <b>por idiomas
     * de docencia, estando primero los que pueden impartir en
     * ingles</b>
     */
    private void ordenarProfesores() {
        BD.getProfesores().sort(BD.comparatorProfesores);
    }

    private void ordenarAsignaturas() {
        BD.getAsignaturas().sort(BD.comparatorGrupos);
    }

    private int numGeneraciones=0;

    private void genetico(String ejecucion) {

        CSVWriter printer2 = null;
        CSVWriter printer3 = null;
        if (!printed) {
            printer2 = new DatosDetalladosEjecuciones(
                    "files/ejecuciones/DatosDetalladosEjecucion" + ejecucion + ".csv",
                    getAlgoritmos());
            printer3 = new DatosFenotipoEjecuciones(
                    "files/DatosFenotipoEjecuciones.csv", ejecucion);
        }

        numGeneraciones = 1;
        Generacion generacion = creacion.createPopulation(populationSize);
        timer.start();
        Individuo mejor=null;
        int numGeneracionesSinMejora = 0; // es la condicion de parada
        generacion.evaluar();
        do {

            List<Individuo[]> padres = seleccion.aplicar(generacion);
            List<Individuo[]> hijos = cruce.aplicar(padres);

            mutar(hijos);

            evaluar(hijos);
            busquedaLocal(hijos);

            int sizeAnterior = generacion.size();
            generacion = reemplazo.aplicar(padres, hijos);

            assert sizeAnterior == generacion.size();

            timer.newLap(numGeneraciones);

            if(mejor==null)
                mejor = obtenerMejor(generacion);
            else {
                Individuo mejorAnterior = mejor;
                mejor = obtenerMejor(generacion);
                numGeneracionesSinMejora = mejor.esMejor(mejorAnterior)?0:++numGeneracionesSinMejora ;
            }
            if (!printed) {
                float[] fitnessMedio = generacion.obtenerFitnessMedio();
                printer2.csvWriteData(this,
                        Integer.toString(numGeneraciones), timer.getTimeAtGeneration(numGeneraciones).toString(),
                        Integer.toString(mejor.getFitnessAsigProfesor()), Float.toString(mejor.getFitnessNumHoras()),
                        Float.toString(fitnessMedio[0]), Float.toString(fitnessMedio[1]));
            }
            numGeneraciones++;
        } while (numGeneraciones <= numeroMaximoGeneraciones && numGeneracionesSinMejora<=numMaxGeneracionesSinMejora);

        mejorIndividuo = obtenerMejor(generacion);
        if (!printed)
            printer3.csvWriteData(this);

        if (!printed) {
            printer2.close();
            printer3.close();
            printed = true;
        }
        if (debug) System.out.println("Mejor resultado: \n" + mejorIndividuo.toStringFull());
//            System.out.println("Mejor resultado: \n" + obtenerMejor(generacion).toString());
    }

    private Individuo obtenerMejor(Generacion generacion) {
        Individuo[] genotipo = generacion.getGenotipo();
        Individuo mejor = genotipo[0];
        for (Individuo indi : genotipo) {
            if (mejor.getFitnessAsigProfesor() == indi.getFitnessAsigProfesor()) {
                if (indi.getFitnessNumHoras() > mejor.getFitnessNumHoras())
                    mejor = indi;
            } else if (indi.getFitnessAsigProfesor() < mejor.getFitnessAsigProfesor())
                mejor = indi;
        }
        return mejor;
    }

    private void mutar(List<Individuo[]> individuos) {
        for (Individuo[] par : individuos)
            for (Individuo indi : par)
                mutacion.mutar(indi);
    }

    private void evaluar(List<Individuo[]> individuos) {
        for (Individuo[] par : individuos)
            for (Individuo indi : par)
                indi.evaluar();
    }

    private void busquedaLocal(List<Individuo[]> individuos) {
        for (Individuo[] par : individuos)
            for (int i = 0; i < par.length; i++) {
                Individuo mejorado = busqueda.aplicar(par[i]);
                par[i]=mejorado;
            }

    }

    public String[] getAlgoritmos() {
        return new String[]{
                /*unir(*/ creacion.getClass().getSimpleName() /*.split("(?=\\p{Upper})") )*/,
                /*unir(*/ seleccion.getClass().getSimpleName() /*.split("(?=\\p{Upper})") )*/,
                /*unir(*/ cruce.getClass().getSimpleName() /*.split("(?=\\p{Upper})") )*/,
                /*unir(*/ mutacion.getClass().getSimpleName() /*.split("(?=\\p{Upper})") )*/,
                /*unir(*/ reemplazo.getClass().getSimpleName() /*.split("(?=\\p{Upper})") )*/
        };
    }

    private String unir(String[] split) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            sb.append(split[i]);
            //if(i<split.length-1)
            //   sb.append(" ");
        }
        return sb.toString();
    }


    public Individuo getMejorIndividuo() {
        return mejorIndividuo;
    }

    public Stopwatch getTimer() {
        return timer;
    }


    /**
     * <p>Variable estatica accesible por todo el sistema,
     * lo que implica que es comun a todas las instancias,
     * por lo que no es modificable en tiempo de ejecución.</p>
     * <p>Representa el tamaño de la población en el algoritmo
     * evolutivo y es utilizado por los diferentes operadores
     * del genético.</p>
     */
    public static Integer getPopulationSize() {
        return populationSize;
    }

    /**
     * <p>Variable accesible por todo el sistema.<br/>
     * Representa el numero de generaciones maximo
     * que ejecutará el algoritmo.<p/>
     * <p>Conforma junto con {@link #getNumMaxGeneracionesSinMejora()}
     * las condiciones de parada del genético</p>
     *
     * @see #getNumMaxGeneracionesSinMejora()
     */
    public Integer getNumeroMaximoGeneraciones() {
        return numeroMaximoGeneraciones;
    }

    /**
     * <p>Variable accesible por todo el sistema.<br/>
     * Representa el numero maximo de generaciones que
     * se ejecutarán en caso de que no haya mejora con
     * respecto a la generación anterior. De esta
     * forma, si el genetico converje demasiado pronto,
     * se evitarán iteraciones (generaciones)
     * innecesarias.<p/>
     * <p>Conforma junto con {@link #getNumeroMaximoGeneraciones()}
     * las condiciones de parada del genético</p>
     *
     * @see #getNumeroMaximoGeneraciones()
     */
    public int getNumMaxGeneracionesSinMejora() {
        return numMaxGeneracionesSinMejora;
    }

}
