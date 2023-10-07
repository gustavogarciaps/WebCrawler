package view

import model.ComponenteTISS
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import service.CrawlerTISSService

class MenuCrawler {

    static final String URL_MAIN = "https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss"
    static Scanner scanner = new Scanner(System.in)

    static void exibir() {

        while (true) {
            println("Opções de Scraping")
            println("1. Download dos Componentes")
            println("2. Visualizar Histórico de Versões")
            println("3. Download Tabelas Relacionadas")
            println("4. Sair")

            int opcaoMenu = scanner.nextInt()
            scanner.nextLine()

            switch (opcaoMenu) {
                case 1:
                    downloadComponents()

                    break
                case 2:
                    def url = CrawlerTISSService.getUrl("p.callout:nth-of-type(4) a.external-link", URL_MAIN)
                    Document document = CrawlerTISSService.getConnect(url)
                    versionHistory(document)
                    break
                case 3:
                    break
                case 4:
                    return
                    break
                default:

                    break
            }
        }

    }

    static void downloadComponents() {

        String url = CrawlerTISSService.getUrl("p.callout:nth-of-type(3) a.external-link", URL_MAIN)
        Document document = CrawlerTISSService.getConnect(url)

        Element tr = document.select("table tr:nth-of-type(5)").first()
        Element td = tr.select("td:nth-of-type(3)").first()
        String href = td.select("a").attr("href")

        CrawlerTISSService.downloadFile(href)


    }

    static void versionHistory(Document document) {

        for (Element row in document.select("table tbody tr")) {

            String competencia = row.select("td:nth-of-type(1)").text()
            String publicacao = row.select("td:nth-of-type(2)").text()
            String inicioVigencia = row.select("td:nth-of-type(3)").text()

            println(new ComponenteTISS(competencia, publicacao, inicioVigencia))
        }
    }
}
