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

public class CombiPenalitzacio implements HeuristicFunction {

    @Override
    public double getHeuristicValue(Object arg0) {
        Estado estado = (Estado) arg0;

        double[] beneficioCentrales = estado.getBeneficioCentrales();
        Centrales centrales = Estado.getCentrales();
        double beneficioTotal = 0;
        for (int idxCentral = 0; idxCentral < beneficioCentrales.length; idxCentral++) {
            double beneficio = beneficioCentrales[idxCentral];
            double coste = beneficio == 0 ? calculaBeneficioParada(centrales.get(idxCentral))
                    : calculaBeneficioEnMarcha(centrales.get(idxCentral));
            beneficioTotal += beneficio - coste;
        }
        int[] asignacionClientes = estado.getAsignacionClientes();
        Clientes clientes = Estado.getClientes();
        double indemnizaciones = 0;
        for (int idxCliente = 0; idxCliente < asignacionClientes.length; idxCliente++) {
            if (asignacionClientes[idxCliente] == NO_ASIGNADO) {
                indemnizaciones += calculaIndemnizacion(clientes.get(idxCliente));
            }
        }
        // for (asignacionClientes)
        double CostDistanciaTotal = 0;
        double costMW = (90 + 419)/2; //90: cost avg de produccio, 419: guany avg
        for (int idxCliente = 0; idxCliente < asignacionClientes.length; idxCliente++) {
            int idxCentral = asignacionClientes[idxCliente];
            if (idxCentral != NO_ASIGNADO) {
                double consumclient = 5.5;//clientes.get(idxCliente).getConsumo();
                double dist = distanciaFromClienteToCentral(Estado.getClientes().get(idxCliente),Estado.getCentrales().get(idxCentral));
                if (dist > 10 && dist <= 25) {
                    CostDistanciaTotal += (costMW*consumclient*1*dist)*1/10;
                } else if (dist <= 50) {
                    CostDistanciaTotal += (costMW*consumclient*2)*2/10;
                } else if (dist <= 75) {
                    CostDistanciaTotal += (costMW*consumclient*4)*4/10;
                } else if (dist > 75) {
                    CostDistanciaTotal += (costMW*consumclient*6)*8/10;
                }
            }
        }
        // + distanciaTotal/(beneficioTotal*2)  o /100000
        return (-(beneficioTotal - indemnizaciones) + CostDistanciaTotal);
    }



    private double calculaBeneficioEnMarcha(Central central) {
        try {
            return VEnergia.getCosteMarcha(central.getTipo())
                    + central.getProduccion() * VEnergia.getCosteProduccionMW(central.getTipo());
        } catch (Exception e) {
            // Nunca saltará esta excepción, es una excepción de tipo de central.
            return 0;
        }
    }

    private double calculaBeneficioParada(Central central) {
        try {
            return VEnergia.getCosteParada(central.getTipo());
        } catch (Exception e) {
            // Nunca saltará esta excepción, es una excepción de tipo de central.
            return 0;
        }
    }

    private double calculaIndemnizacion(Cliente cliente) {
        try {
            return VEnergia.getTarifaClientePenalizacion(cliente.getTipo());
        } catch (Exception e) {
            // Nunca saltará esta excepción, es una excepción de tipo de central.
            return 0;
        }
    }
}