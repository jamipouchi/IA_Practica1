package CentralEnergia.Heuristica;

import CentralEnergia.Estado;
import aima.search.framework.HeuristicFunction;

public class BeneficioYConsumo implements HeuristicFunction {

    @Override
    public double getHeuristicValue(Object arg0) {
        Estado estado = (Estado) arg0;

        double beneficio = new MaximizarBeneficio().getHeuristicValue(estado);
        double usoEnergia = new MaximizarUsoEnergia().getHeuristicValue(estado);

        return beneficio + 2 / 3 * usoEnergia;
    }

}
