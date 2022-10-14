import java.util.List;

import aima.search.framework.SuccessorFunction;


public class Sucesores implements SuccessorFunction {

    @Override
    public List<Estado> getSuccessors(Object arg0) {
        Estado estatActual = (Estado) arg0;
        List<Estado> sucesores;

        //Operador desassiganr
        for(int i = 0; i < estatActual.getAsignacionClientes().size(); ++i){
          Estado nouEstat = new Estado(estatActual);
          nouEstat.desassignar(i); //On i representa el client a desassignar
          sucesores.add(nouEstat);
        }

        //Operador assignar
        for(int i = 0; i < estatActual.getAsignacionClientes().size(); ++i){
            int[] assignacioClients = estatActual.getAsignacionClientes();
            if(assignacioClients[i] == -1){
              for(int j = 0; j < estatActual.centrales.size(); ++j){
                Estado nouEstat = new Estado(estatActual);
                nouEstat.assignar(i, j); //On i representa el client a assignar
                sucesores.add(nouEstat);
              }
           }
        }
        
        
        
                //Operador SWAP
        for(int i = 0; i < estatActual.getAsignacionClientes().size(); ++i){
            int[] assignacioClients = estatActual.getAsignacionClientes();
            
              for(int j = i; j < estatActual.getAsignacionClientes().size(); ++j){
                if(assignacioClients[j] != -1 or assignacioClients[i] != -1){  
                    bool esValid = true;
                    if((estatActual.clientes[i].getContrato() == Cliente.GARANTIZADO or estatActual.clientes[j].getContrato() == Cliente.GARANTIZADO)){
                        if(assignacioClients[i] == -1 or assignacioClients[j] == -1) esValid = false;
                    }
                 
                    if(esValid){
                      Estado nouEstat = new Estado(estatActual);
                      nouEstat.swap(i, j); //On i representa el client a assignar
                      sucesores.add(nouEstat);
                    }
                }
            }
        return sucesores;
       }
}
