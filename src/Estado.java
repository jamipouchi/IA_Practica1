import Generadores.GeneradorEstadoInicial;
import IA.Energia.Centrales;
import IA.Energia.Clientes;

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
    
    public void desassignar(int numClient) {
      if(asignacionClientes[numClient] != -1){
        int numCentral = asignacionClientes[numClient]
        asignacionClientes[numClient] = -1;
        Cliente c = clientes.get(numClient);
        Central ce = centrales.get(numCentral);

        double perdues = calculaPerdues(c.getCoordX, c.getCoordY, ce.getCoordX, ce.getCoordY);
        distribucionCentrales[numCentral] -= c.getConsumo()*(1 + perdues);
        int tipusCentral = ce.getTipo();
        int tipusClient = c.getTipo();
        int contrato = c.getContrato();

        if(ultimClient(numCentral, asignacionClientes)){
          if(tipusCentral == 0) beneficioCentrales[numCentral] = -15000;
          else if(tipusCentral == 1) beneficioCentrales[numCentral] = -5000;
          else beneficioCentrales[numCentral] = -1500;
        }
        else {
          if(tipusClient == 0) beneficioCentrales[numCentral] -= c.getConsumo()*(400-100*contrato);
          else if(tipusClient == 1) beneficioCentrales[numCentral] -= c.getConsumo()*(500-100*contrato);
          else beneficioCentrales[numCentral] -= c.getConsumo()*(600-100*contrato);
        }
      }
    }

    private double calculaPerdues(int clientX, int clientY, int centralX, int centralY){
      int x = abs(clientX - centralX);
      int y = abs(clientY - centralY);
      int d = sqrt(x*x + y*y);

      if(d <= 10) return 0;
      else if(d <= 25) return 0.1;
      else if(d <= 50) return 0.2;
      else if(d <= 75) return 0.4;
      else return 0.6;
    }

    private bool ultimClient(int numCentral, int[] assigClients){
      for(int i = 0; i < assigClients.size(); ++i) if(assigClients[i] == numCentral) return false;
      return true;
    }
}
