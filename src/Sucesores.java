import java.util.List;

import aima.search.framework.SuccessorFunction;


public class Sucesores implements SuccessorFunction {

    @Override
    public List<Estado> getSuccessors(Object arg0) {
        Estado estatActual = (Estado) arg0;
        List<Estado> sucesores;

        //Operador desassiganr
        for(int i = 0; i < estatActual.getAsignacionClientes().size(); ++i){
          Estado nouEstat = estatActual;
          nouEstat.desassignar(i); //On i representa el client a desassignar
          sucesores.add(nouEstat);
        }

        return sucesores;
    }
}
