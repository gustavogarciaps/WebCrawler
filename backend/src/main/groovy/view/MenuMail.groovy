package view

import utils.MailUtils

class MenuMail {

    static Scanner scanner = new Scanner(System.in)

    static void exibir() {

        while (true) {
            println("Opções de Scraping")
            println("1. Enviar e-mail (personalizado)")
            println("2. Enviar e-mail (destinatários cadastrados)")
            println("3. Sair")

            int opcaoMenu = scanner.nextInt()
            scanner.nextLine()

            switch (opcaoMenu) {
                case 1:
                    mailEdition()
                    println("Enviando e-mail...")
                    break
                case 2:
                   mailPattern()
                    println("Enviando e-mail...")
                    break
                case 3:
                    return
                    break
                default:
                    println("Opção não disponível")
                    break
            }
        }

    }

    static void mailEdition() {

        print("E-mail destinatário: ")
        def toEmail = scanner.nextLine()
        print("Assunto do e-mail: ")
        def subject = scanner.nextLine()
        print("Corpo do e-mail: ")
        def body = scanner.nextLine()

        MailUtils.sendEmail(toEmail, subject, body)
    }

    static void mailPattern(){
        def toEmail = "sousagustavogarcia@gmail.com"
        def subject = "Dados coletados"
        def body = "Segue anexo dados coletados"

        MailUtils.sendEmail(toEmail, subject, body)
    }
}
