package CentralEnergia;

import IA.Energia.Central;
import IA.Energia.Centrales;
import IA.Energia.Cliente;
import IA.Energia.Clientes;

import static CentralEnergia.Utils.Utils.*;
import CentralEnergia.Heuristica.MaximizarBeneficio;
import CentralEnergia.Generadores.GeneradorEstadoInicial;

public class Estado {
    private static Centrales centrales;
    private static Clientes clientes;
    private int[] asignacionClientes;
    private double[] distribucionCentrales;
    private double[] beneficioCentrales;

    Estado(int t1, int t2, int t3, int numClientes, double p1, double p2, double p3, double proporcionGarantizado,
            GeneradorEstadoInicial generador)
            throws Exception {
        clientes = generarClientes(numClientes, p1, p2, p3, proporcionGarantizado);
        centrales = generarCentrales(t1, t2, t3);
        generador.creaEstadoInicial(clientes, centrales);
        asignacionClientes = generador.getAsignacionClientes();
        distribucionCentrales = generador.getDistribucionCentrales();
        beneficioCentrales = generador.getBeneficioCentrales();
    }

    public Estado(Estado estadoAntiguo) {
        this.asignacionClientes = estadoAntiguo.asignacionClientes.clone();
        this.beneficioCentrales = estadoAntiguo.beneficioCentrales.clone();
        this.distribucionCentrales = estadoAntiguo.distribucionCentrales.clone();
    }

    private Centrales generarCentrales(int t1, int t2, int t3) throws Exception {
        int[] centralesDeCadaTipo = new int[3];
        centralesDeCadaTipo[0] = t1;
        centralesDeCadaTipo[1] = t2;
        centralesDeCadaTipo[2] = t3;
        return new Centrales(centralesDeCadaTipo, 1267);
    }

    private Clientes generarClientes(int numClientes, double p1, double p2, double p3, double proporcionGarantizado)
            throws Exception {
        double[] clientesDeCadaTipo = new double[3];
        clientesDeCadaTipo[0] = p1;
        clientesDeCadaTipo[1] = p2;
        clientesDeCadaTipo[2] = p3;
        return new Clientes(numClientes, clientesDeCadaTipo, proporcionGarantizado, 2943875);
    }

    public Boolean desasignar(int idxCliente) {
        if (asignacionClientes[idxCliente] == NO_ASIGNADO) {
            return false;
        }
        Cliente clienteToDesasignar = clientes.get(idxCliente);
        int idxCentral = asignacionClientes[idxCliente];
        Central centralToDesasignar = centrales.get(idxCentral);
        asignacionClientes[idxCliente] = NO_ASIGNADO;
        distribucionCentrales[idxCentral] -= produccionNecesariaToClienteFromCentral(clienteToDesasignar,
                centralToDesasignar);
        beneficioCentrales[idxCentral] -= beneficioFromClienteToCentral(clienteToDesasignar,
                centralToDesasignar);
        return true;
    }

    public Boolean asignar(int idxCliente, int idxCentral) {
        if (asignacionClientes[idxCliente] != NO_ASIGNADO) {
            return false;
        }
        Central central = centrales.get(idxCentral);
        Cliente cliente = clientes.get(idxCliente);
        double produccionNecesaria = produccionNecesariaToClienteFromCentral(cliente, central);
        if (central.getProduccion() < distribucionCentrales[idxCentral] + produccionNecesaria) {
            return false;
        }
        asignacionClientes[idxCliente] = idxCentral;
        distribucionCentrales[idxCentral] += produccionNecesaria;
        beneficioCentrales[idxCentral] += beneficioFromClienteToCentral(cliente, central);
        return true;
    }

    public Boolean swap(int idxCliente1, int idxCliente2) {
        int idxCentral1 = asignacionClientes[idxCliente1];
        int idxCentral2 = asignacionClientes[idxCliente2];

        Boolean desasignarCliente1 = this.desasignar(idxCliente1);
        Boolean desasignarCliente2 = this.desasignar(idxCliente2);

        if (!desasignarCliente1 && !desasignarCliente2) {
            return false;
        }

        Boolean asignarCliente1 = true;
        Boolean asignarCliente2 = true;

        if (desasignarCliente2)
            asignarCliente1 = this.asignar(idxCliente1, idxCentral2);
        if (desasignarCliente1)
            asignarCliente2 = this.asignar(idxCliente2, idxCentral1);
        return (asignarCliente1 && asignarCliente2);
    }

    public int[] getAsignacionClientes() {
        return asignacionClientes;
    }

    public double[] getDistribucionCentrales() {
        return distribucionCentrales;
    }

    public double[] getBeneficioCentrales() {
        return beneficioCentrales;
    }

    public static Clientes getClientes() {
        return clientes;
    }

    public static Centrales getCentrales() {
        return centrales;
    }

    public String toString() {
        MaximizarBeneficio calculadoraBeneficio = new MaximizarBeneficio();
        return "Beneficio = " + -calculadoraBeneficio.getHeuristicValue(this);
    }
}
