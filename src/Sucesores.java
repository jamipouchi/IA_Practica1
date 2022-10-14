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
            if(not estatActual.clientGarantitzat(i)) {
          if(nouEstat.desassignar(i)) sucesores.add(nouEstat); //On i representa el client a desassignar
            }
        }

        //Operador assignar
        for(int i = 0; i < estatActual.getAsignacionClientes().size(); ++i){
            int[] assignacioClients = estatActual.getAsignacionClientes();
            if(assignacioClients[i] == -1){
              for(int j = 0; j < estatActual.getSizeCentrales(); ++j){
                Estado nouEstat = new Estado(estatActual);
                if(nouEstat.assignar(i, j)) sucesores.add(nouEstat);//On i representa el client a assignar
              }
           }
        }
        
        
        
                //Operador SWAP
        for(int i = 0; i < estatActual.getAsignacionClientes().size(); ++i){
            int[] assignacioClients = estatActual.getAsignacionClientes();
            
              for(int j = i; j < estatActual.getAsignacionClientes().size(); ++j){
                if(assignacioClients[j] != -1 or assignacioClients[i] != -1){  
                    bool esValid = true;
                    if(estatActual.clientGarantitzat(i) or estatActual.clientGarantitzat(j)){
                        if(assignacioClients[i] == -1 or assignacioClients[j] == -1) esValid = false;
                    
                 
                    if(esValid){
                      Estado nouEstat = new Estado(estatActual);
                      nouEstat.swap(i, j);
                      sucesores.add(nouEstat);
                    }
                    }
                }
            }
        return sucesores;
       }
}
