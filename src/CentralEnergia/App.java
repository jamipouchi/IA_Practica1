package CentralEnergia;

import java.util.List;
import java.util.Properties;
import java.util.Iterator;

import CentralEnergia.GeneradoraSucesores.GeneradoraSucesores;
import CentralEnergia.Generadores.*;
import CentralEnergia.Goaltest.CentralEnergiaGoalTest;
import CentralEnergia.Heuristica.MaximizarAsignaciones;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

public class App {
    public static void main(String[] args) throws Exception {

        // GeneradorLineal generador = new GeneradorLineal();
        GeneradorGarantizadosRandomHastaLLenar generador = new GeneradorGarantizadosRandomHastaLLenar();
        // GeneradorGarantizadosRandom generador = new GeneradorGarantizadosRandom();
        Estado estadoInicial = new Estado(2, 2, 2, 200, 0.3, 0.4, 0.3, 0.2, generador);
        centralEnergiaHillClimbingSearch(estadoInicial);
    }

    private static void centralEnergiaHillClimbingSearch(Estado estadoInicial) {
        try {
            Problem problem = new Problem(estadoInicial, new GeneradoraSucesores(), new CentralEnergiaGoalTest(),
                    new MaximizarAsignaciones());
            Search search = new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem, search);

            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
