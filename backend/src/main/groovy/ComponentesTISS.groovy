import java.time.LocalDate

class ComponentesTISS {

    String competencia
    String publicacao
    String inicioVigencia

    ComponentesTISS(String competencia,
                    String publicacao,
                    String inicioVigencia) {
        this.competencia = competencia
        this.publicacao = publicacao
        this.inicioVigencia = inicioVigencia
    }


    @Override
    public String toString() {
        return "ComponentesTISS{" +
                "competencia='" + competencia + '\'' +
                ", publicacao=" + publicacao +
                ", inicioVigencia=" + inicioVigencia +
                '}';
    }
}
