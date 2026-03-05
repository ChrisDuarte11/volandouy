package logica.vuelo;

import datatypes.DTCostos;

public class Costos {

    private float costoBaseEjecutivo;
    private float costoBaseTurista;
    private float costoEquipajeExtra;

    public Costos(float costoBaseEjecutivo, float costoBaseTurista, float costoEquipajeExtra) {
        this.costoBaseEjecutivo = costoBaseEjecutivo;
        this.costoBaseTurista = costoBaseTurista;
        this.costoEquipajeExtra = costoEquipajeExtra;
    }

    public Costos(DTCostos costos) {
        this.costoBaseEjecutivo = costos.getCostoBaseEjecutivo();
        this.costoBaseTurista = costos.getCostoBaseTurista();
        this.costoEquipajeExtra = costos.getCostoEquipajeExtra();
    }

    public float getCostoBaseEjecutivo() {
        return costoBaseEjecutivo;
    }

    public float getCostoBaseTurista() {
        return costoBaseTurista;
    }

    public float getCostoEquipajeExtra() {
        return costoEquipajeExtra;
    }

    public DTCostos getDT(){
		return new DTCostos(this.costoBaseEjecutivo, this.costoBaseTurista, this.costoEquipajeExtra);
    }

}
