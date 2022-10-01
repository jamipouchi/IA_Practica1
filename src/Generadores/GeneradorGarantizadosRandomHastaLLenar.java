package Generadores;

import java.util.ArrayList;

public class GeneradorGarantizadosRandomHastaLLenar extends GeneradorEstadoInicial {

    @Override
    protected void creaEstadoInicial() throws Exception {
        ArrayList<Integer> idxClientesPorAsignar = seleccionRandomIdxClientesGarantizados();
        asignaClientesRandomHastaLlenar(idxClientesPorAsignar);
    }

    private void asignaClientesRandomHastaLlenar(ArrayList<Integer> idxClientesPorAsignar) throws Exception {
        int idxCentral = escogeCentralLibre();
        for (int idxCliente : idxClientesPorAsignar) {
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
