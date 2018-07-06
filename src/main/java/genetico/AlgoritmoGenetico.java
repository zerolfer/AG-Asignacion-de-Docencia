package main.java.genetico;


import main.java.busqueda.AbstractBusquedaLocal;
import main.java.busqueda.BusquedaIntercambioGrupo;
import main.java.busqueda.BusquedaLocal;
import main.java.genetico.operadores.creacion.CreacionAleatoria;
import main.java.genetico.operadores.creacion.OperadorCreacion;
import main.java.genetico.operadores.cruce.CruceOrderBased;
import main.java.genetico.operadores.cruce.OperadorCruce;
import main.java.genetico.operadores.mutacion.MutacionIntercambio;
import main.java.genetico.operadores.mutacion.OperadorMutacion;
import main.java.genetico.operadores.reemplazo.OperadorReemplazo;
import main.java.genetico.operadores.reemplazo.ReemplazoGeneracional;
import main.java.genetico.operadores.seleccion.OperadorSeleccion;
import main.java.genetico.operadores.seleccion.SeleccionParesAleatorios;
import main.java.io.Settings;
import main.java.io.writer.CSVWriter;
import main.java.io.writer.DatosDetalladosEjecuciones;
import main.java.io.writer.DatosFenotipoEjecuciones;
import main.java.io.writer.DatosGlobalesEjecuciones;
import main.java.model.BD;
import main.java.util.RandomManager;
import main.java.util.Stopwatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlgoritmoGenetico {

    // VARIABLES DE ENTRADA DEL GENÉTICO

    /**
     * Ver {@link #getPopulationSize()}
     */
    private static Integer populationSize =
            Settings.getInteger("genetico.global.populationSize");

    /**
     * Variable estatica accesible por todo el sistema.<br/>
     * Representa la probabilidad <b>POR DEFECTO</b> de que
     * el operador de cruce sea utilizado sobre un individuo concreo
     * <br/> <code>NO UTILIZAR ESTA VARIABLE PARA IMPRIMIR U EMPLEAR DATOS DE
     * INSTANCIA, ES MERAMENTE UNA VARIABLE POR DEFECTO POR SI
     * NO SE ESPECIFICAN DATOS DE INSTANCIA CONCRETOS</code>
     */
    public static final Float PROBABILIDAD_CRUCE =
            Settings.getFloat("genetico.predeterminados.probabilidadCruce");

    /**
     * Variable estatica accesible por todo el sistema.<br/>
     * Representa la probabilidad <b>POR DEFECTO</b> de que el operador de
     * mutacion sea utilizado sobre un individuo concreto
     * <br/> <code>NO UTILIZAR ESTA VARIABLE PARA IMPRIMIR U EMPLEAR DATOS DE
     * * INSTANCIA, ES MERAMENTE UNA VARIABLE POR DEFECTO POR SI
     * * NO SE ESPECIFICAN DATOS DE INSTANCIA CONCRETOS</code>
     */
    public static final Float PROBABILIDAD_MUTACION =
            Settings.getFloat("genetico.predeterminados.probabilidadMutacion");

    /**
     * Variable estatica accesible por todo el sistema.<br/>
     * Representa la probabilidad <b>POR DEFECTO<b/> de que se realice
     * búsqueda local sobre un individuo concreto
     * resultate de un cruce
     * <br/> <code>NO UTILIZAR ESTA VARIABLE PARA IMPRIMIR U EMPLEAR DATOS DE
     * * INSTANCIA, ES MERAMENTE UNA VARIABLE POR DEFECTO POR SI
     * * NO SE ESPECIFICAN DATOS DE INSTANCIA CONCRETOS</code>
     */
    public static final Float PROBABILIDAD_BUSQUEDA =
            Settings.getFloat("genetico.predeterminados.probabilidadBusqueda");

    /**
     * Representa el numero de generaciones que sean generadas
     * en total durante la ejecucion del algoritmo
     * @see #getNumeroMaximoGeneraciones()
     */
    private Integer numeroMaximoGeneraciones =
            Settings.getInteger("genetico.global.numeroMaximoGeneraciones");

    /**
     * @see #getNumMaxGeneracionesSinMejora()
     */
    private Integer numMaxGeneracionesSinMejora =
            Settings.getInteger("genetico.global.numMaxGeneracionesSinMejora");


    // ALGORITMOS
    private OperadorCreacion creacion;
    private OperadorSeleccion seleccion;
    private OperadorCruce cruce;
    private OperadorMutacion mutacion;
    private OperadorReemplazo reemplazo;

    private BusquedaLocal busqueda;

    // ESTRUCTURAS AUXILIARES
    private Individuo mejorIndividuo;
    private static boolean debug = Settings.getBoolean("debug.genetico.mensajes");
    private Stopwatch timer = new Stopwatch();
    private static int numeroEjecuciones = Settings.getInteger("genetico.predeterminados.numeroEjecuciones");

    /**
     * Indica si el output ya ha sido impreso,
     * evita que todas las ejecuciones del
     * algoritmo sean impresas, y que solamente
     * se imprima la primera.
     *
     * Es una variable auxiliar para el writer,
     * no se deberia cambiar su valor
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
        AlgoritmoGenetico.numeroEjecuciones = numEjecuciones;
        printer1 =
                new DatosGlobalesEjecuciones(Settings.get("path.output.globales") +
                        Settings.get("file.nombre.globales") + ".csv");
    }

    /**
     *
     * @param id nombre identificador de la ejecucion a lanzar
     */
    public void lanzarAlgoritmo(String id) {
        lanzarAlgoritmo(id, numeroEjecuciones); // por defecto se lanza el numero de veces indicadas
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
                configuracion.add(AlgoritmoGenetico.populationSize.toString());
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
     * Metodo estatico a invocar tras ejecutar los operadores
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
        AlgoritmoGenetico.populationSize = tamPob;
        this.numeroMaximoGeneraciones = numGen;
        this.numMaxGeneracionesSinMejora=maxSinMejora;
    }

    public AlgoritmoGenetico(OperadorCreacion creator, OperadorSeleccion seleccion, OperadorCruce cruce,
                             OperadorMutacion mutacion, OperadorReemplazo reemplazo, BusquedaLocal busqueda) {
        inicializar(creator, seleccion, cruce, mutacion, reemplazo);

        this.busqueda = busqueda;

    }

    public AlgoritmoGenetico(OperadorCreacion creator, OperadorSeleccion seleccion, OperadorCruce cruce,
                             OperadorMutacion mutacion, OperadorReemplazo reemplazo) {
        inicializar(creator, seleccion, cruce, mutacion, reemplazo);
        this.busqueda = new BusquedaIntercambioGrupo(PROBABILIDAD_BUSQUEDA);
    }

    private void inicializar(OperadorCreacion creator, OperadorSeleccion seleccion, OperadorCruce cruce,
                             OperadorMutacion mutacion, OperadorReemplazo reemplazo) {
        this.creacion = creator;
        this.seleccion = seleccion;
        this.cruce = cruce;
        this.mutacion = mutacion;
        this.reemplazo = reemplazo;
        // this.decodificacion = new Decodificacion();
        // ordenarAsignaturas();
        ordenarProfesores();
//        BD.getProfesores(); // inicializa
    }

    public AlgoritmoGenetico() {
        this(new CreacionAleatoria(), new SeleccionParesAleatorios(), new CruceOrderBased(PROBABILIDAD_CRUCE),
                new MutacionIntercambio(PROBABILIDAD_MUTACION), new ReemplazoGeneracional());
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
        BD.getProfesores().sort(BD.getComparatorProfesores());
    }

    private void ordenarAsignaturas() {
        BD.getGrupos().sort(BD.getComparatorGrupos());
    }

    private int numGeneraciones=0;

    private void genetico(String ejecucion) {

        CSVWriter printer2 = null;
        CSVWriter printer3 = null;
        if (!printed) {
            printer2 = new DatosDetalladosEjecuciones(Settings.get("path.output.detalles") +
                    Settings.get("file.nombre.detalles") + ejecucion + ".csv",
                    getAlgoritmos());
            printer3 = new DatosFenotipoEjecuciones(
                    Settings.get("path.output.fenotipo") + Settings.get("file.nombre.fenotipo") +
                            ".csv", ejecucion);
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

        if (!printed) {
            printer3.csvWriteData(this);
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
        if(AbstractBusquedaLocal.debug)
            System.out.println("\n\nGeneracion "+this.numGeneraciones+" ------------------------------------" +
                "--------------------------------------------------------------------------------------------\n");
        for (Individuo[] par : individuos)
            for (int i = 0; i < par.length; i++) {
                Individuo mejorado = busqueda.aplicar(par[i]);
                par[i]=mejorado;
            }
    }

    private String[] getAlgoritmos() {
        return new String[]{
                /*unir(*/ creacion.getClass().getSimpleName() /*.split("(?=\\p{Upper})") )*/,
                /*unir(*/ seleccion.getClass().getSimpleName() /*.split("(?=\\p{Upper})") )*/,
                /*unir(*/ cruce.getClass().getSimpleName() /*.split("(?=\\p{Upper})") )*/,
                /*unir(*/ mutacion.getClass().getSimpleName() /*.split("(?=\\p{Upper})") )*/,
                /*unir(*/ reemplazo.getClass().getSimpleName() /*.split("(?=\\p{Upper})") )*/
        };
    }

//    private String unir(String[] split) {
//        StringBuilder sb = new StringBuilder();
//        for (String aSplit : split) {
//            sb.append(aSplit);
//            //if(i<split.length-1)
//            //   sb.append(" ");
//        }
//        return sb.toString();
//    }


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
