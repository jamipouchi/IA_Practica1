package CentralEnergia.Heuristica;

import CentralEnergia.Estado;
import IA.Energia.*;
import aima.search.framework.HeuristicFunction;

import static CentralEnergia.Utils.Utils.NO_ASIGNADO;

import CentralEnergia.Estado;
import static CentralEnergia.Utils.Utils.*;
import IA.Energia.Central;
import IA.Energia.Centrales;
import IA.Energia.Cliente;
import IA.Energia.Clientes;
import IA.Energia.VEnergia;
import aima.search.framework.HeuristicFunction;

public class LaCombiPerfecta implements HeuristicFunction {

    @Override
    public double getHeuristicValue(Object arg0) {
        Estado estado = (Estado) arg0;

        double beneficio = new MaximizarBeneficio().getHeuristicValue(estado);
        double distancia = new MinimizarDistancia().getHeuristicValue(estado);

        return beneficio + 2 / 3 * distancia;
    }
}