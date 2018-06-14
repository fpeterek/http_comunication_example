import com.sun.net.httpserver.*
import java.net.InetSocketAddress


fun main(args: Array<String>) {

    val server = HttpServer.create(InetSocketAddress(8000), 0)

    server.createContext("/", Handler())
    server.executor = null

    server.start()

}

class Handler : HttpHandler {

    override fun handle(exchange: HttpExchange) {

        println("Received request")

        println("Request: ${exchange.requestBody} + ${exchange.requestURI}, method: ${exchange.requestMethod}")
        println("Query: ${exchange.requestURI.query}")

        val attributes = parseQuery(exchange.requestURI.query)

        println("strojka: ${attributes["strojka"]}")

        print("\n")

        var response = "<b>Kendrick Lamar</b> - To pimp a butterfly"
        response += "<br>"
        response = attributes.toList().fold(response, fun(acc: String, it: Pair<String, String>): String =
                                                            acc + "<br>" + it.first + ": " + it.second )

        exchange.sendResponseHeaders(200, response.length.toLong())
        var os = exchange.responseBody

        if (os != null) {
            os.write(response.toByteArray())
            os.close()
        }

    }

    private fun parseQuery(query: String?): LinkedHashMap<String, String> {

        if (query == null) {
            return LinkedHashMap()
        }

        val list = query.split("&")

        var pairs = list.map { it.split("=") }
        pairs = pairs.filter { it.size == 2 }

        val map: LinkedHashMap<String, String> = LinkedHashMap()

        pairs.forEach { map[it[0]] = it[1] }

        return map

    }

}