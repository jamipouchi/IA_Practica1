import java.util.List;

import CentralEnergia.Estado;
import aima.search.framework.SuccessorFunction;

public class Sucesores implements SuccessorFunction {

    @Override
    public List<Estado> getSuccessors(Object arg0) {
        Estado estatActual = (Estado) arg0;
        List<Estado> sucesores = generarPossibles();
        return sucesores;
    }

    private List<Estado> generarPossibles() {
        return null;
    }
}