package datatypes;

public class DTCostos {

    private float costoBaseTurista;
    private float costoBaseEjecutivo;
    private float costoEquipajeExtra;

    public DTCostos(float costoBaseEjecutivo, float costoBaseTurista, float costoEquipajeExtra) {
        this.costoBaseEjecutivo = costoBaseEjecutivo;
        this.costoBaseTurista = costoBaseTurista;
        this.costoEquipajeExtra = costoEquipajeExtra;
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

    public void setCostoBaseEjecutivo(float costoBaseEjecutivo) {
        this.costoBaseEjecutivo = costoBaseEjecutivo;
    }

    public void setCostoBaseTurista(float costoBaseTurista) {
        this.costoBaseTurista = costoBaseTurista;
    }

    public void setCostoEquipajeExtra(float costoEquipajeExtra) {
        this.costoEquipajeExtra = costoEquipajeExtra;
    }

}
