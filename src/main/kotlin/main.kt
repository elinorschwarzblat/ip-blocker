import security.NetworkSecurity

fun main() {
    val networkSecurity = NetworkSecurity(listOf("255.55.10.3/24", "10.20.30.40/22", "192.80.255.0/8"))
    networkSecurity.isAllowed("10.20.32.3")
    networkSecurity.isAllowed("240.20.32.3")
    networkSecurity.isAllowed("10.20.28.0")
    networkSecurity.isAllowed("10.20.31.255")
    networkSecurity.isAllowed("10.20.29.3")
}