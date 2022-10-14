package CentralEnergia.Generadores;

import static CentralEnergia.Utils.Utils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import IA.Energia.Centrales;
import IA.Energia.Cliente;
import IA.Energia.Clientes;

public abstract class GeneradorEstadoInicial {
    Clientes clientes;
    Centrales centrales;
    protected int[] asignacionClientes;
    protected double[] distribucionCentrales;
    protected double[] beneficioCentrales;

    protected Boolean[] centralesUsadas;

    public void creaEstadoInicial(Clientes clientes, Centrales centrales) throws Exception {
        this.clientes = clientes;
        this.centrales = centrales;
        asignacionClientes = new int[clientes.size()];
        Arrays.fill(asignacionClientes, NO_ASIGNADO);
        // En java, los arrays estan garantizados de inicializarse a 0.
        distribucionCentrales = new double[centrales.size()];
        beneficioCentrales = new double[centrales.size()];
        centralesUsadas = new Boolean[centrales.size()];
        Arrays.fill(centralesUsadas, false);
        creaEstadoInicial();
    };

    protected abstract void creaEstadoInicial() throws Exception;

    public int[] getAsignacionClientes() {
        return asignacionClientes;
    }

    public double[] getDistribucionCentrales() {
        return distribucionCentrales;
    }

    public double[] getBeneficioCentrales() {
        return beneficioCentrales;
    }

    protected ArrayList<Integer> seleccionRandomIdxClientesGarantizados() {
        ArrayList<Integer> idxClientesPorAsignar = new ArrayList<Integer>();
        for (int idxCliente = 0; idxCliente < clientes.size(); idxCliente++) {
            if (clientes.get(idxCliente).getTipo() == Cliente.GARANTIZADO) {
                idxClientesPorAsignar.add(idxCliente);
            }
        }
        Collections.shuffle(idxClientesPorAsignar);
        return idxClientesPorAsignar;
    }

    protected Boolean sePuedeAsignarClienteToCentral(int idxCliente, int idxCentral) {
        double distribucionExtra = produccionNecesariaToClienteFromCentral(clientes.get(idxCliente),
                centrales.get(idxCentral));
        return distribucionCentrales[idxCentral] + distribucionExtra <= centrales.get(idxCentral).getProduccion();
    }

    protected void asignaClienteToCentral(int idxCliente, int idxCentral) {
        asignacionClientes[idxCliente] = idxCentral;
        distribucionCentrales[idxCentral] += produccionNecesariaToClienteFromCentral(clientes.get(idxCliente),
                centrales.get(idxCentral));
        beneficioCentrales[idxCentral] += beneficioFromClienteToCentral(clientes.get(idxCliente),
                centrales.get(idxCentral));
    }

    protected int escogeCentralLibre() throws Exception {
        Boolean allUsed = true;
        for (Boolean isUsedCentral : centralesUsadas) {
            if (!isUsedCentral) {
                allUsed = false;
            }
        }
        if (allUsed) {
            throw new Exception("Fallada en la creación de un estado inicial \n" +
                    "Hay clientes que no se han podido asignar con el generador garantizados random");
        }
        int origin = 0;
        int end = centralesUsadas.length;
        int randomNum = ThreadLocalRandom.current().nextInt(origin, end);
        while (centralesUsadas[randomNum] == true) {
            randomNum = ThreadLocalRandom.current().nextInt(origin, end);
        }
        return randomNum;
    }
}
