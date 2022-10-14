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
            
          for(int j = 0; j < estatActual.centrales.size(); ++j){
            Estado nouEstat = new Estado(estatActual);
            nouEstat.assignar(i, j); //On i representa el client a assignar
            sucesores.add(nouEstat);
          }
        }
        
        return sucesores;
    }
}
