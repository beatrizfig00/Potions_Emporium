package Negocio;

public class Moeda {

    public static final int SICLES_POR_GALEAO = 17;
    public static final int NUQUES_POR_SICLE = 29;
    public static final int NUQUES_POR_GALEAO = SICLES_POR_GALEAO * NUQUES_POR_SICLE;

    public static int galeoesParaNuques(int galeoes) {
        return galeoes * NUQUES_POR_GALEAO;
    }

    public static int nuquesParaGaleoes(int nuques) {
        return nuques / NUQUES_POR_GALEAO;
    }

    public static int siclesParaNuques(int sicles) {
        return sicles * NUQUES_POR_SICLE;
    }

    public static int nuquesParaSicles(int nuques) {
        return nuques / NUQUES_POR_SICLE;
    }
}
