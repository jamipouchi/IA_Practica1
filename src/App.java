import Generadores.GeneradorGarantizadosRandomHastaLLenar;
import Generadores.GeneradorGarantizadosRandom;
import Generadores.GeneradorLineal;

public class App {
    public static void main(String[] args) throws Exception {

        // GeneradorLineal generador = new GeneradorLineal();
        GeneradorGarantizadosRandomHastaLLenar generador = new GeneradorGarantizadosRandomHastaLLenar();
        // GeneradorGarantizadosRandom generador = new GeneradorGarantizadosRandom();
        Estado estadoInicial = new Estado(2, 2, 2, 200, 0.3, 0.4, 0.3, 0.2, generador);
        int[] asignacionClientes = estadoInicial.getAsignacionClientes();
        double[] distribucionCentrales = estadoInicial.getDistribucionCentrales();
        double[] beneficioCentrales = estadoInicial.getBeneficioCentrales();

        for (int idxCliente = 0; idxCliente < asignacionClientes.length; idxCliente++) {
            System.out.format("El cliente %d se ha asignado a la central %d \n", idxCliente,
                    asignacionClientes[idxCliente]);
        }
        for (int idxCentral = 0; idxCentral < distribucionCentrales.length; idxCentral++) {
            System.out.format("La central %d esta distribuyendo %f con un beneficio %f \n", idxCentral,
                    distribucionCentrales[idxCentral], beneficioCentrales[idxCentral]);
        }
    }
}
