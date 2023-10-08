package view

import model.ComponenteTISS
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
            println("1. Download Componente de Comunicação")
            println("2. Visualizar Histórico de Versões")
            println("3. Download Tabela de Erros no Envio ANS")
            println("4. Sair")

            int opcaoMenu = scanner.nextInt()
            scanner.nextLine()

            switch (opcaoMenu) {
                case 1:
                    println("Iniciando download do Componente de Comunicação...")
                    downloadCommunicationComponent()
                    break
                case 2:
                    println("Criando tabela com histórico de Versões...")
                    String url = CrawlerTISSService.getUrl("p.callout:nth-of-type(4) a.external-link", URL_MAIN)
                    Document document = CrawlerTISSService.getConnect(url)
                    ArrayList<ComponenteTISS> componenteTISS = versionHistory(document)
                    CrawlerTISSService.writeLineByLine(componenteTISS)
                    break
                case 3:
                    println("Download da Tabela de Erro ANS...")
                    downloadTableErrorANS()
                    break
                case 4:
                    return
                    break
                default:
                    println("Opção não disponível")
                    break
            }
        }

    }

    static void downloadCommunicationComponent() {

        String url = CrawlerTISSService.getUrl("p.callout:nth-of-type(3) a.external-link", URL_MAIN)
        Document document = CrawlerTISSService.getConnect(url)

        Element tr = document.select("table tr:nth-of-type(5)").first()
        Element td = tr.select("td:nth-of-type(3)").first()
        String href = td.select("a").attr("href")

        CrawlerTISSService.downloadFile(href)
    }

    static void downloadTableErrorANS() {

        String url = CrawlerTISSService.getUrl("p.callout:nth-of-type(5) a", URL_MAIN)
        Document document = CrawlerTISSService.getConnect(url)

        Element span = document.select("p.callout:nth-of-type(1) a").first()
        String href = span.select("a").attr("href")

        CrawlerTISSService.downloadFile(href)
    }

    static ArrayList<ComponenteTISS> versionHistory(Document document) {

        ArrayList<ComponenteTISS> componenteTISS = new ArrayList<>();

        for (Element row in document.select("table tbody tr")) {

            LocalDate referenceDate = dateConversionMonth(row.select("td:nth-of-type(1)").text())
            LocalDate publication = dateConversion(row.select("td:nth-of-type(2)").text())
            LocalDate beginning = dateConversion(row.select("td:nth-of-type(3)").text())

            if (referenceDate >= LocalDate.of(2016, 1, 1)) {
                componenteTISS.add(new ComponenteTISS(referenceDate,
                        publication, beginning))
            }
        }
        return componenteTISS
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
