package CentralEnergia.Heuristica;

import CentralEnergia.Estado;
import aima.search.framework.HeuristicFunction;

public class MaximizarBeneficio implements HeuristicFunction {

    @Override
    public double getHeuristicValue(Object arg0) {
        Estado estado = (Estado) arg0;
        double[] beneficioCentrales = estado.getBeneficioCentrales();
        double beneficioTotal = 0;
        for (double beneficioCentral : beneficioCentrales) {
            beneficioTotal += beneficioCentral;
        }
        return -beneficioTotal;
    }
}
