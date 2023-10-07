import model.ComponenteTISS
import service.CrawlerService
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class App {

    static void main(args) {

        final String URL_MAIN = "https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/" +
                "padrao-tiss-historico-das-versoes-dos-componentes-do-padrao-tiss"
        final String URL_SECOND = "https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/" +
                "PadroTISS_ComponentedeContedoeEstrutura_202211.zip"

        try {

            Document document = CrawlerService.getConnect(URL_MAIN);

            for (Element row in document.select("table tbody tr")) {

                String competencia = row.select("td:nth-of-type(1)").text()
                String publicacao = row.select("td:nth-of-type(2)").text()
                String inicioVigencia = row.select("td:nth-of-type(3)").text()

                println(new ComponenteTISS(competencia, publicacao, inicioVigencia))
            }

            CrawlerService.downloadFile(URL_SECOND)

        } catch (Exception e) {
            println(e)
        }

    }
}
