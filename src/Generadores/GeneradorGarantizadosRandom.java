package Generadores;

import java.util.ArrayList;

public class GeneradorGarantizadosRandom extends GeneradorEstadoInicial {

    @Override
    protected void creaEstadoInicial() throws Exception {
        ArrayList<Integer> idxClientesPorAsignar = seleccionRandomIdxClientesGarantizados();
        asignaClientesRandom(idxClientesPorAsignar);
    }

    private void asignaClientesRandom(ArrayList<Integer> idxClientesPorAsignar) throws Exception {
        for (int idxCliente : idxClientesPorAsignar) {
            int idxCentral = escogeCentralLibre();
            Boolean asignado = false;
            while (!asignado) {
                if (sePuedeAsignarClienteToCentral(idxCliente, idxCentral)) {
                    asignaClienteToCentral(idxCliente, idxCentral);
                    asignado = true;
                } else {
                    centralesUsadas[idxCentral] = true;
                    idxCentral = escogeCentralLibre();
                }
            }
        }
    }
}