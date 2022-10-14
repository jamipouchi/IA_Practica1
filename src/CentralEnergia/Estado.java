package CentralEnergia;

import IA.Energia.Central;
import IA.Energia.Centrales;
import IA.Energia.Cliente;
import IA.Energia.Clientes;
import IA.Energia.VEnergia;

import static CentralEnergia.Utils.Utils.*;

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

    Estado(Estado estadoAntiguo) {
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

    public int[] getAsignacionClientes() {
        return asignacionClientes;
    }

    public double[] getDistribucionCentrales() {
        return distribucionCentrales;
    }

    public double[] getBeneficioCentrales() {
        return beneficioCentrales;
    }
    
    public Boolean clientGarantitzat(int numClient) {
        Cliente c = clientes.get(numClient);
        return c.getContrato() == Cliente.GARANTIZADO;

    }
    
    
    public int getSizeCentrales(){
     return centrales.size();   
    }
    
    public Boolean desassignar(int numClient) {
      
      Cliente c = clientes.get(numClient);
      if(asignacionClientes[numClient] != -1){
        int numCentral = asignacionClientes[numClient]
        Central ce = centrales.get(numCentral);
         
        asignacionClientes[numClient] = -1;
        double consumClient = produccionNecesariaToClienteFromCentral(c, ce);
        distribucionCentrales[numCentral] -= consumClient;
        beneficioCentrales[numCentral] -= c.getConsumo()*VEnergia.getTarifaClienteNoGarantizada(c.getTipo());
        return true;
      }
        return false;
    }
    
    public Boolean assignar(int numClient, int numCentral){
        try {
     
        Cliente c = clientes.get(numClient);
        Central ce = centrales.get(numCentral);
        
        double consumClient = produccionNecesariaToClienteFromCentral(c, ce);
        if(ce.getProduccion() >= distribucionCentrales[numCentral] + consumClient){ //Si producció actual + demanda client no supera producció total
           asignacionClientes[numClient] = numCentral;
           distribucionCentrales[numCentral] += consumClient;
           if(c.getContrato() != Cliente.GARANTIZADO) beneficioCentrales[numCentral] += c.getConsumo()*VEnergia.getTarifaClienteNoGarantizada(c.getTipo());
           else beneficioCentrales[numCentral] += c.getConsumo()*VEnergia.getTarifaClienteGarantizada(c.getTipo());
            return true;
        }
        return false;
    } catch (Exception e) {
        // Excepcion de tipo, no ocurrira
        return false;
    }
    }
    
    
    public Boolean swap(int numClient1, int numClient2){
            
        int numCentral1 =  asignacionClientes[numClient1];
        int numCentral2 =  asignacionClientes[numClient2];
        
    
        Boolean client1old = this.desassignar(numClient1);   
        Boolean client2old = this.desassignar(numClient2);
        
        Boolean assig1 = true;
        Boolean assig2 = true;

       
        if(client2old) assig1 = this.assignar(numClient1, numCentral2);
        if(client1old) assig2 = this.assignar(numClient2, numCentral1);
        return (assig1 && assig2);
    }
   

    public static Clientes getClientes() {
        return clientes;
    }

    public static Centrales getCentrales() {
        return centrales;
    }
}
