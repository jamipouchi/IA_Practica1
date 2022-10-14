package CentralEnergia.Generadores;

public class GeneradorLineal extends GeneradorEstadoInicial {

    @Override
    protected void creaEstadoInicial() throws Exception {
        asignaClientesLinealmente();
    }

    private void asignaClientesLinealmente() throws Exception {

        int idxCentral = 0;
        for (int idxCliente = 0; idxCliente < asignacionClientes.length; idxCliente++) {
            Boolean asignado = false;
            while (!asignado) {
                if (idxCentral == distribucionCentrales.length) {
                    throw new Exception(
                            "Fallada en la creación de un estado inicial \n" +
                                    "Hay clientes que no se han podido asignar con el generador Lineal");
                }
                if (sePuedeAsignarClienteToCentral(idxCliente, idxCentral)) {
                    asignaClienteToCentral(idxCliente, idxCentral);
                    asignado = true;
                } else {
                    idxCentral++;
                }
            }
        }
    }
}
