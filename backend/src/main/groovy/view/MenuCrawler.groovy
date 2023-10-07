package view

import model.ComponenteTISS
import org.apache.tools.ant.taskdefs.Local
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import service.CrawlerTISSService

import java.time.LocalDate

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

                    println(new ComponenteTISS(dateConversionMonth("jan/2023"), dateConversion("1/1/2023"), dateConversion("1/1/2023")))

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

            LocalDate referenceDate = dateConversionMonth(row.select("td:nth-of-type(1)").text())
            LocalDate publication = dateConversion(row.select("td:nth-of-type(2)").text())
            LocalDate beginning = dateConversion(row.select("td:nth-of-type(3)").text())

            if (referenceDate >= LocalDate.of(2016, 1, 1)) {
                println(new ComponenteTISS(referenceDate,
                        publication, beginning))
            }

        }
    }


    static LocalDate dateConversion(String date) {

        def day = date.split("/")[0]
        def month = date.split("/")[1]
        def year = date.split("/")[2]

        LocalDate dateConversion = LocalDate.of(year.toInteger(), month.toInteger(), day.toInteger())

        return dateConversion;
    }

    static LocalDate dateConversionMonth(String date) {
        def day = 1
        def monthAbbreviation = date.split("/")[0]
        def year = date.split("/")[1]

        def monthMap = [
                "jan": 1,
                "fev": 2,
                "mar": 3,
                "abr": 4,
                "mai": 5,
                "jun": 6,
                "jul": 7,
                "ago": 8,
                "set": 9,
                "out": 10,
                "nov": 11,
                "dez": 12
        ]

        def monthInteger = monthMap[monthAbbreviation?.toLowerCase()]

        LocalDate dateConversion = LocalDate.of(year.toInteger(), monthInteger, day)
        return dateConversion

    }

}
