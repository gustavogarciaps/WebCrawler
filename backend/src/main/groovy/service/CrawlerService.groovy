package service

import static groovyx.net.http.HttpBuilder.configure

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class CrawlerService {

    static Document getConnect(String url) {
        return Jsoup.connect(url).get()
    }

    static void downloadFile(String url) {
        configure {
            request.uri = url
        }.get {
            response.success { resp, reader ->
                def outputFile = new File('./downloads/a.zip')

                if (outputFile.exists()) {
                    outputFile.delete()
                }

                def outputStream = new FileOutputStream(outputFile)
                outputStream << reader
                outputStream.close()

                println "Downloaded: ${outputFile.absolutePath}"
            }
        }
    }
}
