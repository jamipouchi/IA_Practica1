package CentralEnergia.Heuristica;

import CentralEnergia.Estado;
import aima.search.framework.HeuristicFunction;

public class MaximizarUsoEnergia implements HeuristicFunction {

    @Override
    public double getHeuristicValue(Object arg0) {
        Estado estado = (Estado) arg0;
        double[] distribucionCentrales = estado.getDistribucionCentrales();
        double energiaNoUsada = 0;
        for (int idxCentral = 0; idxCentral < distribucionCentrales.length; idxCentral++) {
            double energiaProducida = Estado.getCentrales().get(idxCentral).getProduccion();
            double porcentajeDeUso = (energiaProducida - distribucionCentrales[idxCentral]) / energiaProducida;
            energiaNoUsada += (porcentajeDeUso) * Math.log(porcentajeDeUso);
        }
        return -energiaNoUsada;
    }
}
