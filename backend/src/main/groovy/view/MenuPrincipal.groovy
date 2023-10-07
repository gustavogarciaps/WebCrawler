package view

class MenuPrincipal {

    static Scanner scanner = new Scanner(System.in)

    static void exibir() {

        while (true) {
            println("Opções")
            println("1. Crawler")
            println("2. E-mails")
            println("3. Finalizar")

            int opcaoMenu = scanner.nextInt()
            scanner.nextLine()

            switch (opcaoMenu) {
                case 1:
                    MenuCrawler.exibir()
                    break
                case 2:

                    break
                case 3:
                    return
                default:

                    break
            }
        }

    }
}
