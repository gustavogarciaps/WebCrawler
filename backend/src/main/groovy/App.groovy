import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class App {

    static void main(args) {
        String url = "https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/padrao-tiss-historico-das-versoes-dos-componentes-do-padrao-tiss"

        try {
            Document document = Jsoup.connect(url).get()


            for (Element row in document.select("table tbody tr")) {

                String competencia = row.select("td:nth-of-type(1)").text()
                String publicacao = row.select("td:nth-of-type(2)").text()
                String inicioVigencia = row.select("td:nth-of-type(3)").text()

                println(new ComponentesTISS(competencia, publicacao, inicioVigencia))
            }


        } catch (Exception e) {
            println(e)
        }
    }
}
