package CentralEnergia.Heuristica;

import CentralEnergia.Estado;
import aima.search.framework.HeuristicFunction;
import static CentralEnergia.Utils.Utils.*;

public class MaximizarAsignaciones implements HeuristicFunction {

    @Override
    public double getHeuristicValue(Object arg0) {
        Estado estado = (Estado) arg0;
        int noAsignados = 0;
        int[] asignacionClientes = estado.getAsignacionClientes();
        for (int asignacion : asignacionClientes) {
            if (asignacion == NO_ASIGNADO) {
                noAsignados++;
            }
        }
        return noAsignados;
    }

}
