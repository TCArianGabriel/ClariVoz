package org.dsm.clarivoz.users

import java.io.Serializable


data class User(
    var email: String = "",
    var password: String = "",
    var activo: Boolean = true
) : Serializable {
    // Aquí no es necesario definir getters y setters explícitos
    fun getId(): String {
        return email
    }
}
