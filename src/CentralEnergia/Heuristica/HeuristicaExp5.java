package CentralEnergia.Heuristica;

import CentralEnergia.Estado;
import static CentralEnergia.Utils.Utils.*;
import IA.Energia.Central;
import IA.Energia.Centrales;
import IA.Energia.Cliente;
import IA.Energia.Clientes;
import IA.Energia.VEnergia;
import aima.search.framework.HeuristicFunction;

public class HeuristicaExp5 implements HeuristicFunction {

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
        int penalizacionGarantizados = 0;
        for (int idxCliente = 0; idxCliente < asignacionClientes.length; idxCliente++) {
            if (asignacionClientes[idxCliente] == NO_ASIGNADO) {
                if (clientes.get(idxCliente).getContrato() == Cliente.GARANTIZADO) {
                    penalizacionGarantizados += 1;
                } else {
                    indemnizaciones += calculaIndemnizacion(clientes.get(idxCliente));
                }
            }
        }

        return -(beneficioTotal - indemnizaciones) + 1000000 * Math.pow(penalizacionGarantizados,1);
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