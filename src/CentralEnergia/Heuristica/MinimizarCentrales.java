package CentralEnergia.Heuristica;

import CentralEnergia.Estado;
import aima.search.framework.HeuristicFunction;

public class MinimizarCentrales implements HeuristicFunction {

    @Override
    public double getHeuristicValue(Object arg0) {
        Estado estado = (Estado) arg0;
        double[] distribucionCentrales = estado.getDistribucionCentrales();
        int numParadas = 0;
        for (double distribucion : distribucionCentrales) {
            if (distribucion == 0) {
                numParadas++;
            }
        }
        return numParadas;
    }
}
