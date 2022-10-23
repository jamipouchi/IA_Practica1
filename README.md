IA Pràctica 1 - cerca local
Abel Batalla, Miquel Puig i Arnau Gràcia

PER EXECUTAR:
"Run" des del fitxer Experimento7.java
(IA_Practica1/src/CentralEnergia/Experimentos/Experimento7.java)


PER CANVIAR CONFIGURACIONS:
  Al fitxer Experimento7.java:
    La línia 22 canvia el generador d'estats inicials. Opcions:
          GeneradorGarantizadosRandom generador = new GeneradorGarantizadosRandom();
          GeneradorGarantizadosRandomHastaLlenar generador = new GeneradorGarantizadosRandomHastaLlenar();
          GeneradorLineal generador = new GeneradorLineal();
    De la línia 23 a la 32 es configuren els paràmetres de l'escenari.
      - Nombre de centrals de cada tipus
      - Nombre de clients
      - Proporcio de clients de cada tipus
      - Proporcio de clients garantitzats
      - Seed
    La línia 42 canvia l'heurístic. Opcions:
           new MaximitzarBenefici());
           new LaCombiPerfecta());
           new MaximizarAsignaciones());
           new MaximizarUsoEnergia());
           new MinimizarCentrales());
           new MinimizarDistancia());
           
  Al fitxer GeneradoraSucesores.java es pot canviar el tipus d'operadors que es volen (assignar i desassignar  per default)  (IA_Practica1/src/CentralEnergia/GeneradoraSucesores/GeneradoraSucesores.java):
    Per posar o treure operadors cal comentar o descomentar de la línia 37 a la 50 (operador smartSwap) o de la línia 53 a la 63 (operador swap).
