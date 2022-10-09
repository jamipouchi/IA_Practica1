package Utils;

import IA.Energia.Central;
import IA.Energia.Cliente;
import IA.Energia.VEnergia;

public class Utils {
    public static final int NO_ASIGNADO = -1;

    public static double beneficioFromClienteToCentral(Cliente cliente, Central central) {
        try {
            double consumo = cliente.getConsumo();
            double tarifa = VEnergia.getTarifaClienteGarantizada(cliente.getTipo());
            double ganancias = consumo * tarifa;
            double coste = costeProduccionToClienteFromCentral(cliente, central);
            return ganancias - coste;
        } catch (Exception e) {
            // Esto nunca pasara. Es excepcion de getTarifa por tipo incorrecto de cliente
            return -1;
        }
    }

    public static double costeProduccionToClienteFromCentral(Cliente cliente, Central central) {
        try {
            double produccion = produccionNecesariaToClienteFromCentral(cliente, central);
            double costeMW = VEnergia.getCosteProduccionMW(central.getTipo());
            return produccion * costeMW;
        } catch (Exception e) {
            // Esto nunca pasara. Es excepcion de getCoste por tipo incorrecto de central
            return -1;
        }
    }

    public static double produccionNecesariaToClienteFromCentral(Cliente cliente, Central central) {
        double consumo = cliente.getConsumo();
        double distancia = Math.sqrt(Math.pow(central.getCoordX() - cliente.getCoordX(), 2)
                + Math.pow(central.getCoordY() - cliente.getCoordY(), 2));
        double perdida = VEnergia.getPerdida(distancia);
        return consumo * (1 + perdida);
    }
}