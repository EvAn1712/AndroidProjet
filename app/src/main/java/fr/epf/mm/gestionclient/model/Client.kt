package fr.epf.mm.gestionclient.model

enum class Gender {
    MAN, WOMAN
}

data class Client(
    val lastName: String,
    val firstName: String,
    val gender: Gender
){
    companion object {

        fun generate(size : Int = 20) =
            (1..size).map {
                Client("Nom${it}",
                    "Prénom${it}",
                    if(it % 3 == 0) Gender.MAN else Gender.WOMAN)
            }
    }
}