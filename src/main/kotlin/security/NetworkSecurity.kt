package security

import utils.CidrUtils
import utils.CidrUtils.rangesListBuilder

class NetworkSecurity(suspiciousCidrIps: List<String>) {

    private var suspiciousRanges: List<Pair<String, String>> = rangesListBuilder(suspiciousCidrIps)

    /**
     * Returns false if [incomingIp] is in [suspiciousRanges].
     * Otherwise returns true.
     */
    fun isAllowed(incomingIp: String): Boolean {
        val ip: String = CidrUtils.ipToBinaryString(incomingIp)
        suspiciousRanges.forEach { suspiciousRange ->
            if (ip in suspiciousRange.first..suspiciousRange.second) {
                println("[$incomingIp] is NOT allowed")
                return false
            }
        }
        println("[$incomingIp] is allowed")
        return true
    }
}