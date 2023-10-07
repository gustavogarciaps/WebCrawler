package service

import org.jsoup.nodes.Element

import static groovyx.net.http.HttpBuilder.configure

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

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
            }.get {
                response.success { resp, reader ->
                    def directoryPath = "downloads/${path.split(".zip")[0]}"

                    def directory = new File(directoryPath)
                    directory.mkdirs()

                    def outputFile = new File("${directoryPath}/${path}")

                    def outputStream = new FileOutputStream(outputFile)
                    outputStream << reader
                    outputStream.close()

                    println "Caminho do Arquivo: file://${outputFile.absolutePath}"

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

}
