package CentralEnergia.Heuristica;

import CentralEnergia.Estado;
import aima.search.framework.HeuristicFunction;
import static CentralEnergia.Utils.Utils.*;

public class MinimizarDistancia implements HeuristicFunction {

    @Override
    public double getHeuristicValue(Object arg0) {
        Estado estado = (Estado) arg0;
        double distanciaTotal = 0;
        int[] asignacionClientes = estado.getAsignacionClientes();
        for (int idxCliente = 0; idxCliente < asignacionClientes.length; idxCliente++) {
            int idxCentral = asignacionClientes[idxCliente];
            if (idxCentral != NO_ASIGNADO) {
                distanciaTotal += distanciaFromClienteToCentral(Estado.getClientes().get(idxCliente),
                        Estado.getCentrales().get(idxCentral));
            }
        }
        return distanciaTotal;
    }
}
