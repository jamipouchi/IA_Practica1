package CentralEnergia.GeneradoraSucesores;

import java.util.ArrayList;
import java.util.List;

import CentralEnergia.Estado;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import static CentralEnergia.Utils.Utils.NO_ASIGNADO;

public class GeneradoraSucesores implements SuccessorFunction {

    @Override
    public List getSuccessors(Object arg0) {
        Estado estadoActual = (Estado) arg0;
        ArrayList sucesores = new ArrayList();

        // Operador desasignar
        for (int idxCliente = 0; idxCliente < estadoActual.getAsignacionClientes().length; ++idxCliente) {
            Estado nuevoEstado = new Estado(estadoActual);
            if (nuevoEstado.desasignar(idxCliente)) {
                sucesores.add(new Successor(nuevoEstado.toString(), nuevoEstado));
            }
        }

        // Operador asignar
        for (int idxCliente = 0; idxCliente < estadoActual.getAsignacionClientes().length; ++idxCliente) {
            for (int idxCentral = 0; idxCentral < estadoActual.getDistribucionCentrales().length; ++idxCentral) {
                Estado nuevoEstado = new Estado(estadoActual);
                if (nuevoEstado.asignar(idxCliente, idxCentral)) {
                    sucesores.add(new Successor(nuevoEstado.toString(), nuevoEstado));
                }
            }
        }

        /*
        //Operador smartSwap
        for (int idxCliente1 = 0; idxCliente1 < estadoActual.getAsignacionClientes().length; ++idxCliente1) {
            int[] assig = estadoActual.getAsignacionClientes();
            if (assig[idxCliente1] != NO_ASIGNADO) {
                for (int idxCentral = 0; idxCentral < estadoActual.getDistribucionCentrales().length; ++idxCentral) {
                    Estado nuevoEstado = new Estado(estadoActual);
                    if (nuevoEstado.swapmodern(idxCliente1, idxCentral)) {
                        sucesores.add(new Successor(nuevoEstado.toString(), nuevoEstado));
                    }
                }
            }
        }
        */


        /*
        // Operador swap
        for (int idxCliente1 = 0; idxCliente1 < estadoActual.getAsignacionClientes().length; ++idxCliente1) {
            for (int idxCliente2 = idxCliente1 + 1; idxCliente2 < estadoActual.getAsignacionClientes().length; ++idxCliente2) {
                Estado nuevoEstado = new Estado(estadoActual);
                if (nuevoEstado.swap(idxCliente1, idxCliente2)) {
                    sucesores.add(new Successor(nuevoEstado.toString(), nuevoEstado));
                }
            }
        }
        */
        
        return sucesores;
    }
}
