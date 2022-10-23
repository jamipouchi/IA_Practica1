package CentralEnergia.Experimentos;

import java.util.List;
import java.util.Properties;
import java.util.Iterator;

import CentralEnergia.Estado;
import CentralEnergia.GeneradoraSucesores.GeneradoraSucesoresExp5;
import CentralEnergia.Generadores.*;
import CentralEnergia.Goaltest.CentralEnergiaGoalTest;
import CentralEnergia.Heuristica.HeuristicaExp5;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

public class Experimento5 {
    public static void main(String[] args) throws Exception {

        GeneradorVacio generador = new GeneradorVacio();
        int t1 = 5;
        int t2 = 10;
        int t3 = 25;
        int numClientes = 1000;
        double p1 = 0.25;
        double p2 = 0.3;
        double p3 = 0.45;
        double proporcionGarantizado = 0.75;
        int semilla1 = 4123;
        int semilla2 = 4123;
        Estado estadoInicial = new Estado(t1, t2, t3, numClientes, p1, p2, p3, proporcionGarantizado, generador,
                semilla1, semilla2);
        // centralEnergiaHillClimbingSearch(estadoInicial);
        centralEnergiaSimulatedAnnealing(estadoInicial);
    }

    private static void centralEnergiaHillClimbingSearch(Estado estadoInicial) {
        long startTime = System.currentTimeMillis();
        try {
            Problem problem = new Problem(estadoInicial, new GeneradoraSucesoresExp5(), new CentralEnergiaGoalTest(),
                    new HeuristicaExp5());
            System.out.println("problem build");
            Search search = new HillClimbingSearch();
            System.out.println("search build");
            SearchAgent agent = new SearchAgent(problem, search);
            System.out.println("agent build");
            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        } catch (Exception e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
    }

    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }

    }

    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }

    private static void centralEnergiaSimulatedAnnealing(Estado estadoInicial) {
        long startTime = System.currentTimeMillis();
        try {
            Problem problem = new Problem(estadoInicial, new GeneradoraSucesoresExp5(), new CentralEnergiaGoalTest(),
                    new HeuristicaExp5());
            System.out.println("problem build");
            Search search = new SimulatedAnnealingSearch(100000000, 10, 5, 0.2);
            System.out.println("search build");
            SearchAgent agent = new SearchAgent(problem, search);
            System.out.println("agent build");
            System.out.println();
            printActionsS(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        } catch (Exception e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
    }

    private static void printActionsS(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            Estado action = (Estado) actions.get(i);
            System.out.println(action.toString());
        }
    }
}
