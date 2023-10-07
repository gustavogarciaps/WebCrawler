package model

import groovy.transform.ToString

import java.time.LocalDate

@ToString
class ComponenteTISS {

    LocalDate referenceDate
    LocalDate publication
    LocalDate beginning

    ComponenteTISS(LocalDate referenceDate,
                   LocalDate publication,
                   LocalDate beginning) {

        this.referenceDate = referenceDate
        this.publication = publication
        this.beginning = beginning
    }

    @Override
    public String toString() {
        return "|Competência: ${this.referenceDate.getMonthValue()}/${this.referenceDate.getYear()}\t|Início Vigência: ${this.beginning.getDayOfMonth()}/${this.beginning.getMonthValue()}/${this.beginning.getYear()}\t|Publicação: ${this.publication.getDayOfMonth()}/${this.publication.getMonthValue()}/${this.publication.getYear()}"
    }
}
