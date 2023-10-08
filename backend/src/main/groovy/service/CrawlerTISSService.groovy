package service

import com.opencsv.CSVWriter
import model.ComponenteTISS
import org.jsoup.nodes.Element
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import static groovyx.net.http.HttpBuilder.configure

class CrawlerTISSService {

    static Document getConnect(String url) {
        try {
            return Jsoup.connect(url).get()
        } catch (Exception e) {
            e.printStackTrace()
            return null
        }
    }

    static void downloadFile(String url) {

        def path = url.split("/").last()
        try {
            configure {
                request.uri = url
                println("Obtendo url... ${url}")
            }.get {
                response.success { resp, reader ->
                    println("Criando documento...")
                    def directoryPath = "downloads/${path.split(".zip")[0]}"

                    def directory = new File(directoryPath)
                    directory.mkdirs()

                    def outputFile = new File("${directoryPath}/${path}")

                    def outputStream = new FileOutputStream(outputFile)
                    outputStream << reader

                    outputStream.close()

                    println "file://${outputFile.absolutePath}"
                }
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    static String getUrl(String reference, String URL) {

        try {
            Document document = getConnect(URL);
            Element link = document.select(reference).first()
            return link.attr("href");
        } catch (Exception e) {
            e.getMessage()
            return null
        }
    }

    static void writeLineByLine(ArrayList<ComponenteTISS> componenteTISS) throws IOException {

        File file = new File("downloads/TabelaCSVHistoricoVersoes/versoes.csv");

        try {
            FileWriter outputfile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputfile);

            String[] header = ["Competência", "Publicação", "Início Vigência"]
            writer.writeNext(header);

            componenteTISS.forEach { ComponenteTISS it ->
                String[] data = [it.referenceDate, it.publication, it.beginning]
                writer.writeNext(data)
            }
            writer.close();
            println "Caminho do Arquivo: file://${file.absolutePath}"
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}