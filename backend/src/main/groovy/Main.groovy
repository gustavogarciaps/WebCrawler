import org.jsoup.Jsoup
import org.jsoup.nodes.Document

def execute() {

    String url = "https://stackoverflow.com/questions/19868180/groovy-script-and-log4j"

    try {
        Document document = Jsoup.connect(url).get()
        println(document.outerHtml())
    } catch (Exception e) {
        println(e)
    }
}

execute()